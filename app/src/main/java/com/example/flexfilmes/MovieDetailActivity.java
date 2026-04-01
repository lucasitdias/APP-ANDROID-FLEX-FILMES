package com.example.flexfilmes;

// Imports
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Activity detalhes do filme
public class MovieDetailActivity extends AppCompatActivity {

    // Inicialização
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Componentes
        ImageView imgMovie = findViewById(R.id.imgMovie);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtDescription = findViewById(R.id.txtDescription);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnDownload = findViewById(R.id.btnDownload);
        Button btnShare = findViewById(R.id.btnShare);

        // Dados do Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int image = getIntent().getIntExtra("image", 0);

        // Exibir dados
        txtTitle.setText(title);
        txtDescription.setText(description);
        imgMovie.setImageResource(image);

        // Botão Play
        btnPlay.setOnClickListener(v -> {
            Intent playIntent = new Intent(Intent.ACTION_VIEW);
            playIntent.setDataAndType(Uri.parse("http://exemplo.com/video.mp4"), "video/*");
            startActivity(playIntent);
        });

        // Botão Download
        btnDownload.setOnClickListener(v ->
                Toast.makeText(this, "Download iniciado para: " + title, Toast.LENGTH_SHORT).show()
        );

        // Botão Compartilhar
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Veja este filme: " + title + "\n" + description);
            startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
        });
    }

    // Botão voltar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}