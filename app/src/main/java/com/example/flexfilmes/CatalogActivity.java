package com.example.flexfilmes;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

// Activity principal do catálogo
public class CatalogActivity extends AppCompatActivity {

    // Inicialização
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Catálogo");
        }

        // Drawer e menu
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Botões
        ImageView btnSearch = findViewById(R.id.btnSearch);
        ImageView btnMenu = findViewById(R.id.btnMenu);

        // Pesquisa
        btnSearch.setOnClickListener(v -> {
            final EditText input = new EditText(this);
            input.setHint("Digite o nome do filme");

            new AlertDialog.Builder(this)
                    .setTitle("Pesquisar")
                    .setView(input)
                    .setPositiveButton("Buscar", (dialog, which) -> {

                        // Texto pesquisado
                        String query = input.getText().toString().trim().toLowerCase();

                        // Lista geral
                        List<Movie> allMovies = new ArrayList<>();
                        allMovies.addAll(getContinueWatchingMovies());
                        allMovies.addAll(getMyListMovies());
                        allMovies.addAll(getTopPicksMovies());
                        allMovies.addAll(getRecentMovies());

                        // Filtro
                        List<Movie> filtered = new ArrayList<>();
                        for (Movie m : allMovies) {
                            if (m.getTitle().toLowerCase().contains(query)) {
                                filtered.add(m);
                            }
                        }

                        // Resultado
                        if (filtered.isEmpty()) {
                            Toast.makeText(this, "Nenhum filme encontrado", Toast.LENGTH_SHORT).show();
                        } else {
                            RecyclerView recyclerView = findViewById(R.id.recycler_mylist);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                            MovieAdapter adapter = new MovieAdapter(this, filtered);
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(this, "Encontrados " + filtered.size() + " filmes", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        // Abrir menu
        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Menu lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            // Navegação
            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, CatalogActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_mylist) {
                Intent intent = new Intent(this, MyListActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Configurações", Toast.LENGTH_SHORT).show();
            }

            // Fechar menu
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Seções
        setupSection(R.id.recycler_continue, getContinueWatchingMovies(), true);
        setupSection(R.id.recycler_mylist, getMyListMovies(), false);
        setupSection(R.id.recycler_top, getTopPicksMovies(), false);
        setupSection(R.id.recycler_recent, getRecentMovies(), false);
    }

    // Configuração das listas
    private void setupSection(int recyclerId, List<Movie> movies, boolean isContinue) {
        RecyclerView recyclerView = findViewById(recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Adapter
        if (isContinue) {
            ContinueAdapter adapter = new ContinueAdapter(this, movies);
            recyclerView.setAdapter(adapter);
        } else {
            MovieAdapter adapter = new MovieAdapter(this, movies);
            recyclerView.setAdapter(adapter);
        }
    }

    // Continue assistindo
    public static List<Movie> getContinueWatchingMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Duna", "Ficção científica épica", R.drawable.dune_movie_1));
        list.add(new Movie("Duna Parte 2", "Continuação da saga", R.drawable.dune_movie_2));
        return list;
    }

    // Minha lista
    public static List<Movie> getMyListMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Jogos Vorazes", "A revolução começa", R.drawable.jogos_vorazes));
        list.add(new Movie("Street Fighter", "Luta clássica", R.drawable.street_fighter));
        list.add(new Movie("Super Mario Galaxy", "Aventura do encanador favorito", R.drawable.super_mario_galax));
        return list;
    }

    // Recomendados
    public static List<Movie> getTopPicksMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("A Odisseia", "Clássico épico", R.drawable.a_odisseia));
        list.add(new Movie("Toy Story 5", "A nova aventura dos brinquedos", R.drawable.toy_story_5));
        list.add(new Movie("Zero DC", "Heróis em ação", R.drawable.zero_dc));
        return list;
    }

    // Recentes
    public static List<Movie> getRecentMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Picaretas Não Vão Pro Céu", "Drama nacional", R.drawable.picaretas_nao_vao_pro_ceu));
        list.add(new Movie("Duna Parte 3", "Continuação épica", R.drawable.dune_movie_3));
        list.add(new Movie("Duna Parte 4", "Mais aventuras", R.drawable.dune_movie_4));
        list.add(new Movie("Duna Parte 5", "Final da saga", R.drawable.dune_movie_5));
        return list;
    }

    public static ArrayList<Movie> myList = new ArrayList<>();

    

    // Botão voltar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}