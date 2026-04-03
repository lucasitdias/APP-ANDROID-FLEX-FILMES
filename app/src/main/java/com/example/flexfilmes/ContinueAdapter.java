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
        // usar layout específico de "continuar" (item_continue.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_continue, parent, false);
        return new ContinueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContinueViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Preencher campos (seguro contra null)
        if (holder.txtTitle != null) holder.txtTitle.setText(movie.getTitle() != null ? movie.getTitle() : "");
        if (holder.txtDescription != null) holder.txtDescription.setText(movie.getDescription() != null ? movie.getDescription() : "");
        if (holder.imgPoster != null) holder.imgPoster.setImageResource(movie.getImageResId());

        String infoYear = (movie.getYear() != 0) ? String.valueOf(movie.getYear()) : "2024";
        if (holder.txtInfoYear != null) holder.txtInfoYear.setText("Ano: " + infoYear);

        String age = (movie.getAgeRating() != null && !movie.getAgeRating().isEmpty()) ? movie.getAgeRating() : "+17";
        if (holder.txtInfoAge != null) holder.txtInfoAge.setText("(Livre) " + age);

        // Garantir visibilidade dos textos (se existirem)
        if (holder.txtInfoYear != null) holder.txtInfoYear.setVisibility(View.VISIBLE);
        if (holder.txtInfoAge != null) holder.txtInfoAge.setVisibility(View.VISIBLE);
        if (holder.txtDescription != null) holder.txtDescription.setVisibility(View.VISIBLE);

        // Overlay: ativar apenas para cartazes claros (flag no model)
        View overlay = holder.itemView.findViewById(R.id.poster_overlay);
        if (overlay != null) {
            boolean showOverlay = false;
            try {
                showOverlay = movie.isPosterLight();
            } catch (Exception ignored) { }
            overlay.setVisibility(showOverlay ? View.VISIBLE : View.GONE);
        }

        // Clique abre detalhe — envia genre e age também
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

        // info button (abre um Toast com título ou texto padrão)
        if (holder.btnInfo != null) {
            holder.btnInfo.setOnClickListener(v ->
                    Toast.makeText(context, movie.getTitle() != null ? movie.getTitle() : "Informações", Toast.LENGTH_SHORT).show()
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

            // Esses TextViews podem não existir no item_continue.xml; se não existirem, manter null é seguro
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtInfoYear = itemView.findViewById(R.id.txtInfoYear);
            txtInfoAge = itemView.findViewById(R.id.txtInfoAge);
        }
    }
}
