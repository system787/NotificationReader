package net.vincenthoang.notificationreader.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import net.vincenthoang.notificationreader.data.source.PurchasesDataSource;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;
import net.vincenthoang.notificationreader.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincenthoang on 3/8/18.
 */

public class PurchasesLocalDataSource implements PurchasesDataSource {

    private static volatile PurchasesLocalDataSource INSTANCE;

    private PurchaseDao mPurchaseDao;

    private AppExecutors mAppExecutors;

    public PurchasesLocalDataSource(@NonNull PurchaseDao purchaseDao, @NonNull AppExecutors appExecutors) {
        mPurchaseDao = purchaseDao;
        mAppExecutors = appExecutors;
    }

    public static PurchasesLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull PurchaseDao purchaseDao) {
        if (INSTANCE == null) {
            synchronized (PurchasesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PurchasesLocalDataSource(purchaseDao, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getPurchases(@NonNull final LoadPurchasesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Purchase> purchases = mPurchaseDao.getPurchases();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (purchases.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onPurchasesLoaded(purchases);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPurchase(@NonNull final String purchaseId, @NonNull final GetPurchasesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Purchase purchase = mPurchaseDao.getPurchaseById(purchaseId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (purchase != null) {
                            callback.onPurchasesLoaded(purchase);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void savePurchase(@NonNull final Purchase purchase) {
        checkNotNull(purchase);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPurchaseDao.insertPurchase(purchase);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshPurchases() {
        // Not required because TasksRepository is already responsible for
        // handling the logic of refreshing the tasks from all available sources
    }

    @Override
    public void deleteAllPurchases() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mPurchaseDao.deletePurchases();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deletePurchase(@NonNull final String purchaseId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mPurchaseDao.deletePurchaseById(purchaseId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
