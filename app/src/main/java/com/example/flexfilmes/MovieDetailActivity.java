package com.example.flexfilmes;

// IMPORTS
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// =======================================
// TELA DETALHES DO FILME
// =======================================
public class MovieDetailActivity extends AppCompatActivity {

    // estrelas avaliação
    private ImageView star1, star2, star3, star4, star5;

    private int rating = 0;

    // =======================================
    // INICIO DA ACTIVITY
    // =======================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        // =======================================
        // TOOLBAR
        // =======================================
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("FlexFilmes");

        }

        // botão voltar
        toolbar.setNavigationOnClickListener(v -> finish());


        // =======================================
        // COMPONENTES
        // =======================================

        ImageView imgMovie = findViewById(R.id.imgMovie);

        ImageView playOverlay = findViewById(R.id.playOverlay);

        TextView txtTitle = findViewById(R.id.txtTitle);

        TextView txtDescription = findViewById(R.id.txtDescription);

        TextView txtInfo = findViewById(R.id.txtInfo);

        View btnDownload = findViewById(R.id.btnDownload);

        View btnShare = findViewById(R.id.btnShare);

        View btnMyList = findViewById(R.id.btnMyList);


        // =======================================
        // ESTRELAS
        // =======================================

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);


        // =======================================
        // RECEBER DADOS DO FILME
        // =======================================

        String title = getIntent().getStringExtra("title");

        String description = getIntent().getStringExtra("description");

        int image = getIntent().getIntExtra("image", 0);

        int year = getIntent().getIntExtra("year", 2024);

        String age = getIntent().getStringExtra("age");


        // evitar crash se idade vier null
        if(age == null){

            age = "";

        }


        // =======================================
        // MOSTRAR DADOS
        // =======================================

        txtTitle.setText(title);

        txtDescription.setText(description);

        txtInfo.setText(year + " • " + age);

        imgMovie.setImageResource(image);


        // =======================================
        // PLAY TRAILER
        // =======================================

        playOverlay.setOnClickListener(v -> {

            String url = "https://www.youtube.com/results?search_query=" + title + "+trailer";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            startActivity(intent);

        });


        // =======================================
        // DOWNLOAD
        // =======================================

        btnDownload.setOnClickListener(v ->

                Toast.makeText(this, "Download iniciado: " + title, Toast.LENGTH_SHORT).show()

        );


        // =======================================
        // COMPARTILHAR
        // =======================================

        btnShare.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_TEXT,

                    "Veja este filme: " + title + "\n" + description);

            startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));

        });


        // =======================================
        // MINHA LISTA
        // =======================================

        btnMyList.setOnClickListener(v ->

                Toast.makeText(this, "Adicionado à Minha Lista", Toast.LENGTH_SHORT).show()

        );


        // =======================================
        // SISTEMA DE ESTRELAS
        // =======================================

        star1.setOnClickListener(v -> setRating(1));
        star2.setOnClickListener(v -> setRating(2));
        star3.setOnClickListener(v -> setRating(3));
        star4.setOnClickListener(v -> setRating(4));
        star5.setOnClickListener(v -> setRating(5));


        // =======================================
        // FILMES RECOMENDADOS
        // =======================================

        RecyclerView recyclerRecommended = findViewById(R.id.recycler_recommended);

        recyclerRecommended.setLayoutManager(

                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        );

        List<Movie> recommended = CatalogActivity.getTopPicksMovies();

        if(recommended != null){

            MovieAdapter adapter = new MovieAdapter(this, recommended);

            recyclerRecommended.setAdapter(adapter);

        }

    }


    // =======================================
    // SISTEMA DE AVALIAÇÃO
    // =======================================
    private void setRating(int value) {

        rating = value;

        ImageView[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < stars.length; i++) {

            if (i < value) {

                stars[i].setImageResource(R.drawable.star_filled);

            } else {

                stars[i].setImageResource(R.drawable.star_empty);

            }

        }

    }


    // =======================================
    // MENU SUPERIOR (LUPA + 3 PONTOS)
    // =======================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);

        return true;

    }


    // =======================================
    // CLIQUES DO MENU
    // =======================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {

            Toast.makeText(this, "Pesquisar", Toast.LENGTH_SHORT).show();

            return true;

        }

        if (id == R.id.action_menu) {

            Toast.makeText(this, "Mais opções", Toast.LENGTH_SHORT).show();

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

}
