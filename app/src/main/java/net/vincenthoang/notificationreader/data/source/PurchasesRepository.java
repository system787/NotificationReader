package net.vincenthoang.notificationreader.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/7/18.
 */

public class PurchasesRepository implements PurchasesDataSource {

    private static PurchasesRepository INSTANCE = null;

    private final PurchasesDataSource mPurchasesRemoteDataSource; // no remote data planned yet

    private final PurchasesDataSource mPurchasesLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Purchase> mCachedPurchases;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    private PurchasesRepository(@NonNull PurchasesDataSource purchasesRemoteDataSource,
                                @NonNull PurchasesDataSource purchasesLocalDataSource) {
        mPurchasesRemoteDataSource = checkNotNull(purchasesRemoteDataSource);
        mPurchasesLocalDataSource = checkNotNull(purchasesLocalDataSource);
    }

    public static PurchasesRepository getInstance(PurchasesDataSource purchasesRemoteDataSource,
                                                  PurchasesDataSource purchasesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PurchasesRepository(purchasesRemoteDataSource, purchasesLocalDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private void refreshCache(List<Purchase> purchases) {
        if (mCachedPurchases == null) {
            mCachedPurchases = new LinkedHashMap<>();
        }
        mCachedPurchases.clear();
        for (Purchase purchase : purchases) {
            mCachedPurchases.put(purchase.getId(), purchase);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Purchase> purchases) {
        mPurchasesLocalDataSource.deleteAllPurchases();
        for (Purchase purchase : purchases) {
            mPurchasesLocalDataSource.savePurchase(purchase);
        }
    }

    @Override
    public void getPurchases(@NonNull final LoadPurchasesCallback callback) {
        checkNotNull(callback);

        if (mCachedPurchases != null && !mCacheIsDirty) {
            callback.onPurchasesLoaded(new ArrayList<Purchase>(mCachedPurchases.values()));
            return;
        }

        if (mCacheIsDirty) {
            mPurchasesLocalDataSource.getPurchases(new LoadPurchasesCallback() {
                @Override
                public void onPurchasesLoaded(List<Purchase> purchases) {
                    refreshCache(purchases);
                    callback.onPurchasesLoaded(new ArrayList<Purchase>(mCachedPurchases.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    // no remote data planned yet
                }
            });
        }
    }

    @Override
    public void getPurchase(@NonNull final String purchaseId, @NonNull final GetPurchasesCallback callback) {
        checkNotNull(purchaseId);
        checkNotNull(callback);

        Purchase cachedPurchase = getPurchaseWithId(purchaseId);

        // Respond immediately with cache if available
        if (cachedPurchase != null) {
            callback.onPurchasesLoaded(cachedPurchase);
            return;
        }

        mPurchasesLocalDataSource.getPurchase(purchaseId, new GetPurchasesCallback() {
            @Override
            public void onPurchasesLoaded(Purchase purchase) {
                if (mCachedPurchases == null) {
                    mCachedPurchases = new LinkedHashMap<>();
                }
                mCachedPurchases.put(purchase.getId(), purchase);
                callback.onPurchasesLoaded(purchase);
            }

            @Override
            public void onDataNotAvailable() {
                // no remote data planned yet
            }
        });
    }

    @Override
    public void savePurchase(@NonNull Purchase purchase) {
        checkNotNull(purchase);
        mPurchasesLocalDataSource.savePurchase(purchase);
        //mPurchasesRemoteDataSource.savePurchase(purchase);

        if (mCachedPurchases == null) {
            mCachedPurchases = new LinkedHashMap<>();
        }
        mCachedPurchases.put(purchase.getId(), purchase);
    }

    @Override
    public void refreshPurchases() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllPurchases() {
        mPurchasesLocalDataSource.deleteAllPurchases();

        if (mCachedPurchases == null) {
            mCachedPurchases = new LinkedHashMap<>();
        }

        mCachedPurchases.clear();
    }

    @Override
    public void deletePurchase(@NonNull String purchaseId) {
        mPurchasesLocalDataSource.deletePurchase(checkNotNull(purchaseId));

        mCachedPurchases.remove(purchaseId);
    }

    @Nullable
    private Purchase getPurchaseWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedPurchases == null || mCachedPurchases.isEmpty()) {
            return null;
        } else {
            return mCachedPurchases.get(id);
        }
    }
}
