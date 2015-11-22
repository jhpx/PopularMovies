package com.donneryst.popularmovies.common;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.donneryst.popularmovies.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jhpx on 2015/11/23.
 */
public class CommonTask extends AsyncTask<Uri, Void, AsyncTaskResult<String>> {

    protected final String LOG_TAG = this.getClass().getSimpleName();

    public CommonTask() {
        super();
    }

    @Override
    protected AsyncTaskResult<String> doInBackground(Uri... params) {
        // If there's no Uri, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String result = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String THEMOVIEDB_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie";
            final String SORT_PARAM = "sort_by=";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM,"popularity.desc")
                    .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.d(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            result = buffer.toString();

            Log.d(LOG_TAG, "Forecast string: " + result);
            return new AsyncTaskResult<>(result);
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return new AsyncTaskResult<>(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    return new AsyncTaskResult<>(e);
                }
            }
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<String> stringAsyncTaskResult) {
        super.onPostExecute(stringAsyncTaskResult);
    }
}
