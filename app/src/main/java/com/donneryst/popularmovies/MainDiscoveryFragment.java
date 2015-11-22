package com.donneryst.popularmovies;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainDiscoveryFragment extends Fragment {

    private MovieAdapter mMovieAdapter;

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
        Movie[] movies = {
                new Movie("Cupcake", "1.5", R.drawable.loading),
                new Movie("Donut", "1.6", R.drawable.loading),
                new Movie("Eclair", "2.0-2.1", R.drawable.loading),
                new Movie("Froyo", "2.2-2.2.3", R.drawable.loading),
                new Movie("GingerBread", "2.3-2.3.7", R.drawable.loading),
                new Movie("Honeycomb", "3.0-3.2.6", R.drawable.loading),
                new Movie("Ice Cream Sandwich", "4.0-4.0.4", R.drawable.loading),
                new Movie("Jelly Bean", "4.1-4.3.1", R.drawable.loading),
                new Movie("KitKat", "4.4-4.4.4", R.drawable.loading),
                new Movie("Lollipop", "5.0-5.1.1", R.drawable.loading)
        };

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mMovieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(mMovieAdapter);

        return rootView;
    }
}
