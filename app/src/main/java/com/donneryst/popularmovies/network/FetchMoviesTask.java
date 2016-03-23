package com.donneryst.popularmovies.network;

import android.content.Context;
import android.net.Uri;

import com.donneryst.popularmovies.BuildConfig;
import com.donneryst.popularmovies.URLs;
import com.donneryst.popularmovies.model.Movie;
import com.donneryst.popularmovies.model.Result;
import com.donneryst.popularmovies.util.AppPreferences;
import com.donneryst.popularmovies.util.AppPreferencesHelper;
import com.donneryst.popularmovies.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neovisionaries.i18n.LanguageCode;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.Locale;

/**
 * The task is used for downloading all the discovery movies' specifics from theMovieDB API.
 *
 * Author: jhpx
 * Create: 2015/11/22.
 */
public class FetchMoviesTask extends CommonHttpTask<Void, Void, Result<Movie>> {

    public FetchMoviesTask(Context context, AsyncTaskListener<Result<Movie>> listener) {
        super(context, listener);
    }

    @Override
    protected Result<Movie> doInBackground(Void... params) {
        AppPreferences appPrefs = AppPreferencesHelper.getInstance(getContext()).getAppPreferences();
        String sort_by_method = appPrefs.getSortBy();
        String language = appPrefs.getMovieLanguage();
        if (language==null||language.isEmpty()){
            language = Locale.getDefault().getLanguage();
        }
       // When default language is not supported, set it to English
        if (LanguageCode.getByCode(language) == null)
            language = "en";

        // Construct the Uri for 'The Movie Database' query
        // Possible parameters are avaiable at TMDb's forecast API page, at
        // http://docs.themoviedb.apiary.io/#reference/discover
        Uri builtUri = Uri.parse(URLs.DISCOVER_BASE_URL).buildUpon()
                .appendQueryParameter(URLs.SORT_PARAM, sort_by_method)
                .appendQueryParameter(URLs.LANGUAGE_PARAM, language)
                .appendQueryParameter(URLs.APIKEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        Logger.d("GET " + builtUri.toString());

        // Try to make http connection and get response
        try {
            String response = new String(HttpUtil.getConnectionResponse(builtUri, "GET"));
            Logger.v(response);
            Type listType = new TypeToken<Result<Movie>>() {
            }.getType();
            mResult = new Gson().fromJson(response, listType);
        } catch (Exception e) {
            setException(e);
        }

        return mResult;
    }

}

