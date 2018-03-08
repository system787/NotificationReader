package net.vincenthoang.notificationreader.purchases.domain.filter;

import net.vincenthoang.notificationreader.purchases.PurchasesFilterType;
import net.vincenthoang.notificationreader.purchases.domain.model.Purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincenthoang on 3/7/18.
 */

public class FilterFactory {

    private static final Map<PurchasesFilterType, PurchasesFilter> mFilters = new HashMap<>();

    public FilterFactory() {
        mFilters.put(PurchasesFilterType.ALL_TRANSACTIONS, new FilterAllPurchaseFilter());
        mFilters.put(PurchasesFilterType.PURCHASES, new CompletedPurchasesFilter());
        mFilters.put(PurchasesFilterType.REFUNDS, new CompletedRefundsFilter());
    }

    public PurchasesFilter create(PurchasesFilterType filterType) {
        return mFilters.get(filterType);
    }
}
