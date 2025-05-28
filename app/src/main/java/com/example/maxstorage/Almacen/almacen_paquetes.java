package com.example.maxstorage.Almacen;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maxstorage.R;

import java.util.ArrayList;
import java.util.List;

public class almacen_paquetes extends AppCompatActivity {

    private SearchView      searchView;
    private RecyclerView    rvPaquetes;
    private TextView        areaDetalles;
    private AlmacenDAO      dao;
    private List<Almacen>   originalList;
    private List<Almacen>   filteredList;
    private AlmacenAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_almacen_paquetes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // 1) Referencias
        searchView   = findViewById(R.id.searchViewId);
        rvPaquetes   = findViewById(R.id.rvPaquetes);
        areaDetalles = findViewById(R.id.areaDetalles);

        // 2) Carga datos desde la BD
        dao = new AlmacenDAO(this);
        originalList = dao.getAll();
        filteredList = new ArrayList<>(originalList);

        // 3) Configura RecyclerView y Adapter
        adapter = new AlmacenAdapter(filteredList, this::showDetails);
        rvPaquetes.setLayoutManager(new LinearLayoutManager(this));
        rvPaquetes.setAdapter(adapter);

        // 4) Busca por ID al escribir en SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterById(newText.trim());
                return true;
            }
        });
    }

    /** Filtra la lista por paquete_id que contenga el texto */
    private void filterById(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Almacen a : originalList) {
                if (String.valueOf(a.getPackageId()).contains(text)) {
                    filteredList.add(a);
                }
            }
        }
        adapter.notifyDataSetChanged();
        areaDetalles.setText(""); // limpia detalles al filtrar
    }

    /** Muestra en el TextView los detalles del paquete seleccionado */
    private void showDetails(Almacen a) {
        String details = ""
                + "ID: "        + a.getPackageId()              + "\n"
                + "Peso: "      + a.getWeight()                 + " kg\n"
                + "Registrado por: " + a.getRegisteredBy()       + "\n"
                + "Entrada: "   + a.getArrivalDate()            + "\n"
                + "Salida: "    + a.getEstimatedDepartureDate() + "\n"
                + "QR: "        + a.getQrCode()                 + "\n"
                + "Estado: "    + a.getStatus();
        areaDetalles.setText(details);
    }
}


