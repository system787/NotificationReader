package net.vincenthoang.notificationreader.addtransactions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.vincenthoang.notificationreader.UseCase;
import net.vincenthoang.notificationreader.UseCaseHandler;
import net.vincenthoang.notificationreader.addtransactions.usecase.GetPurchase;
import net.vincenthoang.notificationreader.addtransactions.usecase.SavePurchase;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;
import net.vincenthoang.notificationreader.purchases.domain.usecase.GetPurchases;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/8/18.
 */

public class AddTransactionPresenter implements AddTransactionContract.Presenter {

    private final AddTransactionContract.View mAddTransactionView;

    private final GetPurchase mGetPurchase;

    private final SavePurchase mSavePurchase;

    private final UseCaseHandler mUseCaseHandler;

    @Nullable
    private String mPurchaseId;

    private boolean mIsDataMissing;

    public AddTransactionPresenter(@NonNull UseCaseHandler useCaseHandler, @Nullable String purchaseId,
                                   @NonNull AddTransactionContract.View addTransactionView, @NonNull GetPurchase getPurchase,
                                   @NonNull SavePurchase savePurchase, boolean loadDataFromRepo) {
        mUseCaseHandler = checkNotNull(useCaseHandler);
        mPurchaseId = purchaseId;
        mAddTransactionView = checkNotNull(addTransactionView);
        mGetPurchase = checkNotNull(getPurchase);
        mSavePurchase = checkNotNull(savePurchase);
        mIsDataMissing = loadDataFromRepo;

        mAddTransactionView.setPresenter(this);
    }

    private void showSaveError(Purchase p) {
        Logger.getLogger("AddTransactionPresenter").log(Level.WARNING, "Unable to save object " + p.toString());
    }

    private boolean isNewPurchase() {
        return mPurchaseId == null;
    }

    private void createPurchase(int amount, String type, String description) {
        final Purchase p = new Purchase(type, description, amount);

        if (p.isEmpty()) {
            mAddTransactionView.showEmptyTransactionError();
        } else {
            mUseCaseHandler.execute(mSavePurchase, new SavePurchase.RequestValues(p),
                    new UseCase.UseCaseCallback<SavePurchase.ResponseValue>() {
                        @Override
                        public void onSuccess(SavePurchase.ResponseValue response) {
                            mAddTransactionView.showTransactionsList();
                        }

                        @Override
                        public void onError() {
                            showSaveError(p);
                        }
                    });
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void saveTransaction(int amount, String type, String description) {

    }

    @Override
    public void populateTransaction() {

    }

    @Override
    public boolean isDataMissing() {
        return false;
    }
}
