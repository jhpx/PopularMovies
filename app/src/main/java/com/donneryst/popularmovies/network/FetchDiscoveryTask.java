package com.donneryst.popularmovies.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.donneryst.popularmovies.BuildConfig;
import com.donneryst.popularmovies.R;
import com.donneryst.popularmovies.URLs;
import com.donneryst.popularmovies.model.Movie;
import com.donneryst.popularmovies.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neovisionaries.i18n.LanguageCode;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

/**
 * Created by jhpx on 2015/11/22.
 */
public class FetchDiscoveryTask extends CommonHttpTask<Void, Void, List<Movie>> {
    private Context mContext;

    private FetchDiscoveryTask() {
    }

    public FetchDiscoveryTask(Context context, AsyncTaskListener<List<Movie>> listner) {
        this.listener = listner;
        this.mContext = context;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {

        // Construct the Uri for 'The Movie Database' query
        // Possible parameters are avaiable at TMDb's forecast API page, at
        // http://docs.themoviedb.apiary.io/#reference/discover
        final String THEMOVIEDB_BASE_URL = URLs.DISCOVER_BASE_URL;
        final String SORT_PARAM = "sort_by";
        final String LANGUAGE_PARAM = "language";
        final String APIKEY_PARAM = "api_key";

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        String sort_by_method = sharedPrefs.getString(
                mContext.getString(R.string.pref_sort_by_key),
                mContext.getString(R.string.pref_sort_by_popularity_desc));
        String language = sharedPrefs.getString(
                mContext.getString(R.string.pref_movie_language_key), Locale.getDefault().getLanguage());
        // When default language is not supported, set it to English
        if (LanguageCode.getByCode(language) == null)
            language = "en";

        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, sort_by_method)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(APIKEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();


        // Try to make http connection and get response
        try {
            String response = new String(HttpUtils.getConnectionResponse(builtUri, "GET", TAG));
            Log.d(TAG, response);
            result = getMovieDataFromJSON(response);


        } catch (Exception e) {
            this.e = e;
        }

        return result;
    }


    private List<Movie> getMovieDataFromJSON(String movieJsonStr) throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String TMDB_RESULT = "results";

        JSONObject movieJson = (JSONObject) new JSONTokener(movieJsonStr).nextValue();
        String movieArray = movieJson.getString(TMDB_RESULT);

        Type listType = new TypeToken<List<Movie>>() {
        }.getType();
        return new Gson().fromJson(movieArray, listType);
    }
}

