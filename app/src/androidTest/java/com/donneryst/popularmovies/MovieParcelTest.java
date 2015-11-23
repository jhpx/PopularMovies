package com.donneryst.popularmovies;

import android.os.Parcel;
import android.test.suitebuilder.annotation.SmallTest;

import com.donneryst.popularmovies.model.Movie;
import com.google.gson.Gson;

import junit.framework.TestCase;

/**
 * Created by jhpx on 2015/11/23.
 */
public class MovieParcelTest extends TestCase {

    @SmallTest
    public void testMovieParcelable() {
        String testJSON = "{\"adult\":false,\"backdrop_path\":\"/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg\",\"genre_ids\":[28,12,878],\"id\":102899,\"original_language\":\"en\",\"original_title\":\"Ant-Man\",\"overview\":\"Armed with the astonishing ability to shrink in scale but increase in strength, con-man Scott Lang must embrace his inner-hero and help his mentor, Dr. Hank Pym, protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world.\",\"release_date\":\"2015-08-14\",\"poster_path\":\"/D6e8RJf2qUstnfkTslTXNTUAlT.jpg\",\"popularity\":77.406248,\"title\":\"Ant-Man\",\"video\":false,\"vote_average\":7.0,\"vote_count\":1617}";
        Movie test = new Gson().fromJson(testJSON, Movie.class);

        // Obtain a Parcel object and write the parcelable object to it:
        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);

        // After you're done with writing, you need to reset the parcel for reading:
        parcel.setDataPosition(0);

        // Reconstruct object from parcel and asserts:
        Movie createdFromParcel = Movie.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
}