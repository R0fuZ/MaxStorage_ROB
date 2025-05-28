package com.example.maxstorage.Paquetes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maxstorage.DBConection_Conexion.DBConnection;
import com.example.maxstorage.Paquetes.Paquetes;

import java.util.ArrayList;
import java.util.List;

public class PaquetesDAO {

    private final SQLiteDatabase db;

    public PaquetesDAO(Context context) {
        db = DBConnection.getInstance(context).getDatabase();
    }

    /** Inserta un nuevo paquete y retorna el nuevo ID */
    public long insert(Paquetes paquete) {
        ContentValues cv = new ContentValues();
        cv.put("weight", paquete.getWeight());
        cv.put("registered_by", paquete.getRegisteredBy());
        cv.put("arrival_date", paquete.getArrivalDate());
        cv.put("estimated_departure_date", paquete.getEstimatedDepartureDate());
        cv.put("qr_code", paquete.getQrCode());
        cv.put("status", paquete.getStatus());
        return db.insert("packages", null, cv);
    }

    /** Obtiene todos los paquetes */
    public List<Paquetes> getAll() {
        List<Paquetes> list = new ArrayList<>();
        Cursor c = db.query("packages", null, null, null, null, null, null);
        while (c.moveToNext()) {
            Paquetes p = new Paquetes(
                    c.getInt(c.getColumnIndexOrThrow("package_id")),
                    c.getDouble(c.getColumnIndexOrThrow("weight")),
                    c.getInt(c.getColumnIndexOrThrow("registered_by")),
                    c.getString(c.getColumnIndexOrThrow("arrival_date")),
                    c.getString(c.getColumnIndexOrThrow("estimated_departure_date")),
                    c.getString(c.getColumnIndexOrThrow("qr_code")),
                    c.getString(c.getColumnIndexOrThrow("status"))
            );
            list.add(p);
        }
        c.close();
        return list;
    }

    /** Busca un paquete por su ID */
    public Paquetes findById(int id) {
        Cursor c = db.query(
                "packages",
                null,
                "package_id = ?",
                new String[]{ String.valueOf(id) },
                null, null, null
        );
        if (c.moveToFirst()) {
            Paquetes p = new Paquetes(
                    c.getInt(c.getColumnIndexOrThrow("package_id")),
                    c.getDouble(c.getColumnIndexOrThrow("weight")),
                    c.getInt(c.getColumnIndexOrThrow("registered_by")),
                    c.getString(c.getColumnIndexOrThrow("arrival_date")),
                    c.getString(c.getColumnIndexOrThrow("estimated_departure_date")),
                    c.getString(c.getColumnIndexOrThrow("qr_code")),
                    c.getString(c.getColumnIndexOrThrow("status"))
            );
            c.close();
            return p;
        }
        c.close();
        return null;
    }

    /** Actualiza un paquete existente */
    public int update(Paquetes paquete) {
        ContentValues cv = new ContentValues();
        cv.put("weight", paquete.getWeight());
        cv.put("registered_by", paquete.getRegisteredBy());
        cv.put("arrival_date", paquete.getArrivalDate());
        cv.put("estimated_departure_date", paquete.getEstimatedDepartureDate());
        cv.put("qr_code", paquete.getQrCode());
        cv.put("status", paquete.getStatus());
        return db.update(
                "packages",
                cv,
                "package_id = ?",
                new String[]{ String.valueOf(paquete.getPackageId()) }
        );
    }

    /** Elimina un paquete por su ID */
    public int delete(int id) {
        return db.delete(
                "packages",
                "package_id = ?",
                new String[]{ String.valueOf(id) }
        );
    }
}
