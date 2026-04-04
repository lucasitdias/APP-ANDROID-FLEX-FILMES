package com.example.flexfilmes;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

// Seção: Activity de detalhe do filme
public class MovieDetailActivity extends BaseActivity {

    private ImageView star1, star2, star3, star4, star5;
    private int rating = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Configura toolbar herdada
        setupToolbarCustom(true, true);

        // Seção: views
        ImageView imgMovie = findViewById(R.id.imgMovie);
        ImageView playOverlay = findViewById(R.id.playOverlay);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtInfo = findViewById(R.id.txtInfo);
        View btnDownload = findViewById(R.id.btnDownload);
        View btnShare = findViewById(R.id.btnShare);
        View btnMyList = findViewById(R.id.btnMyList);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        // Seção: dados do Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int image = getIntent().getIntExtra("image", 0);
        int year = getIntent().getIntExtra("year", 2024);
        String genre = getIntent().getStringExtra("genre");
        String age = getIntent().getStringExtra("age");

        if (genre == null) genre = "Gênero";
        if (age == null || age.isEmpty()) age = "Livre";

        // Seção: popula views
        if (txtTitle != null) txtTitle.setText(title);
        if (txtDescription != null) txtDescription.setText(description);

        if (txtInfo != null) {
            // Ano • Gênero • Classificação
            String fullText = "Ano: " + year + "  •  Gênero: " + genre + "  •  " + age;
            SpannableString spannable = new SpannableString(fullText);
            int start = fullText.indexOf(age);

            int color = Color.parseColor("#4CAF50"); // verde padrão
            if (age.contains("16") || age.contains("18")) color = Color.RED;

            spannable.setSpan(
                    new ForegroundColorSpan(color),
                    start,
                    fullText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            txtInfo.setText(spannable);
        }

        // Seção: imagem
        if (imgMovie != null && image != 0) imgMovie.setImageResource(image);

        // Seção: botões de ação
        if (playOverlay != null) {
            playOverlay.setOnClickListener(v -> {
                String url = "https://www.youtube.com/results?search_query=" + Uri.encode(title + " trailer");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            });
        }

        if (btnDownload != null) {
            btnDownload.setOnClickListener(v -> Toast.makeText(this, "Download iniciado: " + title, Toast.LENGTH_SHORT).show());
        }

        if (btnShare != null) {
            btnShare.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Veja este filme: " + title + "\n" + description);
                startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
            });
        }

        if (btnMyList != null) {
            btnMyList.setOnClickListener(v -> Toast.makeText(this, "Adicionado à Minha Lista", Toast.LENGTH_SHORT).show());
        }

        // Seção: avaliação com estrelas
        if (star1 != null) star1.setOnClickListener(v -> setRating(1));
        if (star2 != null) star2.setOnClickListener(v -> setRating(2));
        if (star3 != null) star3.setOnClickListener(v -> setRating(3));
        if (star4 != null) star4.setOnClickListener(v -> setRating(4));
        if (star5 != null) star5.setOnClickListener(v -> setRating(5));

        // Seção: menu lateral
        if (navigationView != null && drawerLayout != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) startActivity(new Intent(this, MainActivity.class));
                else if (id == R.id.nav_profile) startActivity(new Intent(this, SignUpActivity.class));
                else if (id == R.id.nav_mylist) startActivity(new Intent(this, MyListActivity.class));

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }

        // Seção: filmes recomendados
        RecyclerView recyclerRecommended = findViewById(R.id.recycler_recommended);
        if (recyclerRecommended != null) {
            recyclerRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerRecommended.setAdapter(new MovieAdapter(this, CatalogActivity.getTopPicksMovies()));
        }
    }

    // Atualiza estrelas na UI
    private void setRating(int value) {
        rating = value;
        ImageView[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < stars.length; i++) {
            if (stars[i] == null) continue;
            stars[i].setImageResource(i < value ? R.drawable.star_filled : R.drawable.star_empty);
        }
    }
}