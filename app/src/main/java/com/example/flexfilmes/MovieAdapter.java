package com.example.flexfilmes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Seção: Adapter de filmes
 *
 * Adapter responsável por popular os itens de filme na lista horizontal/vertical.
 * Proteções contra NPE e comportamento consistente de clique para abrir detalhe.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    // Seção: estado
    private final Context context;
    private final List<Movie> movieList;

    // Seção: construtor
    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    // Seção: criação do ViewHolder
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    // Seção: bind dos dados ao ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (movieList == null || position < 0 || position >= movieList.size()) return;
        Movie movie = movieList.get(position);
        if (movie == null) return;

        // Título e descrição
        if (holder.txtTitle != null) {
            holder.txtTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");
        }
        if (holder.txtDescription != null) {
            holder.txtDescription.setText(movie.getDescription() != null ? movie.getDescription() : "");
        }

        // Poster (proteção contra recursos inválidos)
        try {
            if (holder.imgMovie != null) {
                holder.imgMovie.setImageResource(movie.getImageResId());
            }
        } catch (Exception ignored) { }

        // Gênero
        String genre = (movie.getGenre() != null && !movie.getGenre().isEmpty()) ? movie.getGenre() : "Gênero desconhecido";
        if (holder.txtGenre != null) holder.txtGenre.setText("Gênero: " + genre);

        // Ano
        String infoYear = (movie.getYear() != 0) ? String.valueOf(movie.getYear()) : "2024";
        if (holder.txtInfoYear != null) holder.txtInfoYear.setText("Ano: " + infoYear);

        // Faixa etária
        String age = (movie.getAgeRating() != null && !movie.getAgeRating().isEmpty()) ? movie.getAgeRating() : "+17";
        if (holder.txtInfoAge != null) holder.txtInfoAge.setText("(Livre) " + age);

        // Garantir visibilidade
        if (holder.txtInfoYear != null) holder.txtInfoYear.setVisibility(View.VISIBLE);
        if (holder.txtInfoAge != null) holder.txtInfoAge.setVisibility(View.VISIBLE);
        if (holder.txtDescription != null) holder.txtDescription.setVisibility(View.VISIBLE);
        if (holder.txtGenre != null) holder.txtGenre.setVisibility(View.VISIBLE);

        // Cores com fallback
        try {
            int gray = ContextCompat.getColor(context, R.color.gray);
            if (holder.txtInfoYear != null) holder.txtInfoYear.setTextColor(gray);
            if (holder.txtInfoAge != null) holder.txtInfoAge.setTextColor(gray);
            if (holder.txtGenre != null) holder.txtGenre.setTextColor(gray);
        } catch (Exception e) {
            if (holder.txtInfoYear != null) holder.txtInfoYear.setTextColor(0xFFCCCCCC);
            if (holder.txtInfoAge != null) holder.txtInfoAge.setTextColor(0xFFCCCCCC);
            if (holder.txtGenre != null) holder.txtGenre.setTextColor(0xFFCCCCCC);
        }

        // Clique abre detalhe (apenas em clique, não em mudanças de texto)
        View.OnClickListener openDetail = v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());
            intent.putExtra("year", movie.getYear());
            intent.putExtra("genre", movie.getGenre());
            intent.putExtra("age", movie.getAgeRating());
            if (!(context instanceof android.app.Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        };

        if (holder.imgMovie != null) holder.imgMovie.setOnClickListener(openDetail);
        if (holder.btnPlayOverlay != null) holder.btnPlayOverlay.setOnClickListener(openDetail);
        holder.itemView.setOnClickListener(openDetail);
    }

    // Seção: contagem de itens
    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    // Seção: ViewHolder
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;
        ImageView btnPlayOverlay;

        TextView txtTitle;
        TextView txtDescription;
        TextView txtGenre;
        TextView txtInfoYear;
        TextView txtInfoAge;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind das views (IDs esperados no layout item_movie.xml)
            imgMovie = itemView.findViewById(R.id.imgMovie);
            btnPlayOverlay = itemView.findViewById(R.id.btnPlayOverlay);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);

            txtGenre = itemView.findViewById(R.id.txtGenre);

            txtInfoYear = itemView.findViewById(R.id.txtInfoYear);
            txtInfoAge = itemView.findViewById(R.id.txtInfoAge);
        }
    }
}
