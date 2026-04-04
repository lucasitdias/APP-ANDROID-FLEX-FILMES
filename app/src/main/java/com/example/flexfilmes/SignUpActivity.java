package com.example.flexfilmes;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Seção: Activity de Cadastro
 *
 * Permite criar uma nova conta com nome, email e senha.
 * Comentários padronizados: // Seção: descrição
 */
public class SignUpActivity extends AppCompatActivity {

    // Seção: componentes
    private EditText inputName, inputEmail, inputPassword;
    private Button btnSignUp;

    // Seção: ciclo de vida
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Seção: ActionBar (com botão voltar e título)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Criar conta");
        }

        // Seção: bind das views
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnSignUp = findViewById(R.id.btn_signup);

        // Seção: listener do botão cadastrar
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> {
                String name = (inputName != null) ? inputName.getText().toString().trim() : "";
                String email = (inputEmail != null) ? inputEmail.getText().toString().trim() : "";
                String password = (inputPassword != null) ? inputPassword.getText().toString() : "";

                // Seção: validação simples
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    // Sucesso
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Voltar
                    finish();
                } else {
                    // Erro
                    Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Seção: botão voltar da ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
