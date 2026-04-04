package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Seção: Activity de Busca
 *
 * Correções aplicadas:
 * - Toolbar com botão voltar configurado
 * - SearchView usa onQueryTextSubmit para executar busca (Enter/OK)
 * - Exibe resultados em RecyclerView usando MovieAdapter
 * - Faz busca sobre o cache estático de CatalogActivity
 * - Proteções contra NPE e logs para debug
 */
public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerResults;
    private MovieAdapter resultsAdapter;
    private List<Movie> resultsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Configurar toolbar padronizada
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        // Bind das views
        searchView = findViewById(R.id.search_view);
        recyclerResults = findViewById(R.id.recycler_search_results);

        // Preparar RecyclerView
        resultsList = new ArrayList<>();
        resultsAdapter = new MovieAdapter(this, resultsList);
        if (recyclerResults != null) {
            recyclerResults.setLayoutManager(new LinearLayoutManager(this));
            recyclerResults.setAdapter(resultsAdapter);
        }

        // Configurar SearchView
        if (searchView != null) {
            searchView.setIconifiedByDefault(false);
            searchView.setQueryHint(getString(R.string.search_hint));

            // Se houver query passada via Intent, preencher e submeter
            Intent intent = getIntent();
            if (intent != null) {
                String initialQuery = intent.getStringExtra("query");
                if (initialQuery != null && !initialQuery.trim().isEmpty()) {
                    searchView.setQuery(initialQuery.trim(), false);
                    // não submeter automaticamente; esperar ação do usuário
                }
            }

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    performSearch(query);
                    // remover foco para fechar teclado
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // não executar busca automática para evitar navegação imediata
                    return true;
                }
            });
        }
    }

    /**
     * Executa busca simples sobre os filmes disponíveis (fonte: CatalogActivity)
     * Atualiza o RecyclerView com os resultados filtrados.
     */
    private void performSearch(String rawQuery) {
        if (rawQuery == null) rawQuery = "";
        String query = rawQuery.trim().toLowerCase();

        // Reunir todas as fontes conhecidas
        List<Movie> source = new ArrayList<>();
        source.addAll(CatalogActivity.getContinueWatchingMovies());
        source.addAll(CatalogActivity.getMyListMovies());
        source.addAll(CatalogActivity.getTopPicksMovies());
        source.addAll(CatalogActivity.getRecentMovies());

        List<Movie> filtered = new ArrayList<>();
        for (Movie m : source) {
            if (m == null) continue;
            if (m.getTitle() != null && m.getTitle().toLowerCase().contains(query)) {
                filtered.add(m);
            }
        }

        Log.d("SEARCH_ACTIVITY", "performSearch query='" + query + "' results=" + filtered.size());

        // Atualizar adapter
        resultsList.clear();
        resultsList.addAll(filtered);
        if (resultsAdapter != null) {
            resultsAdapter.notifyDataSetChanged();
        }

        // Se não houver resultados, manter lista vazia e opcionalmente mostrar mensagem
        if (filtered.isEmpty()) {
            // Toast opcional: comentar se não quiser popup
            // Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
        }
    }

    // Tratar clique no botão voltar da toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
