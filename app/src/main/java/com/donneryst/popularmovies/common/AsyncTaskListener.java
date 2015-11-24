package com.donneryst.popularmovies.common;

public interface AsyncTaskListener<Result> {
    void onError(Exception e);

    void onSuccess(Result result);

    void onFinally();
}
