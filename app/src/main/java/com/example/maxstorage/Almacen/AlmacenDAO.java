package com.example.maxstorage.Almacen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maxstorage.DBConection_Conexion.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO {

    private final SQLiteDatabase db;

    public AlmacenDAO(Context context) {
        // Obtiene la instancia singleton de la BD
        db = DBConnection.getInstance(context).getDatabase();
    }

    /** Inserta un registro de Almacen (mismo esquema que packages) */
    public long insert(Almacen a) {
        ContentValues cv = new ContentValues();
        cv.put("weight",                    a.getWeight());
        cv.put("registered_by",             a.getRegisteredBy());
        cv.put("arrival_date",              a.getArrivalDate());
        cv.put("estimated_departure_date",  a.getEstimatedDepartureDate());
        cv.put("qr_code",                   a.getQrCode());
        cv.put("status",                    a.getStatus());
        return db.insert("packages", null, cv);
    }

    /** Obtiene todos los registros almacenados */
    public List<Almacen> getAll() {
        List<Almacen> list = new ArrayList<>();
        Cursor c = db.query(
                "packages",
                null,   // todas las columnas
                null, null, null, null,
                "arrival_date DESC"  // ordena por fecha de llegada descendente
        );
        while (c.moveToNext()) {
            Almacen a = new Almacen(
                    c.getInt   (c.getColumnIndexOrThrow("package_id")),
                    c.getDouble(c.getColumnIndexOrThrow("weight")),
                    c.getInt   (c.getColumnIndexOrThrow("registered_by")),
                    c.getString(c.getColumnIndexOrThrow("arrival_date")),
                    c.getString(c.getColumnIndexOrThrow("estimated_departure_date")),
                    c.getString(c.getColumnIndexOrThrow("qr_code")),
                    c.getString(c.getColumnIndexOrThrow("status"))
            );
            list.add(a);
        }
        c.close();
        return list;
    }

    /** Busca un registro por su ID */
    public Almacen findById(int id) {
        Cursor c = db.query(
                "packages",
                null,
                "package_id = ?",
                new String[]{ String.valueOf(id) },
                null, null, null
        );
        if (c.moveToFirst()) {
            Almacen a = new Almacen(
                    c.getInt   (c.getColumnIndexOrThrow("package_id")),
                    c.getDouble(c.getColumnIndexOrThrow("weight")),
                    c.getInt   (c.getColumnIndexOrThrow("registered_by")),
                    c.getString(c.getColumnIndexOrThrow("arrival_date")),
                    c.getString(c.getColumnIndexOrThrow("estimated_departure_date")),
                    c.getString(c.getColumnIndexOrThrow("qr_code")),
                    c.getString(c.getColumnIndexOrThrow("status"))
            );
            c.close();
            return a;
        } else {
            c.close();
            return null;
        }
    }

    /** Actualiza un registro existente */
    public int update(Almacen a) {
        ContentValues cv = new ContentValues();
        cv.put("weight",                   a.getWeight());
        cv.put("registered_by",            a.getRegisteredBy());
        cv.put("arrival_date",             a.getArrivalDate());
        cv.put("estimated_departure_date", a.getEstimatedDepartureDate());
        cv.put("qr_code",                  a.getQrCode());
        cv.put("status",                   a.getStatus());
        return db.update(
                "packages",
                cv,
                "package_id = ?",
                new String[]{ String.valueOf(a.getPackageId()) }
        );
    }

    /** Elimina un registro por su ID */
    public int delete(int id) {
        return db.delete(
                "packages",
                "package_id = ?",
                new String[]{ String.valueOf(id) }
        );
    }
}

