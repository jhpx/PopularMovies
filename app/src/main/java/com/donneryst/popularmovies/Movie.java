package com.donneryst.popularmovies;

/**
 * Created by jhpx on 2015/11/22.
 */
public class Movie {
    String versionName;
    String versionNumber;
    int image; // drawable reference id

    public Movie(String vName, String vNumber, int image)
    {
        this.versionName = vName;
        this.versionNumber = vNumber;
        this.image = image;
    }
}
