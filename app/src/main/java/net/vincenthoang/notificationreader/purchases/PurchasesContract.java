package net.vincenthoang.notificationreader.purchases;

import net.vincenthoang.notificationreader.BasePresenter;
import net.vincenthoang.notificationreader.BaseView;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.List;

/**
 * Created by vincenthoang on 3/7/18.
 */

public interface PurchasesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showPurchases(List<Purchase> purchases);

        void showAddPurchase();

        void showLoadingPurchasesError();

        void showPurchasesFilterLabel();

        void showRefundsFilterLabel();

        void showAllFilterLabel();

        void showSuccessfullySavedMessage();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);

        void loadPurchases(boolean forceUpdate);

        void addNewPurchase();

        void setFiltering(PurchasesFilterType requestType);

        PurchasesFilterType getFiltering();
    }
}
