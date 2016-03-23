package com.donneryst.popularmovies.util;

import com.donneryst.popularmovies.R;

import net.orange_box.storebox.annotations.method.DefaultValue;
import net.orange_box.storebox.annotations.method.KeyByResource;

/**
 * The interface is used in SharedPreferences access
 *
 * Author: jhpx
 * Create: 2016/3/23
 */
public interface AppPreferences {

    @KeyByResource(R.string.pref_sort_by_key)
    @DefaultValue(R.string.pref_sort_by_popularity_desc)
    String getSortBy();

    @KeyByResource(R.string.pref_movie_language_key)
    String getMovieLanguage();
}
