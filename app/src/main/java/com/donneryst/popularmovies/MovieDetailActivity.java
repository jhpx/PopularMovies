package com.donneryst.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.donneryst.popularmovies.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    static final String EXTRA_MOVIE = "com.donneryst.popularmovies.model.movie";

    /**
     * Provide this method as an interface for anyone who want to start this Activity
     *
     * @param context
     */
    public static void startActivity(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
