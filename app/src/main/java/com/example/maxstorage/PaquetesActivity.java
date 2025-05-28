package com.example.maxstorage;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PaquetesActivity extends AppCompatActivity {

    private EditText etFechaEntrada;
    private EditText etFechaSalida;
    private final SimpleDateFormat fmt =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paquetes);

        // Referenciamos los EditText
        etFechaEntrada = findViewById(R.id.etFechaEntrada);
        etFechaSalida  = findViewById(R.id.etFechaSalida);

        // Hacemos que no reciban foco para que no salga el teclado
        etFechaEntrada.setFocusable(false);
        etFechaSalida.setFocusable(false);

        // Listener para abrir DatePicker al tocarlos
        etFechaEntrada.setOnClickListener(v -> showDatePicker(etFechaEntrada));
        etFechaSalida.setOnClickListener(v -> showDatePicker(etFechaSalida));
    }

    /**
     * Muestra un MaterialDatePicker y, al confirmar,
     * formatea la fecha y la asigna al EditText target.
     */
    private void showDatePicker(EditText target) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona fecha")
                .build();

        picker.show(getSupportFragmentManager(), "DATE_PICKER");

        picker.addOnPositiveButtonClickListener(selection -> {
            // 'selection' es milisegundos desde el epoch
            String fechaFormateada = fmt.format(selection);
            target.setText(fechaFormateada);
        });
    }
}
