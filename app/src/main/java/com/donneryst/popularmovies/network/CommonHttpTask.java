package com.donneryst.popularmovies.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Common AsyncTask class for http GET/POST task
 * Any Sub class should implement doInBackground() method itself
 *
 * Created by jhpx on 2015/11/23.
 */
public abstract class CommonHttpTask<Param, Progress, Result> extends AsyncTask<Param, Progress, Result> {

    private AsyncTaskListener<Result> mListener;
    private Context mContext;

    protected Result mResult;
    protected Exception mException;

    public CommonHttpTask(Context context, AsyncTaskListener<Result> listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onPostExecute(Result result) {
        this.mResult = result;
        dispatchResult();
    }

    private void dispatchResult() {
        if (mListener != null) {
            if (mException == null) { //handle success
                mListener.onSuccess(mResult);
            } else { //handle errors
                Logger.e(Log.getStackTraceString(mException));
                mListener.onError(mException);
            }
            mListener.onFinally();
        }
    }

    public void setListener(AsyncTaskListener<Result> listener) {
        this.mListener = listener;
        if (getStatus().equals(Status.FINISHED)) {
            dispatchResult();
        } else if (getStatus().equals(Status.RUNNING)) {
            onPreExecute();
        }
    }

    protected void setException(Exception exception) {
        this.mException = exception;
    }

    public Context getContext() {
        return mContext;
    }


    public interface AsyncTaskListener<Result> {
        void onError(Exception e);

        void onSuccess(Result result);

        void onFinally();
    }
}
