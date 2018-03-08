package net.vincenthoang.notificationreader.purchases.domain.usecase;

import android.support.annotation.NonNull;

import net.vincenthoang.notificationreader.UseCase;
import net.vincenthoang.notificationreader.data.source.PurchasesDataSource;
import net.vincenthoang.notificationreader.data.source.PurchasesRepository;
import net.vincenthoang.notificationreader.purchases.PurchasesFilterType;
import net.vincenthoang.notificationreader.purchases.domain.filter.FilterFactory;
import net.vincenthoang.notificationreader.purchases.domain.filter.PurchasesFilter;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/7/18.
 */

public class GetPurchases extends UseCase<GetPurchases.RequestValues, GetPurchases.ResponseValue> {

    private final PurchasesRepository mPurchasesRepository;

    private final FilterFactory mFilterFactory;

    public GetPurchases(@NonNull PurchasesRepository purchasesRepository, @NonNull FilterFactory filterFactory) {
        mPurchasesRepository = checkNotNull(purchasesRepository, "purchasesRepository cannot be null");
        mFilterFactory = checkNotNull(filterFactory, "filterFactory cannot be null");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        if (values.isForceUpdate()) {
            mPurchasesRepository.refreshPurchases();
        }

        mPurchasesRepository.getPurchases(new PurchasesDataSource.LoadPurchasesCallback() {
            @Override
            public void onPurchasesLoaded(List<Purchase> purchases) {
                PurchasesFilterType currentFiltering = values.getCurrentFiltering();
                PurchasesFilter purchasesFilter = mFilterFactory.create(currentFiltering);

                List<Purchase> purchasesFiltered = purchasesFilter.filter(purchases);
                ResponseValue responseValue = new ResponseValue(purchasesFiltered);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final PurchasesFilterType mCurrentFiltering;

        private final boolean mForceUpdate;

        public RequestValues(@NonNull PurchasesFilterType currentFiltering, boolean forceUpdate) {
            mCurrentFiltering = checkNotNull(currentFiltering, "current filtering cannot be null");
            mForceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public PurchasesFilterType getCurrentFiltering() {
            return mCurrentFiltering;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Purchase> mPurchases;

        public ResponseValue(@NonNull List<Purchase> purchases) {
            mPurchases = checkNotNull(purchases, "purchases cannot be null");
        }

        public List<Purchase> getPurchases() {
            return mPurchases;
        }
    }
}
