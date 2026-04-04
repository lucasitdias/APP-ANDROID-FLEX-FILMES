package com.example.flexfilmes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Seção: Adapter para "Continuar assistindo"
 *
 * Correções aplicadas:
 * - Proteções contra NPE e índices inválidos
 * - Controle de visibilidade do overlay via flag posterLight no modelo Movie
 * - Clique abre MovieDetailActivity passando extras
 * - Uso seguro de recursos e listeners
 */
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_continue, parent, false);
        return new ContinueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContinueViewHolder holder, int position) {
        if (movieList == null || position < 0 || position >= movieList.size()) return;
        Movie movie = movieList.get(position);
        if (movie == null) return;

        // Título e descrição (seguro contra null)
        if (holder.txtTitle != null) holder.txtTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");
        if (holder.txtDescription != null) holder.txtDescription.setText(movie.getDescription() != null ? movie.getDescription() : "");

        // Poster
        try {
            if (holder.imgPoster != null) holder.imgPoster.setImageResource(movie.getImageResId());
        } catch (Exception ignored) { }

        // Ano e faixa etária
        String infoYear = (movie.getYear() != 0) ? String.valueOf(movie.getYear()) : "2024";
        if (holder.txtInfoYear != null) holder.txtInfoYear.setText("Ano: " + infoYear);

        String age = (movie.getAgeRating() != null && !movie.getAgeRating().isEmpty()) ? movie.getAgeRating() : "+17";
        if (holder.txtInfoAge != null) holder.txtInfoAge.setText("(Livre) " + age);

        // Garantir visibilidade dos campos informativos
        if (holder.txtInfoYear != null) holder.txtInfoYear.setVisibility(View.VISIBLE);
        if (holder.txtInfoAge != null) holder.txtInfoAge.setVisibility(View.VISIBLE);
        if (holder.txtDescription != null) holder.txtDescription.setVisibility(View.VISIBLE);

        // Overlay controlado por flag no modelo
        View overlay = holder.itemView.findViewById(R.id.poster_overlay);
        if (overlay != null) {
            boolean showOverlay = false;
            try {
                showOverlay = movie.isPosterLight();
            } catch (Exception ignored) { }
            overlay.setVisibility(showOverlay ? View.VISIBLE : View.GONE);
        }

        // Ação de abrir detalhe (apenas em clique)
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

        holder.itemView.setOnClickListener(openDetail);
        if (holder.imgPoster != null) holder.imgPoster.setOnClickListener(openDetail);
        if (holder.btnPlay != null) holder.btnPlay.setOnClickListener(openDetail);

        // Botão de info exibe Toast (placeholder)
        if (holder.btnInfo != null) {
            holder.btnInfo.setOnClickListener(v ->
                    Toast.makeText(context, movie.getTitle() != null ? movie.getTitle() : context.getString(R.string.info), Toast.LENGTH_SHORT).show()
            );
        }
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    static class ContinueViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        ImageView btnPlay;
        ImageView btnInfo;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtInfoYear;
        TextView txtInfoAge;

        public ContinueViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            btnPlay = itemView.findViewById(R.id.btnPlay);
            btnInfo = itemView.findViewById(R.id.btnInfo);

            // Esses IDs podem não existir em todos os layouts; lookup seguro
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtInfoYear = itemView.findViewById(R.id.txtInfoYear);
            txtInfoAge = itemView.findViewById(R.id.txtInfoAge);
        }
    }
}
