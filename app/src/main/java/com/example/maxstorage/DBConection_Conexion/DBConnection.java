package com.example.maxstorage.DBConection_Conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "maxstorage.db";
    private static final int DATABASE_VERSION = 1;

    // Singleton instance
    private static DBConnection instance;

    private DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Obtiene la instancia única de DBConnection
     */
    public static synchronized DBConnection getInstance(Context context) {
        if (instance == null) {
            instance = new DBConnection(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Abre (o crea) la base de datos y retorna el objeto SQLiteDatabase
     */
    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1) Usuarios del sistema
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS system_users (" +
                        "  user_id       INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  username      TEXT    NOT NULL UNIQUE," +
                        "  password_hash TEXT    NOT NULL," +
                        "  owner_name    TEXT    NOT NULL," +
                        "  email         TEXT    NOT NULL UNIQUE," +
                        "  created_at    TEXT    NOT NULL DEFAULT (datetime('now'))" +
                        ");"
        );

        // 2) Restablecimientos de contraseña
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS password_resets (" +
                        "  reset_id   INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  user_id    INTEGER NOT NULL," +
                        "  token      TEXT    NOT NULL UNIQUE," +
                        "  created_at TEXT    NOT NULL DEFAULT (datetime('now'))," +
                        "  used_at    TEXT," +
                        "  FOREIGN KEY(user_id) REFERENCES system_users(user_id) ON DELETE CASCADE" +
                        ");"
        );

        // 3) Paquetes
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS packages (" +
                        "  package_id               INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  weight                   REAL    NOT NULL CHECK(weight > 0)," +
                        "  registered_by            INTEGER NOT NULL," +
                        "  arrival_date             TEXT    NOT NULL," +
                        "  estimated_departure_date TEXT," +
                        "  qr_code                  TEXT    NOT NULL UNIQUE," +
                        "  status                   TEXT    NOT NULL DEFAULT 'IN' " +
                        "                               CHECK(status IN('IN','OUT'))," +
                        "  FOREIGN KEY(registered_by) REFERENCES system_users(user_id) ON DELETE RESTRICT" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En próximas versiones, agrega ALTER TABLE o migra datos aquí
        db.execSQL("DROP TABLE IF EXISTS packages;");
        db.execSQL("DROP TABLE IF EXISTS password_resets;");
        db.execSQL("DROP TABLE IF EXISTS system_users;");
        onCreate(db);
    }
}
