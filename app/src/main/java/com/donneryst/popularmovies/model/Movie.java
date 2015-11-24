package com.donneryst.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Movie Bean for TheMovieDB
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

    public String getOriginal_title() {
        return original_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Parcel in) {
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        genre_ids = in.createIntArray();
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

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (adult != movie.adult) return false;
        if (id != movie.id) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (video != movie.video) return false;
        if (Double.compare(movie.vote_average, vote_average) != 0) return false;
        if (vote_count != movie.vote_count) return false;
        if (backdrop_path != null ? !backdrop_path.equals(movie.backdrop_path) : movie.backdrop_path != null)
            return false;
        if (!Arrays.equals(genre_ids, movie.genre_ids)) return false;
        if (original_language != null ? !original_language.equals(movie.original_language) : movie.original_language != null)
            return false;
        if (original_title != null ? !original_title.equals(movie.original_title) : movie.original_title != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (release_date != null ? !release_date.equals(movie.release_date) : movie.release_date != null)
            return false;
        if (poster_path != null ? !poster_path.equals(movie.poster_path) : movie.poster_path != null)
            return false;
        return !(title != null ? !title.equals(movie.title) : movie.title != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (adult ? 1 : 0);
        result = 31 * result + (backdrop_path != null ? backdrop_path.hashCode() : 0);
        result = 31 * result + (genre_ids != null ? Arrays.hashCode(genre_ids) : 0);
        result = 31 * result + id;
        result = 31 * result + (original_language != null ? original_language.hashCode() : 0);
        result = 31 * result + (original_title != null ? original_title.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (video ? 1 : 0);
        temp = Double.doubleToLongBits(vote_average);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + vote_count;
        return result;
    }
}
