package com.donneryst.popularmovies.model;

import java.util.List;

/**
 * Result Bean for TheMovieDB
 *
 * Author: jhpx
 * Create: 2016/3/23
 */
public class Result<T> {
    int page;
    List<T> results;
    int total_pages;
    int total_results;

    public List<T> getResults() {
        return results;
    }
}
