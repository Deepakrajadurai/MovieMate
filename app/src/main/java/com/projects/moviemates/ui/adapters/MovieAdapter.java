// File: app/src/main/java/com/projects/moviemates/ui/adapters/MovieAdapter.java
package com.projects.moviemates.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// 1. REMOVED the Glide import
// import com.bumptech.glide.Glide;

// 2. ADDED the Picasso import
import com.squareup.picasso.Picasso;

import com.projects.moviemates.R;
import com.projects.moviemates.model.Movie;
import com.projects.moviemates.ui.MovieDetailActivity;
import com.projects.moviemates.utils.Constants;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final Context context;
    private List<Movie> movieList = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());

        String posterUrl = Constants.TMDB_IMAGE_BASE_URL_W500 + movie.getPosterPath();

        // 3. REPLACED the Glide call with the Picasso call
        Picasso.get()
                .load(posterUrl)
                .placeholder(R.color.material_grey_600) // This works the same in Picasso
                .error(R.drawable.ic_image_error) // Good practice: add an error image
                .into(holder.poster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
        }
    }
}