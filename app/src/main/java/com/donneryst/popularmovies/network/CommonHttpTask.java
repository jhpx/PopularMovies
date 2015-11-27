package com.donneryst.popularmovies.network;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Common AsyncTask class for http GET/POST task
 * Any Sub class should implement doInBackground() method itself
 *
 * Created by jhpx on 2015/11/23.
 */
public abstract class CommonHttpTask<Param, Progress, Result> extends AsyncTask<Param, Progress, Result> {

    protected final String TAG = this.getClass().getSimpleName();
    protected Exception e;
    protected AsyncTaskListener<Result> listener;
    protected Result result;

    @Override
    protected void onPostExecute(Result result) {
        this.result = result;
        dispatchResult();
    }

    private void dispatchResult() {
        if (listener != null) {
            if (e == null) { //handle success
                listener.onSuccess(result);
            } else { //handle errors
                Log.e(TAG,Log.getStackTraceString(e));
                listener.onError(e);
            }
            listener.onFinally();
        }
    }

    public void setListener(AsyncTaskListener<Result> listener) {
        this.listener = listener;
        if (getStatus().equals(Status.FINISHED)) {
            dispatchResult();
        } else if (getStatus().equals(Status.RUNNING)) {
            onPreExecute();
        }
    }

}
