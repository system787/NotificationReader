package net.vincenthoang.notificationreader.purchases.domain.filter;

import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.List;

/**
 * Created by vincenthoang on 3/7/18.
 */

public interface PurchasesFilter {
    List<Purchase> filter(List<Purchase> purchases);
}
