package com.donneryst.popularmovies.network;

public interface AsyncTaskListener<Result> {
    void onError(Exception e);

    void onSuccess(Result result);

    void onFinally();
}
