package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

// Activity principal (entrada do app)
public class MainActivity extends AppCompatActivity {

    // Drawer lateral
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botões de navegação
        Button btnCatalog = findViewById(R.id.btnOpenCatalog);
        Button btnMyList = findViewById(R.id.btnOpenMyList);

        // Botão catálogo → abre tela de catálogo
        if (btnCatalog != null) {
            btnCatalog.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, CatalogActivity.class);
                startActivity(i);
            });
        }

        // Botão minha lista → abre tela de lista
        if (btnMyList != null) {
            btnMyList.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, MyListActivity.class);
                startActivity(i);
            });
        }

        // Inicializa drawer
        drawerLayout = findViewById(R.id.drawerLayout);
    }
}