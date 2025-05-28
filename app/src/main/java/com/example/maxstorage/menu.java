package com.example.maxstorage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        // Ajuste de inset para Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón Registrar Paquete
        Button btnRegistrar = findViewById(R.id.btnMenuAlmacenPaquetes);
        btnRegistrar.setOnClickListener(v -> {
            Intent i = new Intent(menu.this, almacen_paquetes.class);
            startActivity(i);
        });

        // Botón Almacén de Paquetes
        Button btnAlmacen = findViewById(R.id.btnMenuRegistrarPaquete);
        btnAlmacen.setOnClickListener(v -> {
            Intent i = new Intent(menu.this, PaquetesActivity.class);
            startActivity(i);
        });

        // Texto Cerrar sesión
        TextView tvCerrar = findViewById(R.id.tvCerrarSesion);
        tvCerrar.setOnClickListener(v -> {
            // Aquí cierras sesión, por ejemplo volviendo al login:
            Intent i = new Intent(menu.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}
