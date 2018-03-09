package net.vincenthoang.notificationreader.addtransactions.usecase;

import android.support.annotation.NonNull;

import net.vincenthoang.notificationreader.UseCase;
import net.vincenthoang.notificationreader.data.source.PurchasesRepository;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/8/18.
 */

public class SavePurchase extends UseCase<SavePurchase.RequestValues, SavePurchase.ResponseValue> {

    private final PurchasesRepository mPurchasesRepository;

    public SavePurchase(@NonNull PurchasesRepository purchasesRepository) {
        mPurchasesRepository = checkNotNull(purchasesRepository, "purchasesRepository cannot be null");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {

        Purchase purchase = requestValues.getPurchase();
        mPurchasesRepository.savePurchase(purchase);

        getUseCaseCallback().onSuccess(new ResponseValue(purchase));
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Purchase mPurchase;

        public RequestValues(@NonNull Purchase purchase) {
            mPurchase = checkNotNull(purchase, "purchase cannot be null");
        }

        public Purchase getPurchase() {
            return mPurchase;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Purchase mPurchase;

        public ResponseValue(@NonNull Purchase purchase) {
            mPurchase = checkNotNull(purchase, "purchase cannot be null");
        }

        public Purchase getPurchase() {
            return mPurchase;
        }
    }
}
