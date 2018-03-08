package net.vincenthoang.notificationreader;

/**
 * Created by vincenthoang on 3/7/18.
 */

public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <V extends UseCase.ResponseValue> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValue> void onError(final UseCase.UseCaseCallback<V> useCaseCallback);
}
