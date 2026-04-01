package com.example.flexfilmes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Ativa botão de voltar na ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalhes do Filme");
        }

        // Referências aos elementos da tela
        ImageView imgMovie = findViewById(R.id.imgMovie);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtDescription = findViewById(R.id.txtDescription);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnDownload = findViewById(R.id.btnDownload);
        Button btnShare = findViewById(R.id.btnShare);

        // Recebe dados do Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int image = getIntent().getIntExtra("image", 0);

        // Exibe dados na tela
        txtTitle.setText(title);
        txtDescription.setText(description);
        imgMovie.setImageResource(image);

        // Botão Play → abre player externo (simulação)
        btnPlay.setOnClickListener(v -> {
            Intent playIntent = new Intent(Intent.ACTION_VIEW);
            playIntent.setDataAndType(Uri.parse("http://exemplo.com/video.mp4"), "video/*");
            startActivity(playIntent);
        });

        // Botão Download → simula download
        btnDownload.setOnClickListener(v ->
                Toast.makeText(this, "Download iniciado para: " + title, Toast.LENGTH_SHORT).show()
        );

        // Botão Compartilhar → envia texto
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Veja este filme: " + title + "\n" + description);
            startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Fecha a tela e volta para o catálogo
        return true;
    }
}
