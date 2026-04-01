package com.example.flexfilmes;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Activity Login
public class LoginActivity extends AppCompatActivity {

    // Componentes
    private EditText inputEmail, inputPassword;
    private Button btnLogin;
    private CheckBox rememberMe;
    private TextView helpText, signupText;

    // Inicialização
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind das views
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        rememberMe = findViewById(R.id.remember_me);
        helpText = findViewById(R.id.help_text);
        signupText = findViewById(R.id.signup_text);

        // Botão Login
        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            boolean remember = rememberMe.isChecked();

            // Validação
            if (!email.isEmpty() && !password.isEmpty()) {

                // Login sucesso
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Navegação
                startActivity(new Intent(LoginActivity.this, CatalogActivity.class));
                finish();

            } else {
                // Erro
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            }
        });

        // Ajuda
        helpText.setOnClickListener(v -> {
            Toast.makeText(this, "Abrir tela de ajuda...", Toast.LENGTH_SHORT).show();
        });

        // Cadastro
        signupText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }
}