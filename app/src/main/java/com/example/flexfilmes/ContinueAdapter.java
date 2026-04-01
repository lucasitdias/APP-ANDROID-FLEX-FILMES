package com.example.flexfilmes;

// Imports
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter Continue Assistindo
public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ViewHolder> {

    // Variáveis
    private Context context;
    private List<Movie> movieList;

    // Construtor
    public ContinueAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    // Criar item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_continue, parent, false);
        return new ViewHolder(view);
    }

    // Vincular dados
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Imagem
        holder.imgPoster.setImageResource(movie.getImageResId());

        // Botão Play
        holder.btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());
            context.startActivity(intent);
        });

        // Botão Info
        holder.btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());
            context.startActivity(intent);
        });
    }

    // Quantidade de itens
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Componentes
        ImageView imgPoster, btnPlay, btnInfo;

        // Construtor ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind das views
            imgPoster = itemView.findViewById(R.id.imgPoster);
            btnPlay = itemView.findViewById(R.id.btnPlay);
            btnInfo = itemView.findViewById(R.id.btnInfo);
        }
    }
}