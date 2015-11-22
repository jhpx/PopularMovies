package com.donneryst.popularmovies.utils;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jhpx on 2015/11/22.
 */
public class HttpUtils {

    /**
     * Build Get/Post Uri
     *
     * @param url
     * @param params
     * @return
     */
    public static Uri buildUri(String url, Map params) {
        Uri output = null;
        Uri.Builder builder = Uri.parse(url).buildUpon();

        if (params != null) {
            Iterator entries = params.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                entries.remove(); // avoids a ConcurrentModificationException
            }
            output = builder.build();
        }

        return output;
    }

    /**
     * Build Post Parameters
     *
     * @param params
     * @return
     */
    public static String buildPostParameters(Map params) {
        String output = null;
        Uri.Builder builder = new Uri.Builder();

        if (params != null) {
            Iterator entries = params.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                entries.remove(); // avoids a ConcurrentModificationException
            }
            output = builder.build().getEncodedQuery();
        }

        return output;
    }

    /**
     * Make Http Request
     *
     * @param method
     * @param apiAddress
     * @param mimeType
     * @param requestBody
     * @return
     * @throws IOException
     */

    public static URLConnection makeRequest(String method, String apiAddress, String mimeType, String requestBody) throws IOException {
        URL url = new URL(apiAddress);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(!method.equals("GET"));
        urlConnection.setRequestMethod(method);

        //urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
        urlConnection.setRequestProperty("Content-Type", mimeType);
        OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(requestBody);
        writer.flush();
        writer.close();
        outputStream.close();

        urlConnection.connect();

        return urlConnection;
    }
}
