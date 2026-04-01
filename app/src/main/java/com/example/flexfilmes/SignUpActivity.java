package com.example.flexfilmes;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ativa a seta de voltar na barra superior
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Criar conta");
        }

        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // volta para LoginActivity
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Ação da seta de voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // fecha a tela e volta para LoginActivity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
