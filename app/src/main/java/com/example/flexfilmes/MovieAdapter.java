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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final Context context;
    private final List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Título e descrição
        holder.txtTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");
        holder.txtDescription.setText(movie.getDescription() != null ? movie.getDescription() : "");

        // Poster
        holder.imgMovie.setImageResource(movie.getImageResId());

        // Gênero (novo)
        String genre = (movie.getGenre() != null && !movie.getGenre().isEmpty()) ? movie.getGenre() : "Gênero desconhecido";
        holder.txtGenre.setText("Gênero: " + genre);

        // Ano
        String infoYear = (movie.getYear() != 0) ? String.valueOf(movie.getYear()) : "2024";
        holder.txtInfoYear.setText("Ano: " + infoYear);

        // Faixa etária: usar formato solicitado "(Livre) +17" — se o Movie tiver ageRating, usa; senão fallback
        String age = (movie.getAgeRating() != null && !movie.getAgeRating().isEmpty()) ? movie.getAgeRating() : "+17";
        holder.txtInfoAge.setText("(Livre) " + age);

        // Garantir visibilidade e contraste
        holder.txtInfoYear.setVisibility(View.VISIBLE);
        holder.txtInfoAge.setVisibility(View.VISIBLE);
        holder.txtDescription.setVisibility(View.VISIBLE);
        holder.txtGenre.setVisibility(View.VISIBLE);

        try {
            int gray = ContextCompat.getColor(context, R.color.gray);
            holder.txtInfoYear.setTextColor(gray);
            holder.txtInfoAge.setTextColor(gray);
            holder.txtGenre.setTextColor(gray);
        } catch (Exception e) {
            holder.txtInfoYear.setTextColor(0xFFCCCCCC);
            holder.txtInfoAge.setTextColor(0xFFCCCCCC);
            holder.txtGenre.setTextColor(0xFFCCCCCC);
        }

        // Ação de abrir detalhe (passa extras: title, description, image, year, genre, age)
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

        holder.imgMovie.setOnClickListener(openDetail);
        if (holder.btnPlayOverlay != null) holder.btnPlayOverlay.setOnClickListener(openDetail);
        holder.itemView.setOnClickListener(openDetail);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;
        ImageView btnPlayOverlay;

        TextView txtTitle;
        TextView txtDescription;
        TextView txtGenre;       // novo
        TextView txtInfoYear;
        TextView txtInfoAge;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.imgMovie);
            btnPlayOverlay = itemView.findViewById(R.id.btnPlayOverlay);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);

            txtGenre = itemView.findViewById(R.id.txtGenre);   // novo

            txtInfoYear = itemView.findViewById(R.id.txtInfoYear);
            txtInfoAge = itemView.findViewById(R.id.txtInfoAge);
        }
    }
}
