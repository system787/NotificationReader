package net.vincenthoang.notificationreader.addtransactions;

import net.vincenthoang.notificationreader.BasePresenter;
import net.vincenthoang.notificationreader.BaseView;

/**
 * Created by vincenthoang on 3/8/18.
 */

public interface AddTransactionContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTransactionError();

        void showTransactionsList();

        void setAmount(int amount);

        void setType(String type);

        void setDescription(String description);
    }

    interface Presenter extends BasePresenter {

        void saveTransaction(int amount, String type, String description);

        void populateTransaction();

        boolean isDataMissing();
    }
}
