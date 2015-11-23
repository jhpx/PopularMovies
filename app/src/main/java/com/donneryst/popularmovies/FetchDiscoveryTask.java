package com.donneryst.popularmovies;

import android.net.Uri;
import android.util.Log;

import com.donneryst.popularmovies.common.AsyncTaskListener;
import com.donneryst.popularmovies.common.CommonHttpTask;
import com.donneryst.popularmovies.constants.URLs;
import com.donneryst.popularmovies.model.Movie;
import com.donneryst.popularmovies.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jhpx on 2015/11/22.
 */
public class FetchDiscoveryTask extends CommonHttpTask<Void, Void, List<Movie>> {
    private FetchDiscoveryTask() {
    }

    public FetchDiscoveryTask(AsyncTaskListener<List<Movie>> listner) {
        this.listener = listner;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {

        // Construct the Uri for 'The Movie Database' query
        // Possible parameters are avaiable at TMDb's forecast API page, at
        // http://docs.themoviedb.apiary.io/#reference/discover
        final String THEMOVIEDB_BASE_URL = URLs.DISCOVER_BASE_URL;
        final String SORT_PARAM = "sort_by";
        final String APPID_PARAM = "api_key";

        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, "popularity.desc")
                .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
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
        final String TMDB_PAGE = "page";

        JSONObject movieJson = (JSONObject) new JSONTokener(movieJsonStr).nextValue();
        String movieArray = movieJson.getString(TMDB_RESULT);

        Type listType = new TypeToken<List<Movie>>() {
        }.getType();
        return new Gson().fromJson(movieArray, listType);
    }
}

