package com.example.maxstorage.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maxstorage.Almacen.AlmacenPaquetesActivity;
import com.example.maxstorage.Login.Login;
import com.example.maxstorage.Paquetes.PaquetesActivity_Accion;
import com.example.maxstorage.R;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // -- Corrige aquí las referencias y los Intents --

        // 1) Botón "Registrar Paquete" (pantalla de alta de paquetes)
        Button btnRegistrarPaquete = findViewById(R.id.btnMenuRegistrarPaquete);
        btnRegistrarPaquete.setOnClickListener(v -> {
            startActivity(new Intent(this, PaquetesActivity_Accion.class));
        });

        // 2) Botón "Almacén de Paquetes" (pantalla de consulta/almacén)
        Button btnAlmacenPaquetes = findViewById(R.id.btnMenuAlmacenPaquetes);
        btnAlmacenPaquetes.setOnClickListener(v -> {
            startActivity(new Intent(this, AlmacenPaquetesActivity.class));
        });

        // 3) Cerrar sesión
        TextView tvCerrar = findViewById(R.id.tvCerrarSesion);
        tvCerrar.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}
