package com.example.flexfilmes;

// IMPORTS
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// =====================================
// ADAPTER DOS FILMES
// =====================================
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    // CONSTRUTOR
    public MovieAdapter(Context context, List<Movie> movieList) {

        this.context = context;
        this.movieList = movieList;

    }

    // CRIAR VIEW
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(view);

    }

    // BIND
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        holder.txtTitle.setText(movie.getTitle());

        holder.txtDescription.setText(movie.getDescription());

        holder.imgMovie.setImageResource(movie.getImageResId());

        // =====================================
        // ABRIR DETALHES
        // =====================================

        View.OnClickListener openDetail = v -> {

            Intent intent = new Intent(context, MovieDetailActivity.class);

            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());
            intent.putExtra("year", movie.getYear());
            intent.putExtra("age", movie.getAgeRating());

            context.startActivity(intent);

        };

        holder.imgMovie.setOnClickListener(openDetail);

        if(holder.btnPlayOverlay != null){

            holder.btnPlayOverlay.setOnClickListener(openDetail);

        }

    }

    // QUANTIDADE
    @Override
    public int getItemCount() {

        return movieList.size();

    }

    // =====================================
    // VIEWHOLDER
    // =====================================

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;
        ImageView btnPlayOverlay;

        TextView txtTitle;
        TextView txtDescription;

        public MovieViewHolder(@NonNull View itemView) {

            super(itemView);

            imgMovie = itemView.findViewById(R.id.imgMovie);

            txtTitle = itemView.findViewById(R.id.txtTitle);

            txtDescription = itemView.findViewById(R.id.txtDescription);

            // CORREÇÃO CRASH
            btnPlayOverlay = itemView.findViewById(R.id.btnPlayOverlay);

        }
    }
}