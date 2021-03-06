package com.donneryst.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.donneryst.popularmovies.R;
import com.donneryst.popularmovies.activity.MovieDetailActivity;
import com.donneryst.popularmovies.adapter.MovieGridAdapter;
import com.donneryst.popularmovies.model.Movie;
import com.donneryst.popularmovies.model.Result;
import com.donneryst.popularmovies.network.CommonHttpTask;
import com.donneryst.popularmovies.network.FetchMoviesTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment implements CommonHttpTask.AsyncTaskListener<Result<Movie>> {

    private MovieGridAdapter mMovieGridAdapter;

    private FetchMoviesTask task;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_discovery_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            pullMovieDiscovery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // Create some dummy data.
        List<Movie> movies = new ArrayList<>();

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mMovieGridAdapter = new MovieGridAdapter(getActivity(), movies);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(mMovieGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = mMovieGridAdapter.getItem(position);
                MovieDetailActivity.startActivity(getActivity(), movie);
            }
        });

        return rootView;
    }

    private void pullMovieDiscovery() {
        task = new FetchMoviesTask(getContext(),this);
        task.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        pullMovieDiscovery();
    }

    @Override
    public void onDestroy() {
        if (task != null) {
            task.setListener(null); //prevent leaking this Fragment
        }
        super.onDestroy();
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Result<Movie> result) {
        mMovieGridAdapter.clear();
        if (result!=null)
        for (Movie mov: result.getResults()) {
            mMovieGridAdapter.add(mov);
        }
    }

    @Override
    public void onFinally() {
        task = null; //prevents task from being leaked
    }
}
