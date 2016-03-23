package com.donneryst.popularmovies.network;

import android.content.Context;
import android.net.Uri;

import com.donneryst.popularmovies.BuildConfig;
import com.donneryst.popularmovies.URLs;
import com.donneryst.popularmovies.model.Result;
import com.donneryst.popularmovies.model.Video;
import com.donneryst.popularmovies.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The task is used for downloading all the videos' specifics of a particular movie from theMovieDB API.
 *
 * Author: jhpx
 * Create: 2016/3/22
 */
public class FetchVideosTask extends CommonHttpTask<Integer, Void, List<Video>> {

    public FetchVideosTask(Context context, AsyncTaskListener<List<Video>> listener) {
        super(context, listener);
    }

    @Override
    protected List<Video> doInBackground(Integer... params) {
        if (params.length <= 0 || params[0] == null)
            return null;

        // Construct the Uri for 'The Movie Database' query
        // Possible parameters are avaiable at TMDb's forecast API page, at
        // http://docs.themoviedb.apiary.io/#reference/discover
        Uri builtUri = Uri.parse(URLs.getMovieReviewsURL(params[0])).buildUpon()
                .appendQueryParameter(URLs.APIKEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        Logger.d("GET " + builtUri.toString());

        // Try to make http connection and get response
        try {
            String response = new String(HttpUtil.getConnectionResponse(builtUri, "GET"));
            Logger.v(response);
            Type listType = new TypeToken<Result<Video>>() {
            }.getType();
            mResult = new Gson().fromJson(response, listType);
        } catch (Exception e) {
            setException(e);
        }

        return mResult;
    }
}
