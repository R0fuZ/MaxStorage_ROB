package com.example.maxstorage.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maxstorage.Menu.menu;
import com.example.maxstorage.R;
import com.example.maxstorage.Login.UserDAO;
import com.example.maxstorage.Login.User;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

public class registro extends AppCompatActivity {

    private EditText            etUsuario;
    private TextInputEditText   tiPassword;
    private EditText            etPropietario;
    private EditText            etEmail;
    private Button              btnRegistrarse;
    private UserDAO             userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                }
        );

        // 1) Referencia las vistas
        etUsuario       = findViewById(R.id.etUsuario);
        tiPassword      = findViewById(R.id.tiPassword);
        etPropietario   = findViewById(R.id.etPropietario);
        etEmail         = findViewById(R.id.etEmail);
        btnRegistrarse  = findViewById(R.id.btnRegistrarse);

        // 2) Instancia el DAO
        userDao = new UserDAO(this);

        // 3) Acción de Registrarse
        btnRegistrarse.setOnClickListener(v -> {
            String username   = etUsuario.getText().toString().trim();
            String password   = tiPassword.getText().toString();
            String ownerName  = etPropietario.getText().toString().trim();
            String email      = etEmail.getText().toString().trim();

            // 4) Validaciones simples
            if (username.isEmpty() || password.isEmpty()
                    || ownerName.isEmpty() || email.isEmpty()) {
                Toast.makeText(this,
                        "Todos los campos son obligatorios",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 5) Hashea la contraseña
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());

            // 6) Crea el objeto User y lo inserta
            User newUser = new User(username, hash, ownerName, email);
            long id = userDao.insert(newUser);

            if (id > 0) {
                Toast.makeText(this,
                        "Registro exitoso", Toast.LENGTH_SHORT).show();
                // 7) Regresa al login
                startActivity(new Intent(this, Login.class));
                finish();
            } else {
                Toast.makeText(this,
                        "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Llamado por android:onClick en el XML */
    public void goToLogin(View view) {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
