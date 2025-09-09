// File: app/src/main/java/com/example/moviemate/model/MovieResponse.java
package com.example.moviemate.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public List<Movie> getResults() {
        return results;
    }
}