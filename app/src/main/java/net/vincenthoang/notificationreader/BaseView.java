package net.vincenthoang.notificationreader;

/**
 * Created by vincenthoang on 3/7/18.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
