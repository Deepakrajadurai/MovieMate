// File: app/src/main/java/com/example/moviemate/ui/MovieDetailActivity.java
package com.example.moviemate.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moviemate.databinding.ActivityMovieDetailBinding;
import com.example.moviemate.model.Movie;
import com.example.moviemate.model.VideoResponse;
import com.example.moviemate.sensors.AccelerometerHelper;
import com.example.moviemate.utils.Constants;
import com.example.moviemate.viewmodel.MovieViewModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity implements AccelerometerHelper.MotionListener {

    private ActivityMovieDetailBinding binding;
    private MovieViewModel movieViewModel;
    private ExoPlayer player;
    private AccelerometerHelper accelerometerHelper;
    private boolean isPlayerInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        accelerometerHelper = new AccelerometerHelper(this, this);

        Movie movie = getIntent().getParcelableExtra("movie");
        if (movie != null) {
            populateUi(movie);
            observeViewModel(movie.getId());
        }
    }

    private void populateUi(Movie movie) {
        binding.collapsingToolbar.setTitle(movie.getTitle());
        binding.movieTitleDetail.setText(movie.getTitle());
        binding.movieOverview.setText(movie.getOverview());
        binding.movieRating.setText(String.format(Locale.getDefault(), "%.1f / 10", movie.getVoteAverage()));
    }

    private void observeViewModel(int movieId) {
        movieViewModel.getMovieVideos(movieId).observe(this, videoResponse -> {
            if (videoResponse != null && !videoResponse.getResults().isEmpty()) {
                // Find the first official trailer on YouTube
                for (VideoResponse.Video video : videoResponse.getResults()) {
                    if ("Trailer".equalsIgnoreCase(video.getType()) && "YouTube".equalsIgnoreCase(video.getSite())) {
                        initializePlayer(video.getKey());
                        return; // Stop after finding the first trailer
                    }
                }
            }
            // If no trailer, you could hide the player view or show a message
        });
    }

    private void initializePlayer(String videoKey) {
        if (isPlayerInitialized) return;

        // Build a MediaItem for a YouTube video using a custom extractor is complex.
        // For simplicity, we will load the thumbnail and handle play via Intent later.
        // A more advanced solution uses a YouTube extractor library.
        // Here, we just display the thumbnail.
        String thumbnailUrl = String.format(Constants.YOUTUBE_THUMBNAIL_URL, videoKey);
        // We'll create a new ImageView to overlay on top of the PlayerView for the thumbnail
        // For simplicity in this example, we'll just log it. The player setup is for local/direct URLs.

        // Let's assume you have a direct video link for demonstration of accelerometer
        // In a real app, you would use a library to get a direct link from the youtube key.
        // For now, let's just use the presence of a key to enable the player logic

        player = new ExoPlayer.Builder(this).build();
        binding.videoPlayerView.setPlayer(player);
        // MediaItem mediaItem = MediaItem.fromUri("YOUR_DIRECT_VIDEO_URL_HERE");
        // player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(false);
        player.setVolume(0f); // Muted
        isPlayerInitialized = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayerInitialized) accelerometerHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlayerInitialized) {
            accelerometerHelper.stop();
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }

    @Override
    public void onMotionDetected() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    @Override
    public void onIdle() {
        if (player != null && !player.isPlaying()) {
            player.play();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}