package com.projects.moviemates.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView; // ✅ Import PlayerView

import com.projects.moviemates.databinding.ActivityMovieDetailBinding;
import com.projects.moviemates.model.Movie;
import com.projects.moviemates.model.VideoResponse;
import com.projects.moviemates.sensors.AccelerometerHelper;
import com.projects.moviemates.utils.Constants;
import com.projects.moviemates.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity implements AccelerometerHelper.MotionListener {

    private ActivityMovieDetailBinding binding;
    private MovieViewModel movieViewModel;
    private ExoPlayer player;
    private PlayerView playerView; // ✅ added explicit field
    private AccelerometerHelper accelerometerHelper;
    private boolean isPlayerInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ✅ Initialize playerView from binding
        playerView = binding.videoPlayerView;

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        if (movie.getBackdropPath() != null && !movie.getBackdropPath().isEmpty()) {
            String backdropUrl = Constants.TMDB_IMAGE_BASE_URL_W500 + movie.getBackdropPath();
            // ✅ Find artworkView inside PlayerView
            ImageView artworkView = playerView.findViewById(androidx.media3.ui.R.id.exo_artwork);
            if (artworkView != null) {
                Picasso.get()
                        .load(backdropUrl)
                        .into(artworkView);
            }
        }
    }

    private void observeViewModel(int movieId) {
        movieViewModel.getMovieVideos(movieId).observe(this, videoResponse -> {
            if (videoResponse != null && !videoResponse.getResults().isEmpty()) {
                for (VideoResponse.Video video : videoResponse.getResults()) {
                    if ("Trailer".equalsIgnoreCase(video.getType()) && "YouTube".equalsIgnoreCase(video.getSite())) {
                        initializePlayer(video.getKey());
                        return;
                    }
                }
            }
        });
    }

    private void initializePlayer(String videoKey) {
        if (isPlayerInitialized) return;

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player); // ✅ use playerView instead of binding
        player.prepare();
        player.setPlayWhenReady(false);
        player.setVolume(0f);
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
            if (player != null) {
                player.pause();
            }
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
