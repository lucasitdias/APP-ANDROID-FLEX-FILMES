package com.example.flexfilmes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

// Adapter da seção "Continuar assistindo"
public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ContinueViewHolder> {

    private final Context context;
    private final List<Movie> movieList;

    public ContinueAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ContinueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Infla layout do item
        View view = LayoutInflater.from(context).inflate(R.layout.item_continue, parent, false);
        return new ContinueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContinueViewHolder holder, int position) {

        // Valida posição e lista
        if (movieList == null || position < 0 || position >= movieList.size()) return;

        Movie movie = movieList.get(position);
        if (movie == null) return;

        // Título
        if (holder.txtTitle != null) holder.txtTitle.setText(movie.getTitle());

        // Carrega imagem com Glide | Melhora a performance de carregamento
        if (holder.imgPoster != null) {
            Glide.with(context)
                    .load(movie.getImageResId())
                    .centerCrop()
                    .into(holder.imgPoster);
        }

        // Gênero
        if (holder.txtGenre != null) holder.txtGenre.setText("Gênero: " + movie.getGenre());

        // Descrição
        if (holder.txtDescription != null) holder.txtDescription.setText(movie.getDescription());

        // Formata: Ano + classificação
        if (holder.txtInfoYear != null) {

            String yearStr = String.valueOf(movie.getYear());
            String ageStr = movie.getAgeRating() != null ? movie.getAgeRating() : "Livre";

            // Ex: "Ano: 2025 | +14"
            String fullText = "Ano: " + yearStr + " | " + ageStr;

            SpannableString spannable = new SpannableString(fullText);
            int start = fullText.indexOf(ageStr);

            // Define cor da classificação
            int color = Color.parseColor("#4CAF50"); // verde padrão

            // Vermelho para 16 ou 18
            if (ageStr.contains("16") || ageStr.contains("18")) {
                color = Color.RED;
            }

            spannable.setSpan(
                    new ForegroundColorSpan(color),
                    start,
                    fullText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            holder.txtInfoYear.setText(spannable);
        }

        // Clique no item → abre detalhes
        View.OnClickListener openDetail = v -> {

            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());
            intent.putExtra("year", movie.getYear());
            intent.putExtra("genre", movie.getGenre());
            intent.putExtra("age", movie.getAgeRating());

            context.startActivity(intent);
        };

        holder.itemView.setOnClickListener(openDetail);
    }

    @Override
    public int getItemCount() {

        // Retorna quantidade de itens
        return movieList != null ? movieList.size() : 0;
    }

    static class ContinueViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView txtTitle, txtGenre, txtInfoYear, txtDescription;

        public ContinueViewHolder(@NonNull View itemView) {
            super(itemView);

            // Mapeia views do layout
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtInfoYear = itemView.findViewById(R.id.txtInfoYear);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}