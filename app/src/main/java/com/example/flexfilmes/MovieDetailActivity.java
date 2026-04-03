package com.example.flexfilmes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView star1, star2, star3, star4, star5;
    private int rating = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // configurar toolbar padronizada (usa toolbar_flexfilmes do layout include)
        setupToolbar(true);

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

        // Receber extras do Intent (agora incluindo genre e year)
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int image = getIntent().getIntExtra("image", 0);
        int year = getIntent().getIntExtra("year", 2024);
        String genre = getIntent().getStringExtra("genre");
        String age = getIntent().getStringExtra("age");

        // evitar nulls
        if (genre == null) genre = "";
        if (age == null) age = "";

        // Mostrar dados na UI (verificações para evitar NPE)
        if (txtTitle != null && title != null) {
            txtTitle.setText(title);
        }
        if (txtDescription != null && description != null) {
            txtDescription.setText(description);
        }
        if (txtInfo != null) {
            String ageLabel = age.isEmpty() ? "+14" : age;
            String genreLabel = genre.isEmpty() ? "Gênero desconhecido" : genre;
            txtInfo.setText("Ano: " + year + "  •  Gênero: " + genreLabel + "  •  Faixa Etaria: " + ageLabel);
        }
        if (imgMovie != null && image != 0) {
            imgMovie.setImageResource(image);
        }

        // PLAY TRAILER (abre busca no YouTube)
        if (playOverlay != null) {
            playOverlay.setOnClickListener(v -> {
                String q = (title != null) ? title : "";
                String url = "https://www.youtube.com/results?search_query=" + q + "+trailer";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            });
        }

        // DOWNLOAD
        if (btnDownload != null) {
            btnDownload.setOnClickListener(v ->
                    Toast.makeText(this, "Download iniciado: " + (title != null ? title : ""), Toast.LENGTH_SHORT).show()
            );
        }

        // COMPARTILHAR
        if (btnShare != null) {
            btnShare.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "Veja este filme: " + (title != null ? title : "") + "\n" + (description != null ? description : ""));
                startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
            });
        }

        // MINHA LISTA
        if (btnMyList != null) {
            btnMyList.setOnClickListener(v ->
                    Toast.makeText(this, "Adicionado à Minha Lista", Toast.LENGTH_SHORT).show()
            );
        }

        // SISTEMA DE ESTRELAS
        if (star1 != null) star1.setOnClickListener(v -> setRating(1));
        if (star2 != null) star2.setOnClickListener(v -> setRating(2));
        if (star3 != null) star3.setOnClickListener(v -> setRating(3));
        if (star4 != null) star4.setOnClickListener(v -> setRating(4));
        if (star5 != null) star5.setOnClickListener(v -> setRating(5));

        // FILMES RECOMENDADOS
        RecyclerView recyclerRecommended = findViewById(R.id.recycler_recommended);
        if (recyclerRecommended != null) {
            recyclerRecommended.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            );

            List<Movie> recommended = CatalogActivity.getTopPicksMovies();
            if (recommended != null) {
                MovieAdapter adapter = new MovieAdapter(this, recommended);
                recyclerRecommended.setAdapter(adapter);
            }
        }
    }

    private void setRating(int value) {
        rating = value;
        ImageView[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < stars.length; i++) {
            if (stars[i] == null) continue;
            if (i < value) {
                stars[i].setImageResource(R.drawable.star_filled);
            } else {
                stars[i].setImageResource(R.drawable.star_empty);
            }
        }
    }

    // Inflar menu específico desta Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Tratar cliques do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(this, CatalogActivity.class);
            intent.putExtra("open_search", true);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_hamburger) {
            Toast.makeText(this, "Abrir menu", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // setupToolbar sem inflar menu global
    private void setupToolbar(boolean showBackArrow) {
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) return;

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        View logo = toolbar.findViewById(R.id.toolbar_logo);
        View title = toolbar.findViewById(R.id.toolbar_title);
        View.OnClickListener goHome = v -> {
            Intent i = new Intent(this, CatalogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        };
        if (logo != null) logo.setOnClickListener(goHome);
        if (title != null) title.setOnClickListener(goHome);

        if (showBackArrow) {
            // seta de voltar
            toolbar.setNavigationIcon(R.drawable.icone_voltar);
            toolbar.setNavigationContentDescription("Voltar");
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(this, CatalogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        } else {
            toolbar.setNavigationIcon(null);
        }

        // overflow (lookup seguro)
        int overflowId = getResources().getIdentifier("toolbar_overflow", "id", getPackageName());
        View overflow = (overflowId != 0) ? toolbar.findViewById(overflowId) : null;
        if (overflow != null) {
            final Toolbar toolbarFinal = toolbar;
            overflow.setOnClickListener(v -> toolbarFinal.showOverflowMenu());
        }

        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_profile) {
                startActivity(new Intent(this, SignUpActivity.class));
                return true;
            } else if (itemId == R.id.menu_catalog) {
                startActivity(new Intent(this, CatalogActivity.class));
                return true;
            } else if (itemId == R.id.menu_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.menu_mylist) {
                startActivity(new Intent(this, MyListActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                Toast.makeText(this, "Configurações", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.action_more) {
                Toast.makeText(this, "Mais opções", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}
