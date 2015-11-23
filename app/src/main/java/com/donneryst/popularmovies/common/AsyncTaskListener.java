package com.donneryst.popularmovies.common;

public interface AsyncTaskListener<TResult> {
    void onError(Exception e);

    void onSuccess(TResult result);

    void onFinally();
}
