package com.donneryst.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.donneryst.popularmovies.common.AsyncTaskListener;
import com.donneryst.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainDiscoveryFragment extends Fragment implements AsyncTaskListener<List<Movie>> {

    protected final String LOG_TAG = MainDiscoveryFragment.class.getSimpleName();

    private MovieAdapter mMovieAdapter;

    private FetchDiscoveryTask task;


    public MainDiscoveryFragment() {
    }

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
        mMovieAdapter = new MovieAdapter(getActivity(), movies);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(mMovieAdapter);

        return rootView;
    }

    private void pullMovieDiscovery() {
        task = new FetchDiscoveryTask(this);
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
    public void onSuccess(List<Movie> result) {
        mMovieAdapter.clear();
        for (Movie mov: result) {
            mMovieAdapter.add(mov);
        }
    }

    @Override
    public void onFinally() {
        task = null; //prevents task from being leaked
    }
}
