// File: app/src/main/java/com/example/moviemate/network/MovieApiService.java
package com.projects.moviemates.network;

import com.projects.moviemates.model.MovieDetail;
import com.projects.moviemates.model.MovieResponse;
import com.projects.moviemates.model.VideoResponse;
import com.projects.moviemates.model.WatchProviders;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("region") String region
    );

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByGenre(
            @Query("api_key") String apiKey,
            @Query("with_genres") int genreId
    );

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getMovieVideos(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/watch/providers")
    Call<WatchProviders> getWatchProviders(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );
}