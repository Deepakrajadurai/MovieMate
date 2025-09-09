// File: app/src/main/java/com/example/moviemate/repository/MovieRepository.java
package com.projects.moviemates.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.projects.moviemates.model.Movie;
import com.projects.moviemates.model.MovieDetail;
import com.projects.moviemates.model.MovieResponse;
import com.projects.moviemates.model.VideoResponse;
import com.projects.moviemates.model.WatchProviders;
import com.projects.moviemates.network.MovieApiService;
import com.projects.moviemates.network.RetrofitClient;
import com.projects.moviemates.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final MovieApiService apiService;

    public MovieRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<Movie>> getPopularMovies(String region) {
        MutableLiveData<List<Movie>> data = new MutableLiveData<>();
        apiService.getPopularMovies(Constants.TMDB_API_KEY, region).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getResults());
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<VideoResponse> getMovieVideos(int movieId) {
        MutableLiveData<VideoResponse> data = new MutableLiveData<>();
        apiService.getMovieVideos(movieId, Constants.TMDB_API_KEY).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if(response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    // Add other repository methods here for getMovieDetails, getWatchProviders, etc.
}