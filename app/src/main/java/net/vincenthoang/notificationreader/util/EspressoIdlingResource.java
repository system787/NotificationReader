package net.vincenthoang.notificationreader.util;

/**
 * Created by vincenthoang on 3/7/18.
 */

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static final SimpleCountingIdlingResource DEFAULT_INSTANCE =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        DEFAULT_INSTANCE.increment();
    }

    public static void decrement() {
        DEFAULT_INSTANCE.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return DEFAULT_INSTANCE;
    }
}
