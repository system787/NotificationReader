package net.vincenthoang.notificationreader.data.source;

import android.support.annotation.NonNull;

import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.List;

/**
 * Main entry point for accessing purchases data.
 * <p>
 * For simplicity, only getPurchases() and getPurchase() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new purchase is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface PurchasesDataSource {

    interface LoadPurchasesCallback {

        void onPurchasesLoaded(List<Purchase> purchases);

        void onDataNotAvailable();
    }

    interface GetPurchasesCallback {

        void onPurchasesLoaded(Purchase purchase);

        void onDataNotAvailable();
    }

    void getPurchases(@NonNull LoadPurchasesCallback callback);

    void getPurchase(@NonNull String purchaseId, @NonNull GetPurchasesCallback callback);

    void savePurchase(@NonNull Purchase purchase);

    void refreshPurchases();

    void deleteAllPurchases();

    void deletePurchase(@NonNull String purchaseId);
}
