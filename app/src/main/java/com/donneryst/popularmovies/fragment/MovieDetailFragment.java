package com.donneryst.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donneryst.popularmovies.R;
import com.donneryst.popularmovies.model.Movie;
import com.orhanobut.logger.Logger;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    static final String DETAIL_URI = "URI";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Movie.class.getName())) {
            Movie movie = intent.getParcelableExtra(Movie.class.getName());
            Logger.e(movie.getTitle());
            Logger.e(movie.getOriginal_title());
        }
        return  rootView;
    }
}
