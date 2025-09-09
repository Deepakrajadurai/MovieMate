// File: src/main/java/com/example/moviemate/model/Movie.java
package com.example.moviemate.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    // Fields, Getters, Parcelable implementation... (Identical to the previous example)
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    // ... add more fields like release_date, vote_average etc.

    // Getters and Setters...

    // Parcelable implementation...
    protected Movie(Parcel in) { /* ... */ }
    public static final Creator<Movie> CREATOR = new Creator<Movie>() { /* ... */ };
    @Override public int describeContents() { return 0; }
    @Override public void writeToParcel(Parcel dest, int flags) { /* ... */ }
}

// File: src/main/java/com/example/moviemate/model/MovieResponse.java
package com.example.moviemate.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> results;
    public List<Movie> getResults() { return results; }
}