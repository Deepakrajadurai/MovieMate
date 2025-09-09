// File: app/src/main/java/com/example/moviemate/model/MovieDetail.java
package com.example.moviemate.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetail extends Movie {
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("runtime")
    private int runtime; // in minutes

    public List<Genre> getGenres() { return genres; }
    public int getRuntime() { return runtime; }

    public static class Genre {
        @SerializedName("name")
        private String name;
        public String getName() { return name; }
    }
}