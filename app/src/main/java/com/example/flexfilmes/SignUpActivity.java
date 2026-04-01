package com.example.flexfilmes;

// Imports
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Activity Cadastro
public class SignUpActivity extends AppCompatActivity {

    // Componentes
    private EditText inputName, inputEmail, inputPassword;
    private Button btnSignUp;

    // Inicialização
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Criar conta");
        }

        // Bind das views
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnSignUp = findViewById(R.id.btn_signup);

        // Botão cadastrar
        btnSignUp.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            // Validação
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

    // Botão voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}