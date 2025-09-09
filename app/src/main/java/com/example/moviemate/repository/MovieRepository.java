// File: app/src/main/java/com/example/moviemate/repository/MovieRepository.java
package com.example.moviemate.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviemate.model.Movie;
import com.example.moviemate.model.MovieDetail;
import com.example.moviemate.model.MovieResponse;
import com.example.moviemate.model.VideoResponse;
import com.example.moviemate.model.WatchProviders;
import com.example.moviemate.network.MovieApiService;
import com.example.moviemate.network.RetrofitClient;
import com.example.moviemate.utils.Constants;

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