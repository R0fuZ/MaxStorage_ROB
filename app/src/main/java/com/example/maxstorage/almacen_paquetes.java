package com.example.maxstorage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.SearchAutoComplete;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class almacen_paquetes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_almacen_paquetes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuro el color del texto del SearchView
        SearchView searchView = findViewById(R.id.searchViewId);
        @SuppressLint("RestrictedApi") SearchAutoComplete searchText =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTextColor(Color.BLACK);
        searchText.setHintTextColor(Color.BLACK);

        // Resto de tu inicialización (RecyclerView, listeners, etc.)
    }
}
