package net.vincenthoang.notificationreader.addtransactions.usecase;

import android.support.annotation.NonNull;

import net.vincenthoang.notificationreader.UseCase;
import net.vincenthoang.notificationreader.data.source.PurchasesRepository;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/8/18.
 */

public class DeletePurchase extends UseCase<DeletePurchase.RequestValues, DeletePurchase.ResponseValue> {

    private final PurchasesRepository mPurchasesRepository;

    public DeletePurchase(@NonNull PurchasesRepository purchasesRepository) {
        mPurchasesRepository = checkNotNull(purchasesRepository, "purchasesRepository cannot be null");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mPurchasesRepository.deletePurchase(requestValues.getPurchaseId());
        getUseCaseCallback().onSuccess(new ResponseValue());
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

    }
}
