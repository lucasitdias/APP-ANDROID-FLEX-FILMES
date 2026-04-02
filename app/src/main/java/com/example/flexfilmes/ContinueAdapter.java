package com.example.flexfilmes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public ContinueAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_continue, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        // imagem do poster
        holder.imgPoster.setImageResource(movie.getImageResId());

        // botão play
        holder.btnPlay.setOnClickListener(v -> {

            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());

            context.startActivity(intent);

        });

        // botão info
        holder.btnInfo.setOnClickListener(v -> {

            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("image", movie.getImageResId());

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        ImageView btnPlay;
        ImageView btnInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgPoster);
            btnPlay = itemView.findViewById(R.id.btnPlay);
            btnInfo = itemView.findViewById(R.id.btnInfo);
        }
    }
}