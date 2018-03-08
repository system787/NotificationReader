package net.vincenthoang.notificationreader.purchases;

import android.support.annotation.NonNull;

import net.vincenthoang.notificationreader.UseCaseHandler;
import net.vincenthoang.notificationreader.purchases.domain.usecase.GetPurchases;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/7/18.
 */

public class PurchasesPresenter implements PurchasesContract.Presenter {

    private final PurchasesContract.View mPurchasesView;
    private final GetPurchases mGetPurchases;

    private PurchasesFilterType mCurrentFiltering = PurchasesFilterType.ALL_TRANSACTIONS;

    private boolean mFirstLoad = true;

    private final UseCaseHandler mUseCaseHandler;

    public PurchasesPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull PurchasesContract.View purchasesView,
                              @NonNull GetPurchases getPurchases) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null");
        mPurchasesView = checkNotNull(purchasesView, "purchasesView cannot be null");
        mGetPurchases = checkNotNull(getPurchases, "getPurchases cannot be null");

        mPurchasesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPurchases(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadPurchases(boolean forceUpdate) {

    }

    @Override
    public void addNewPurchase() {

    }

    @Override
    public void setFiltering(PurchasesFilterType requestType) {

    }

    @Override
    public PurchasesFilterType getFiltering() {
        return null;
    }
}
