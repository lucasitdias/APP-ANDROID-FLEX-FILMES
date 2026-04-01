package com.example.flexfilmes;

// Imports
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

// Adapter de filmes
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    // Variáveis
    private Context context;
    private List<Movie> movieList;

    // Construtor
    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    // Criar item
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    // Vincular dados
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Dados
        holder.txtTitle.setText(movie.getTitle());
        holder.txtDescription.setText(movie.getDescription());
        holder.imgMovie.setImageResource(movie.getImageResId());

        // Abrir detalhes
        View.OnClickListener openDetail = v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());
            context.startActivity(intent);
        };

        // Clique imagem
        holder.imgMovie.setOnClickListener(openDetail);

        // Clique play
        if (holder.btnPlayOverlay != null) {
            holder.btnPlayOverlay.setOnClickListener(openDetail);
        }
    }

    // Quantidade de itens
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // ViewHolder
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        // Componentes
        ImageView imgMovie, btnPlayOverlay;
        TextView txtTitle, txtDescription;

        // Construtor ViewHolder
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind das views
            imgMovie = itemView.findViewById(R.id.imgMovie);
            btnPlayOverlay = itemView.findViewById(R.id.btnPlayOverlay);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}