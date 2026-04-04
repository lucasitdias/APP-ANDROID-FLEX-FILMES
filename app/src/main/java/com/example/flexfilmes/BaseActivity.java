package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    // Drawer abrir o menu lateral
    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Configura a toolbar custom (voltar + ações)
    protected void setupToolbarCustom(boolean showBack, boolean showCustomActions) {

        // Inicializa toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) return;

        // Define toolbar como action bar
        setSupportActionBar(toolbar);

        // Remove título padrão
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Remove ícone padrão da toolbar
        toolbar.setNavigationIcon(null);

        // Inicializa drawer layout
        drawerLayout = findViewById(R.id.drawerLayout);

        // Referência dos botões da toolbar
        View btnBackCustom = findViewById(R.id.btn_back_custom);
        View btnSearchCustom = findViewById(R.id.btn_search_custom);
        View btnMenuCustom = findViewById(R.id.btn_menu_custom);
        View actionsContainer = findViewById(R.id.container_actions_custom);

        // Botão voltar → controla visibilidade e ação
        if (btnBackCustom != null) {
            btnBackCustom.setVisibility(showBack ? View.VISIBLE : View.GONE);

            if (showBack) {
                btnBackCustom.setOnClickListener(v -> onBackPressed());
            }
        }

        // Container das ações (buscar/menu)
        if (actionsContainer != null) {
            actionsContainer.setVisibility(showCustomActions ? View.VISIBLE : View.GONE);
        }

        // Botão buscar → abre dialog de busca
        if (btnSearchCustom != null && showCustomActions) {
            btnSearchCustom.setOnClickListener(v -> openSearchDialog());
        }

        // Botão menu → abre drawer lateral
        if (btnMenuCustom != null && showCustomActions) {
            btnMenuCustom.setOnClickListener(v -> {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    // Abre o dialog de busca
    protected void openSearchDialog() {

        // Inicializa campo de busca
        final SearchView searchView = new SearchView(this);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_hint));

        // Cria dialog com input de busca
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.search_title))
                .setView(searchView)
                .setNegativeButton(getString(R.string.close), (d, which) -> d.dismiss())
                .create();

        // Listener de envio da busca
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // Quando envia a busca
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query); // executa busca
                dialog.dismiss();     // fecha dialog
                return true;
            }

            // Quando texto muda (não utilizado)
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Exibe dialog
        dialog.show();
    }

    // Faz a busca nos filmes disponíveis
    protected void performSearch(String rawQuery) {

        // Evita busca vazia
        if (rawQuery == null || rawQuery.isEmpty()) return;

        // Normaliza texto da busca
        String query = rawQuery.trim().toLowerCase();

        // Junta todos os filmes em uma lista
        List<Movie> all = new ArrayList<>();
        all.addAll(CatalogActivity.getContinueWatchingMovies());
        all.addAll(CatalogActivity.getMyListMovies());
        all.addAll(CatalogActivity.getTopPicksMovies());
        all.addAll(CatalogActivity.getRecentMovies());

        // Procura filme pelo título
        for (Movie m : all) {

            if (m.getTitle().toLowerCase().contains(query)) {

                // Abre tela de detalhes do filme
                Intent i = new Intent(this, MovieDetailActivity.class);
                i.putExtra("title", m.getTitle());
                i.putExtra("description", m.getDescription());
                i.putExtra("image", m.getImageResId());
                i.putExtra("year", m.getYear());
                i.putExtra("genre", m.getGenre());
                i.putExtra("age", m.getAgeRating());

                // Inicia activity de detalhes
                startActivity(i);
                return;
            }
        }

        // Exibe mensagem quando não encontra resultado
        Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
    }
}