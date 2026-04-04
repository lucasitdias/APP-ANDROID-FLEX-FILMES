package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

/**
 * Seção: Activity de Catálogo
 *
 * Esta Activity exibe as seções do catálogo, gerencia o drawer e a toolbar.
 * Comentários padronizados: // Seção: descrição
 */
public class CatalogActivity extends AppCompatActivity {

    // Seção: views e estado
    private RecyclerView recyclerMyList;
    private MovieAdapter currentAdapter;
    private List<Movie> allMoviesCache;
    private DrawerLayout drawerLayout;

    // Seção: ciclo de vida
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Seção: Drawer e NavigationView (inicializar antes da toolbar)
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Seção: configurar toolbar padronizada (ajustada para esta Activity)
        setupToolbar();

        // Seção: ActionBar (mantém título via string resources)
        if (getSupportActionBar() != null) {
            // não mostramos o título padrão (usamos TextView centralizado no layout)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle(getString(R.string.catalog));
        }

        // Seção: Recycler principal (onde mostramos resultados de busca)
        recyclerMyList = findViewById(R.id.recycler_mylist);
        if (recyclerMyList != null) {
            recyclerMyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }

        // Seção: Prepara cache com todos os filmes (para busca)
        allMoviesCache = new ArrayList<>();
        allMoviesCache.addAll(getContinueWatchingMovies());
        allMoviesCache.addAll(getMyListMovies());
        allMoviesCache.addAll(getTopPicksMovies());
        allMoviesCache.addAll(getRecentMovies());

        // Seção: Exibe por padrão "Minha Lista"
        displayMovies(getMyListMovies());

        // Seção: Menu lateral - listener completo
        if (navigationView != null && drawerLayout != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    startActivity(new Intent(this, MainActivity.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, SignUpActivity.class));
                } else if (id == R.id.nav_mylist) {
                    startActivity(new Intent(this, MyListActivity.class));
                } else if (id == R.id.nav_downloads) {
                    Toast.makeText(this, "Downloads", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_subscription) {
                    Toast.makeText(this, "Assinatura", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    Toast.makeText(this, "Configurações", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }

        // Seção: configurar seções horizontais da tela
        setupSection(R.id.recycler_continue, getContinueWatchingMovies(), true);
        setupSection(R.id.recycler_mylist, getMyListMovies(), false);
        setupSection(R.id.recycler_top, getTopPicksMovies(), false);
        setupSection(R.id.recycler_recent, getRecentMovies(), false);

        // Seção: abrir busca automaticamente se solicitado pela Intent
        if (getIntent() != null && getIntent().getBooleanExtra("open_search", false)) {
            openSearchDialog();
        }
    }

    // Seção: Exibe lista no recycler principal
    private void displayMovies(List<Movie> movies) {
        if (recyclerMyList == null) return;
        currentAdapter = new MovieAdapter(this, movies);
        recyclerMyList.setAdapter(currentAdapter);
    }

    /**
     * Seção: Abre um diálogo com SearchView para busca rápida
     *
     * Observação: usa string resource para hint (padronização).
     */
    private void openSearchDialog() {
        final SearchView searchView = new SearchView(this);
        searchView.setIconifiedByDefault(false);
        // Seção: usar string resource para hint
        searchView.setQueryHint(getString(R.string.search_hint));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.search_title))
                .setView(searchView)
                .setNegativeButton(getString(R.string.close), (d, which) -> d.dismiss())
                .create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

        dialog.show();
    }

    // Seção: Executa a busca sobre allMoviesCache e atualiza o recycler
    private void performSearch(String rawQuery) {
        if (rawQuery == null) rawQuery = "";
        String query = rawQuery.trim().toLowerCase();

        List<Movie> filtered = new ArrayList<>();
        for (Movie m : allMoviesCache) {
            if (m.getTitle() != null && m.getTitle().toLowerCase().contains(query)) {
                filtered.add(m);
            }
        }

        if (filtered.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
        }

        // Se houver exatamente 1 resultado, abrir detalhe diretamente
        if (filtered.size() == 1) {
            Movie single = filtered.get(0);
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("title", single.getTitle());
            intent.putExtra("description", single.getDescription());
            intent.putExtra("image", single.getImageResId());
            intent.putExtra("year", single.getYear());       // requer Movie.getYear()
            intent.putExtra("genre", single.getGenre());     // requer Movie.getGenre()
            startActivity(intent);
            return;
        }

        // Caso contrário, exibe lista filtrada
        displayMovies(filtered);
    }

    // Seção: Configuração das listas horizontais
    private void setupSection(int recyclerId, List<Movie> movies, boolean isContinue) {
        RecyclerView recyclerView = findViewById(recyclerId);
        if (recyclerView == null) return;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (isContinue) {
            ContinueAdapter adapter = new ContinueAdapter(this, movies);
            recyclerView.setAdapter(adapter);
        } else {
            MovieAdapter adapter = new MovieAdapter(this, movies);
            recyclerView.setAdapter(adapter);
        }
    }

    // Seção: Dados de exemplo - Continuar assistindo
    public static List<Movie> getContinueWatchingMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Duna", "Ficção científica épica", "Ficção científica", 2024, R.drawable.dune_movie_1));
        list.add(new Movie("Duna Parte 2", "Continuação da saga", "Ação", 2024, R.drawable.dune_movie_2));
        return list;
    }

    // Seção: Dados de exemplo - Minha lista
    public static List<Movie> getMyListMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Jogos Vorazes", "A revolução começa", "Ação", 2023, R.drawable.jogos_vorazes));
        list.add(new Movie("Street Fighter", "Luta clássica", "Ação", 2022, R.drawable.street_fighter));
        list.add(new Movie("Super Mario Galaxy", "Aventura do encanador favorito", "Animação", 2021, R.drawable.super_mario_galax));
        return list;
    }

    // Seção: Dados de exemplo - Principais escolhas
    public static List<Movie> getTopPicksMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("A Odisseia", "Clássico épico", "Drama", 2018, R.drawable.a_odisseia));
        list.add(new Movie("Toy Story 5", "A nova aventura dos brinquedos", "Animação", 2025, R.drawable.toy_story_5));
        list.add(new Movie("Zero DC", "Heróis em ação", "Ação", 2024, R.drawable.zero_dc));
        return list;
    }

    // Seção: Dados de exemplo - Recentes
    public static List<Movie> getRecentMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Picaretas Não Vão Pro Céu", "Drama nacional", "Drama", 2024, R.drawable.picaretas_nao_vao_pro_ceu));
        list.add(new Movie("Duna Parte 3", "Continuação épica", "Ficção científica", 2026, R.drawable.dune_movie_3));
        list.add(new Movie("Duna Parte 4", "Mais aventuras", "Ficção científica", 2027, R.drawable.dune_movie_4));
        list.add(new Movie("Duna Parte 5", "Final da saga", "Ficção científica", 2028, R.drawable.dune_movie_5));
        return list;
    }

    // Seção: botão voltar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * Seção: MÉTODO PADRÃO PARA CONFIGURAR TOOLBAR (ajustado)
     *
     * - Usa lookup seguro para ImageViews opcionais e os remove (não esconde duplicidade).
     * - Centraliza comportamento do logo/título.
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar);
        }
        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Seção: esconder ImageViews opcionais se existirem (usa lookup seguro)
        int searchId = getResources().getIdentifier("toolbar_search", "id", getPackageName());
        View searchIcon = (searchId != 0) ? toolbar.findViewById(searchId) : null;
        if (searchIcon != null) searchIcon.setVisibility(View.GONE);

        int overflowId = getResources().getIdentifier("toolbar_overflow", "id", getPackageName());
        View overflow = (overflowId != 0) ? toolbar.findViewById(overflowId) : null;
        if (overflow != null) overflow.setVisibility(View.GONE);

        // Seção: seta de voltar à esquerda (ou comportamento que você definiu)
        toolbar.setNavigationIcon(R.drawable.icone_voltar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Seção: logo e título central clicáveis (levam para a home/catalog)
        View logo = toolbar.findViewById(R.id.toolbar_logo);
        View title = toolbar.findViewById(R.id.toolbar_title);
        View.OnClickListener goHome = v -> {
            Intent i = new Intent(this, CatalogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        };
        if (logo != null) logo.setOnClickListener(goHome);
        if (title != null) title.setOnClickListener(goHome);

        // Seção: não usamos toolbar.setOnMenuItemClickListener aqui; usamos onCreateOptionsMenu / onOptionsItemSelected
    }

    // Seção: Inflar menu específico desta Activity (apenas lupa e hambúrguer)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu); // ver instrução: criar menu_catalog.xml
        return true;
    }

    // Seção: Tratar cliques dos itens do menu (lupa e hambúrguer)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            // abrir diálogo de busca (reaproveita seu método existente)
            openSearchDialog();
            return true;
        } else if (id == R.id.action_hamburger) {
            // abrir drawer lateral
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
