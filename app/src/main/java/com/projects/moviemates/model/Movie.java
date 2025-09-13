// File: app/src/main/java/com/projects/moviemates/model/Movie.java
package com.projects.moviemates.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    // ✅ ADD THIS FIELD
    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private double voteAverage;

    // You might have other fields like release_date, etc.

    // Constructor, Getters, and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    // ✅ ADD THIS GETTER (This is the method the error is about)
    public String getBackdropPath() {
        return backdropPath;
    }

    // ✅ ADD THIS SETTER (Good practice)
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }


    // --- Parcelable Implementation ---

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString(); // ✅ READ THE NEW FIELD FROM THE PARCEL
        voteAverage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(backdropPath); // ✅ WRITE THE NEW FIELD TO THE PARCEL
        dest.writeDouble(voteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}