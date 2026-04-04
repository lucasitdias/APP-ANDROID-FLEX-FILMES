package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

// Activity principal do catálogo de filmes
public class CatalogActivity extends AppCompatActivity {

    // Recycler principal da minha lista
    private RecyclerView recyclerMyList;

    // Adapter atual da lista
    private MovieAdapter currentAdapter;

    // Cache com todos os filmes para busca
    private List<Movie> allMoviesCache;

    // Drawer abrir o menu lateral
    private DrawerLayout drawerLayout;

    // Pool compartilhado para otimizar recyclers horizontais
    private final RecyclerView.RecycledViewPool sharedPool = new RecyclerView.RecycledViewPool();

    // Listas estáticas de filmes por seção
    private static List<Movie> continueList;
    private static List<Movie> myList;
    private static List<Movie> topList;
    private static List<Movie> recentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define layout da tela
        setContentView(R.layout.activity_catalog);

        // Inicializa drawer e menu lateral
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Configura a toolbar
        setupToolbar();

        // Inicializa recycler da minha lista
        recyclerMyList = findViewById(R.id.recycler_mylist);
        if (recyclerMyList != null) {
            recyclerMyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerMyList.setRecycledViewPool(sharedPool);
        }

        // Prepara cache para busca
        prepareSearchCache();

        // Exibe lista inicial
        displayMovies(getMyListMovies());

        // Ações do menu lateral
        if (navigationView != null && drawerLayout != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();

                // Navegação entre telas do menu
                if (id == R.id.nav_home) {
                    startActivity(new Intent(this, MainActivity.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, SignUpActivity.class));
                } else if (id == R.id.nav_mylist) {
                    startActivity(new Intent(this, MyListActivity.class));
                }

                // Fecha menu após clique
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }

        // Configura seções horizontais
        setupSection(R.id.recycler_continue, getContinueWatchingMovies(), true);
        setupSection(R.id.recycler_mylist, getMyListMovies(), false);
        setupSection(R.id.recycler_top, getTopPicksMovies(), false);
        setupSection(R.id.recycler_recent, getRecentMovies(), false);

        // Abre busca automaticamente (quando vem por intent)
        if (getIntent() != null && getIntent().getBooleanExtra("open_search", false)) {
            openSearchDialog();
        }
    }

    // Junta todos os filmes em cache para busca
    private void prepareSearchCache() {
        allMoviesCache = new ArrayList<>();
        allMoviesCache.addAll(getContinueWatchingMovies());
        allMoviesCache.addAll(getMyListMovies());
        allMoviesCache.addAll(getTopPicksMovies());
        allMoviesCache.addAll(getRecentMovies());
    }

    // Atualiza recycler principal com lista filtrada
    private void displayMovies(List<Movie> movies) {
        if (recyclerMyList == null) return;

        currentAdapter = new MovieAdapter(this, movies);
        recyclerMyList.setAdapter(currentAdapter);
    }

    // Abre o dialog de busca
    private void openSearchDialog() {

        // Inicializa campo de busca
        final SearchView searchView = new SearchView(this);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_hint));

        // Cria dialog com input
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.search_title))
                .setView(searchView)
                .setNegativeButton(getString(R.string.close), (d, which) -> d.dismiss())
                .create();

        // Executa busca ao digitar ou enviar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // Quando envia a busca
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            // Quando texto muda
            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

        // Exibe dialog
        dialog.show();
    }

    // Filtra filmes pelo título
    private void performSearch(String rawQuery) {

        // Evita null
        if (rawQuery == null) rawQuery = "";

        // Normaliza texto
        String query = rawQuery.trim().toLowerCase();

        List<Movie> filtered = new ArrayList<>();

        // Filtra pelo título
        for (Movie m : allMoviesCache) {
            if (m.getTitle() != null && m.getTitle().toLowerCase().contains(query)) {
                filtered.add(m);
            }
        }

        // Atualiza lista com resultado
        displayMovies(filtered);
    }

    // Configura cada seção horizontal
    private void setupSection(int recyclerId, List<Movie> movies, boolean isContinue) {

        RecyclerView recyclerView = findViewById(recyclerId);
        if (recyclerView == null) return;

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Seção "Continuar assistindo"
        if (isContinue) {
            recyclerView.setAdapter(new ContinueAdapter(this, movies));
        } else {
            // Demais seções usam pool compartilhado
            recyclerView.setRecycledViewPool(sharedPool);
            recyclerView.setAdapter(new MovieAdapter(this, movies));
        }
    }

    // Lista "Continuar assistindo"
    public static List<Movie> getContinueWatchingMovies() {
        if (continueList == null) {
            continueList = new ArrayList<>();
            continueList.add(new Movie("Duna", "Tudo começa.", "Ficção Científica", 2024, R.drawable.dune_movie_1, "+14"));
            continueList.add(new Movie("Duna Parte 2", "A jornada continua.", "Ficção Científica", 2024, R.drawable.dune_movie_2, "+14"));
            continueList.add(new Movie("Invencível", "Poder sem limites.", "Animação", 2023, R.drawable.invencivel, "+18"));
        }
        return continueList;
    }

    // Lista "Minha lista"
    public static List<Movie> getMyListMovies() {
        if (myList == null) {
            myList = new ArrayList<>();
            myList.add(new Movie("Jogos Vorazes", "A esperança sobrevive.", "Ação", 2023, R.drawable.jogos_vorazes, "+14"));
            myList.add(new Movie("Street Fighter", "A luta final.", "Ação", 2022, R.drawable.street_fighter, "+12"));
            myList.add(new Movie("Super Mario Galaxy", "Uma aventura galáctica.", "Animação", 2021, R.drawable.super_mario_galax, "+10"));
            myList.add(new Movie("Avatar", "Fogo e Cinzas.", "Aventura", 2025, R.drawable.avatar_fogo_e_cinzas, "+12"));
        }
        return myList;
    }

    // Lista "Top picks"
    public static List<Movie> getTopPicksMovies() {
        if (topList == null) {
            topList = new ArrayList<>();
            topList.add(new Movie("A Odisseia", "Uma jornada épica.", "Drama", 2018, R.drawable.a_odisseia, "+12"));
            topList.add(new Movie("Toy Story 5", "Amigos para sempre.", "Animação", 2025, R.drawable.toy_story_5, "+10"));
            topList.add(new Movie("Zero DC", "Justiça absoluta.", "Ação", 2024, R.drawable.zero_dc, "+14"));
            topList.add(new Movie("Invencível", "Poder sem limites.", "Animação", 2023, R.drawable.invencivel, "+18"));
        }
        return topList;
    }

    // Lista "Recentes"
    public static List<Movie> getRecentMovies() {
        if (recentList == null) {
            recentList = new ArrayList<>();
            recentList.add(new Movie("Picaretas", "Não vão pro céu.", "Comédia", 2024, R.drawable.picaretas_nao_vao_pro_ceu, "+14"));
            recentList.add(new Movie("Alice", "No País das Maravilhas.", "Fantasia", 2020, R.drawable.alice_no_pais_das_maravilhas, "+12"));
            recentList.add(new Movie("Malévola", "Dona do mal.", "Fantasia", 2019, R.drawable.malevola, "+10"));
            recentList.add(new Movie("Pânico 7", "O terror retorna.", "Terror", 2025, R.drawable.painco_7, "+18"));
            recentList.add(new Movie("Searching", "Desaparecida.", "Suspense", 2023, R.drawable.searching, "+14"));
            recentList.add(new Movie("Duna Parte 3", "O destino selado.", "Ficção Científica", 2026, R.drawable.dune_movie_3, "+14"));
            recentList.add(new Movie("Duna Parte 4", "Novos horizontes.", "Ficção Científica", 2027, R.drawable.dune_movie_4, "+14"));
        }
        return recentList;
    }

    // Configura a toolbar custom (voltar + ações)
    private void setupToolbar() {

        // Inicializa toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        // Remove título padrão
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Botão voltar
        ImageView btnBack = findViewById(R.id.btn_back_custom);
        if (btnBack != null) btnBack.setOnClickListener(v -> onBackPressed());

        // Botão buscar → abre dialog
        ImageView btnSearch = findViewById(R.id.btn_search_custom);
        if (btnSearch != null) btnSearch.setOnClickListener(v -> openSearchDialog());

        // Botão menu → abre drawer lateral
        ImageView btnMenu = findViewById(R.id.btn_menu_custom);
        if (btnMenu != null) btnMenu.setOnClickListener(v -> {
            if (drawerLayout != null) drawerLayout.openDrawer(GravityCompat.START);
        });

        // Clique na logo → recarrega catálogo
        View logoContainer = findViewById(R.id.container_logo);
        if (logoContainer != null) {
            logoContainer.setOnClickListener(v -> {
                Intent i = new Intent(this, CatalogActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            });
        }
    }
}