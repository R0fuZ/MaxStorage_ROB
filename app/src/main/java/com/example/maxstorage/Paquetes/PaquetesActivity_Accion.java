package com.example.maxstorage.Paquetes;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maxstorage.R;
import com.example.maxstorage.Paquetes.PaquetesDAO;
import com.example.maxstorage.Paquetes.Paquetes;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class PaquetesActivity_Accion extends AppCompatActivity {

    private EditText    etRegistradoPor,
            etId,
            etPeso,
            etEmpresa,
            etFechaEntrada,
            etFechaSalida;
    private ImageView   imgQr;
    private Button      btnGenerarQr,
            btnImprimir,
            btnGuardarDatos;

    private final SimpleDateFormat fmt =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private PaquetesDAO paquetesDAO;
    private String       currentQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paquetes);

        // 1) Referenciar vistas
        etRegistradoPor = findViewById(R.id.etRegistradoPor);
        etId            = findViewById(R.id.etId);
        etPeso          = findViewById(R.id.etPeso);
        etEmpresa       = findViewById(R.id.etEmpresa);
        etFechaEntrada  = findViewById(R.id.etFechaEntrada);
        etFechaSalida   = findViewById(R.id.etFechaSalida);
        imgQr           = findViewById(R.id.imgQr);
        btnGenerarQr    = findViewById(R.id.btnGenerarQr);
        btnImprimir     = findViewById(R.id.btnImprimir);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);

        // 2) Cargar usuario logeado desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("maxstorage_prefs", MODE_PRIVATE);
        String ownerName = prefs.getString("owner_name", "Usuario");
        etRegistradoPor.setText(ownerName);
        etRegistradoPor.setEnabled(false);

        // 3) Generar ID temporal (segundos desde epoch)
        int tempId = (int) (System.currentTimeMillis() / 1000);
        etId.setText(String.valueOf(tempId));
        etId.setEnabled(false);

        // 4) Configurar DatePickers
        etFechaEntrada.setFocusable(false);
        etFechaSalida.setFocusable(false);
        etFechaEntrada.setOnClickListener(v -> showDatePicker(etFechaEntrada));
        etFechaSalida.setOnClickListener(v -> showDatePicker(etFechaSalida));

        // 5) Inicializar DAO
        paquetesDAO = new PaquetesDAO(this);

        // 6) Generar QR
        btnGenerarQr.setOnClickListener(v -> {
            currentQrCode = UUID.randomUUID().toString();
            generateQrImage(currentQrCode);
        });

        // 7) Imprimir (pendiente de implementar)
        btnImprimir.setOnClickListener(v ->
                Toast.makeText(this, "Imprimir no implementado", Toast.LENGTH_SHORT).show()
        );

        // 8) Guardar datos en BD
        btnGuardarDatos.setOnClickListener(v -> {
            try {
                int registeredBy = prefs.getInt("user_id", -1);
                if (registeredBy < 0) {
                    Toast.makeText(this, "Usuario inválido", Toast.LENGTH_SHORT).show();
                    return;
                }
                double weight = Double.parseDouble(etPeso.getText().toString().trim());
                String arrivalDate = etFechaEntrada.getText().toString().trim();
                String departDate  = etFechaSalida.getText().toString().trim();
                String qrCode      = currentQrCode != null
                        ? currentQrCode
                        : UUID.randomUUID().toString();
                String status = "IN";

                Paquetes nuevo = new Paquetes(
                        weight, registeredBy,
                        arrivalDate, departDate,
                        qrCode, status
                );
                long id = paquetesDAO.insert(nuevo);
                if (id > 0) {
                    Toast.makeText(this,
                            "Paquete guardado con ID " + id,
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,
                            "Error al guardar paquete",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(this,
                        "Peso debe ser un número válido",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker(EditText target) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona fecha")
                .build();
        picker.show(getSupportFragmentManager(), "DATE_PICKER");
        picker.addOnPositiveButtonClickListener(selection ->
                target.setText(fmt.format(selection))
        );
    }

    private void generateQrImage(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            int size = 200;
            com.google.zxing.common.BitMatrix bm =
                    writer.encode(text, BarcodeFormat.QR_CODE, size, size);
            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bmp.setPixel(x, y, bm.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQr.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Error generando QR", Toast.LENGTH_SHORT).show();
        }
    }
}


