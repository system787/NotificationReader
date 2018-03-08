package net.vincenthoang.notificationreader.purchases.domain.filter;

import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincenthoang on 3/7/18.
 */

class CompletedRefundsFilter implements PurchasesFilter {
    @Override
    public List<Purchase> filter(List<Purchase> purchases) {
        List<Purchase> filteredPurchases = new ArrayList<>();
        for (Purchase purchase : purchases) {
            if (purchase.getType().equalsIgnoreCase("REFUND")) {
                filteredPurchases.add(purchase);
            }
        }
        return filteredPurchases;
    }
}
