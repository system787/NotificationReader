package net.vincenthoang.notificationreader.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.List;

/**
 * Created by vincenthoang on 3/8/18.
 */

@Dao
public interface PurchaseDao {

    /**
     * Selects all purchases from the table
     *
     * @return all purchases
     */
    @Query("SELECT * FROM purchases")
    List<Purchase> getPurchases();

    /**
     * Select a purchase by its id
     *
     * @param purchaseId the purchase id
     * @return the purchase with the purchaseId
     */
    @Query("SELECT * FROM purchases WHERE entryid = :purchaseId")
    Purchase getPurchaseById(String purchaseId);

    /**
     * Inserts a task in the database. If task exists, replace it
     *
     * @param purchase to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPurchase(Purchase purchase);

    /**
     * Updates a task
     *
     * @param purchase to be updated
     * @return the number of purchases updated. Should always be 1 due to UUID
     */
    @Update
    int updatePurchase(Purchase purchase);

    /**
     * Updates the purchase amount
     *
     * @param purchaseId id of the purchase
     * @param amount     the amount of the purchase
     */
    @Query("UPDATE purchases SET amount = :amount WHERE entryid = :purchaseId")
    void updateAmount(String purchaseId, int amount);

    /**
     * Updates the purchase description
     *
     * @param purchaseId        id of the purchase
     * @param updateDescription description of the purchase
     */
    @Query("UPDATE purchases SET description = :updateDescription WHERE entryid = :purchaseId")
    void updateDescription(String purchaseId, String updateDescription);

    /**
     * Updates the purchase type
     *
     * @param purchaseId id of the purchase
     * @param updateType description of the purchase
     */
    @Query("UPDATE purchases SET type = :updateType WHERE entryid = :purchaseId")
    void updateType(String purchaseId, String updateType);

    /**
     * Deletes a purchase by id
     *
     * @return the number of purchases deleted. Should always be 1
     */
    @Query("DELETE FROM purchases WHERE entryid = :purchaseId")
    int deletePurchaseById(String purchaseId);

    /**
     * Deletes all purchases
     */
    @Query("DELETE FROM purchases")
    void deletePurchases();
}
