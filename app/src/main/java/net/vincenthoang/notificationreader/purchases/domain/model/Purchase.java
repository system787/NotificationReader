package net.vincenthoang.notificationreader.purchases.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Created by vincenthoang on 3/7/18.
 */


@Entity(tableName = "purchases")
public class Purchase {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;

    @NonNull
    @ColumnInfo(name = "amount")
    private final int mAmount;

    @Nullable
    @ColumnInfo(name = "type")
    private final String mType;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;


    /**
     * Constructor for purchase amount
     *
     * @param type        transaction type
     * @param description transaction description (location/store)
     * @param amount      transaction amount
     */
    @Ignore
    public Purchase(@Nullable String type, @Nullable String description, @NonNull int amount) {
        this(UUID.randomUUID().toString(), amount, type, description);
    }

    public Purchase(@NonNull String id, @NonNull int amount, String type, String description) {
        mId = id;
        mAmount = amount;
        mType = type;
        mDescription = description;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public int getAmount() {
        return mAmount;
    }

    @Nullable
    public String getType() {
        return mType;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (mAmount != purchase.mAmount) return false;
        if (!mId.equals(purchase.mId)) return false;
        if (mType != null ? !mType.equals(purchase.mType) : purchase.mType != null) return false;
        return mDescription != null ? mDescription.equals(purchase.mDescription) : purchase.mDescription == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mType, mDescription, mAmount);
    }
}
