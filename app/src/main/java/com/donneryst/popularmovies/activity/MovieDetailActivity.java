package com.donneryst.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.donneryst.popularmovies.R;
import com.donneryst.popularmovies.model.Movie;

/**
 *
 * The activity is used for setting a detail fragment and showing movie detail data.
 *
 * Created by jhpx on 2015/11/22.
 */
public class MovieDetailActivity extends AppCompatActivity {

    /**
     * Provide this method as an interface for anyone who want to start this Activity
     *
     * @param context
     */
    public static void startActivity(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(Movie.class.getName(), movie);
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
