package net.vincenthoang.notificationreader.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

/**
 * Created by vincenthoang on 3/8/18.
 */

@Database(entities = {Purchase.class}, version = 1)
public abstract class PurchaseDatabase extends RoomDatabase {

    private static PurchaseDatabase INSTANCE;

    public abstract PurchaseDao purchaseDao();

    private static final Object sLock = new Object();

    public static PurchaseDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PurchaseDatabase.class, "purchases.db").build();
            }
            return INSTANCE;
        }
    }

}

