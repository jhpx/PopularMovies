package com.donneryst.popularmovies;

/**
 * Some useful Constants related to the URLs
 *
 * Created by jhpx on 2015/11/23.
 */
public class URLs {

    // Urls
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    public static final String DISCOVER_BASE_URL = "http://api.themoviedb.org/3/discover/movie";

    public static String getMovieVideosURL(int id) {
        return "http://api.themoviedb.org/3/movie/" + id + "/videos";
    }

    public static String getMovieReviewsURL(int id) {
        return "http://api.themoviedb.org/3/movie/" + id + "/reviews";
    }
    // Params
    public static final String SORT_PARAM = "sort_by";
    public static final String LANGUAGE_PARAM = "language";
    public static final String APIKEY_PARAM = "api_key";

    // Other Sites
    public static final String YOU_TUBE_VIDEO_URL = "http://www.youtube.com/watch?v=";

}
