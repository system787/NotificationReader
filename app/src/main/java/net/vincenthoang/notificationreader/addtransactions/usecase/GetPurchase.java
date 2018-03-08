package net.vincenthoang.notificationreader.addtransactions.usecase;

import android.support.annotation.NonNull;

import net.vincenthoang.notificationreader.UseCase;
import net.vincenthoang.notificationreader.data.source.PurchasesDataSource;
import net.vincenthoang.notificationreader.data.source.PurchasesRepository;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/8/18.
 */

public class GetPurchase extends UseCase<GetPurchase.RequestValues, GetPurchase.ResponseValue> {

    private final PurchasesRepository mPurchasesRepository;

    public GetPurchase(@NonNull PurchasesRepository purchasesRepository) {
        mPurchasesRepository = checkNotNull(purchasesRepository, "purchasesRepository cannot be null");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mPurchasesRepository.getPurchase(requestValues.getPurchaseId(), new PurchasesDataSource.GetPurchasesCallback() {
            @Override
            public void onPurchasesLoaded(Purchase purchase) {
                if (purchase != null) {
                    ResponseValue responseValue = new ResponseValue(purchase);
                    getUseCaseCallback().onSuccess(responseValue);
                } else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }


    public static final class RequestValues implements UseCase.RequestValues {

        private final String mPurchaseId;

        public RequestValues(@NonNull String purchaseId) {
            mPurchaseId = checkNotNull(purchaseId, "purchaseId cannot be null");
        }

        public String getPurchaseId() {
            return mPurchaseId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private Purchase mPurchase;

        public ResponseValue(@NonNull Purchase purchase) {
            mPurchase = checkNotNull(purchase, "purchase cannot be null");
        }

        public Purchase getPurchase() {
            return mPurchase;
        }
    }
}
