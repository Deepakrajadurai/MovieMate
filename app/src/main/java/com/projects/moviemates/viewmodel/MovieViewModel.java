// File: app/src/main/java/com/example/moviemate/viewmodel/MovieViewModel.java
package com.projects.moviemates.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.projects.moviemates.model.Movie;
import com.projects.moviemates.model.VideoResponse;
import com.projects.moviemates.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;

    public MovieViewModel() {
        movieRepository = new MovieRepository();
    }

    public LiveData<List<Movie>> getPopularMovies(String region) {
        return movieRepository.getPopularMovies(region);
    }

    public LiveData<VideoResponse> getMovieVideos(int movieId) {
        return movieRepository.getMovieVideos(movieId);
    }

    // Add other ViewModel methods here
}