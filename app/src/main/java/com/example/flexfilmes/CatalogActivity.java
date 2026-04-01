package com.example.flexfilmes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Ativa a seta de voltar na ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Catálogo de Filmes");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista de filmes usando imagens reais do drawable
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("A Odisseia", "Clássico épico", R.drawable.a_odisseia));
        movieList.add(new Movie("Duna", "Ficção científica épica", R.drawable.dune_movie_1));
        movieList.add(new Movie("Duna Parte 2", "Continuação da saga", R.drawable.dune_movie_2));
        movieList.add(new Movie("Jogos Vorazes", "A revolução começa", R.drawable.jogos_vorazes));
        movieList.add(new Movie("Picaretas Não Vão Pro Céu", "Drama nacional", R.drawable.picaras_nao_vao_pro_ceu));
        movieList.add(new Movie("Street Fighter", "Luta clássica", R.drawable.street_fighter));
        movieList.add(new Movie("Super Mario Galaxy", "Aventura do encanador favorito", R.drawable.super_mario_galax));
        movieList.add(new Movie("Toy Story 5", "A nova aventura dos brinquedos", R.drawable.toy_story_5));
        movieList.add(new Movie("Zero DC", "Heróis em ação", R.drawable.zero_dc));

        // Configura o adapter
        MovieAdapter adapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
