package com.madelene.projectkamus.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME = "dbkamus";

    private Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_KAMUS_ENG = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_KAMUS_ENG,
            DatabaseContract.KamusColumns._ID,
            DatabaseContract.KamusColumns.KATAA,
            DatabaseContract.KamusColumns.DESKRIPSI

    );
    private static final String SQL_CREATE_TABLE_KAMUS_IN = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_KAMUS_IN,
            DatabaseContract.KamusColumns._ID,
            DatabaseContract.KamusColumns.KATAA,
            DatabaseContract.KamusColumns.DESKRIPSI

    );


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_KAMUS_ENG);
        db.execSQL(SQL_CREATE_TABLE_KAMUS_IN);
    }

    protected static final String SQL_INSERT_STATEMENT_ENG = String.format("INSERT INTO %s (%s, %s) VALUES (?,? )", DatabaseContract.TABLE_KAMUS_ENG, DatabaseContract.KamusColumns.KATAA, DatabaseContract.KamusColumns.DESKRIPSI);
    protected static final String SQL_INSERT_STATEMENT_IND = String.format("INSERT INTO %s (%s, %s) VALUES (?,? )", DatabaseContract.TABLE_KAMUS_IN, DatabaseContract.KamusColumns.KATAA, DatabaseContract.KamusColumns.DESKRIPSI);

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("upgraded");
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_KAMUS_ENG);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_KAMUS_IN);
        onCreate(db);
    }
}
