package com.example.maxstorage.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maxstorage.DBConection_Conexion.DBConnection;
import com.example.maxstorage.Menu.menu;
import com.example.maxstorage.R;
import com.example.maxstorage.Login.UserDAO;
import com.example.maxstorage.Login.User;
import com.google.android.material.textfield.TextInputEditText;


import org.mindrot.jbcrypt.BCrypt;

public class Login extends AppCompatActivity {

    private EditText              etUsername;
    private TextInputEditText     tiPassword;
    private Button                btnIniciarSesion;
    private TextView              tvRegistrate, tvOlvideContrasena;
    private UserDAO               userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // o R.layout.activity_login si renombraste el XML

        // Ajuste de insets para full-screen
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
                    return insets;
                }
        );

        // --- 1) Referenciar vistas con los IDs de tu XML ---
        etUsername        = findViewById(R.id.etRegistradoPor);
        tiPassword        = findViewById(R.id.tiPassword);
        btnIniciarSesion  = findViewById(R.id.btnIniciarSecion);
        tvRegistrate      = findViewById(R.id.tvRegistrate);
        tvOlvideContrasena= findViewById(R.id.tvOlbidecontrsena);

        // --- 2) Crear instancia del DAO ---
        userDao = new UserDAO(this);

        // --- 3) Lógica de inicio de sesión ---
        btnIniciarSesion.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = tiPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            User u = userDao.findByUsername(username);
            if (u != null && BCrypt.checkpw(password, u.getPasswordHash())) {
                // Éxito → abrir menú principal
                startActivity(new Intent(this, menu.class));
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña inválidos", Toast.LENGTH_SHORT).show();
            }
            // guardar en prefs
            SharedPreferences prefs = getSharedPreferences("maxstorage_prefs", MODE_PRIVATE);
            prefs.edit()
                    .putInt   ("user_id",   u.getUserId())
                    .putString("owner_name",u.getOwnerName())
                    .apply();

        });

        // --- 4) Ir a pantalla de registro ---
        tvRegistrate.setOnClickListener(v ->
                startActivity(new Intent(this, registro.class))
        );

        // --- 5) “Olvidé mi contraseña” (pendiente de implementar) ---
        tvOlvideContrasena.setOnClickListener(v ->
                Toast.makeText(this, "Funcionalidad de recuperación no implementada", Toast.LENGTH_SHORT).show()
        );
    }
}
