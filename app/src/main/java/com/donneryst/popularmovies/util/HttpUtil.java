package com.donneryst.popularmovies.util;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


/**
 * Some Utils about HTTP
 *
 * Created by jhpx on 2015/11/22.
 */
public class HttpUtil {

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
     * @param contentType
     * @param requestBody
     * @return
     * @throws IOException
     */

    public static HttpURLConnection makeRequest(String method, String apiAddress, String contentType, String requestBody) throws IOException {
        URL url = new URL(apiAddress);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(!method.equals("GET"));
        urlConnection.setRequestMethod(method);

        //urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
        if (contentType != null)
            urlConnection.setRequestProperty("Content-Type", contentType);

        if (requestBody != null && requestBody.length() > 0) {
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();
        }

        urlConnection.connect();

        return urlConnection;
    }


    /**
     * Make http connection and get response in byte array format
     *
     * @param uri
     * @param method
     * @return
     * @throws Exception
     */
    public static byte[] getConnectionResponse(Uri uri, String method) throws Exception {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Catch apiAddress and requestBody from Uri
            final String address = uri.getScheme() + "://" + uri.getAuthority() + uri.getPath();
            final String query = uri.getQuery();
            if (method.equals("GET"))
                urlConnection = makeRequest(method, uri.toString(), null, null);
            else
                urlConnection = makeRequest(method, address, null, query);


            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                return readFully(inputStream, -1, true);
            } else {
                throw new IOException("Http Status " + responseCode);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * This snippet is taken from the sun.misc.IOUtils class. It's nearly twice as fast as the common implementation using ByteBuffers:
     *
     * @param is
     * @param length
     * @param readAll
     * @return
     * @throws IOException
     */
    private static byte[] readFully(InputStream is, int length, boolean readAll)
            throws IOException {
        byte[] output = {};
        if (length == -1) length = Integer.MAX_VALUE;
        int pos = 0;
        while (pos < length) {
            int bytesToRead;
            if (pos >= output.length) { // Only expand when there's no room
                bytesToRead = Math.min(length - pos, output.length + 1024);
                if (output.length < pos + bytesToRead) {
                    output = Arrays.copyOf(output, pos + bytesToRead);
                }
            } else {
                bytesToRead = output.length - pos;
            }
            int cc = is.read(output, pos, bytesToRead);
            if (cc < 0) {
                if (readAll && length != Integer.MAX_VALUE) {
                    throw new EOFException("Detect premature EOF");
                } else {
                    if (output.length != pos) {
                        output = Arrays.copyOf(output, pos);
                    }
                    break;
                }
            }
            pos += cc;
        }
        return output;
    }


}
