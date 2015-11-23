package com.donneryst.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jhpx on 2015/11/22.
 */
public class Movie implements Parcelable {
    boolean adult;
    String backdrop_path;
    int[] genre_ids;
    int id;
    String original_language;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    double popularity;
    String title;
    boolean video;
    double vote_average;
    int vote_count;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Parcel in) {
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        in.readIntArray(genre_ids);
        id = in.readInt();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        popularity = in.readDouble();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        vote_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
