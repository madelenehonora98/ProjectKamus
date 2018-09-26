package com.madelene.projectkamus.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.ContactsContract;

import com.madelene.projectkamus.Entity.Kamus;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.madelene.projectkamus.Util.DatabaseContract.TABLE_KAMUS_ENG;
import static com.madelene.projectkamus.Util.DatabaseContract.TABLE_KAMUS_IN;

public class KamusHelper {

    private static String DATABASE_TABLE_EN = TABLE_KAMUS_ENG;
    private static String DATABASE_TABLE_IN = TABLE_KAMUS_IN;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context){
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Kamus> query(boolean isEng, String kataCari){
        ArrayList<Kamus> kamuss = new ArrayList<Kamus>();
        String table = "";
        Cursor cursor;
        if(isEng){
            table= DatabaseContract.TABLE_KAMUS_ENG;
        }else{
            table= TABLE_KAMUS_IN;
        }
        System.out.println("Kata Cari "+kataCari);
        if(kataCari!=null && !kataCari.isEmpty()){
            System.out.println("aaa");
            cursor = database.query(table,null,DatabaseContract.KamusColumns.KATAA + " = ?", new String[]{kataCari},null,null,_ID +" DESC",null);

        }else{
            System.out.println("bbb");
            cursor = database.query(table,null,null,null,null,_ID +" DESC",null);
        }


        cursor.moveToFirst();
        if (cursor.getCount()!=0) {
            while(!cursor.isAfterLast()){
                Kamus kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.KamusColumns.KATAA)));
                kamus.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.KamusColumns.DESKRIPSI)));

                kamuss.add(kamus);
                cursor.moveToNext();
            }

        }

        cursor.close();
        return kamuss;
    }

    public long insert(boolean isEng, Kamus kamus){

        String table = "";
        if(isEng){
            table= DatabaseContract.TABLE_KAMUS_ENG;
        }else{
            table= TABLE_KAMUS_IN;
        }

        ContentValues initialValues =  new ContentValues();
        initialValues.put(DatabaseContract.KamusColumns.KATAA, kamus.getKata());
        initialValues.put(DatabaseContract.KamusColumns.DESKRIPSI, kamus.getDeskripsi());
        return database.insert(table, null, initialValues);
    }

    public int update(boolean isEng,Kamus kamus){
        String table = "";
        if(isEng){
            table= DatabaseContract.TABLE_KAMUS_ENG;
        }else{
            table= TABLE_KAMUS_IN;
        }
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.KamusColumns.KATAA, kamus.getKata());
        args.put(DatabaseContract.KamusColumns.DESKRIPSI, kamus.getDeskripsi());
        return database.update(table, args, _ID + "= '" + kamus.getId() + "'", null);
    }

    public int delete(boolean isEng, int id){

        String table = "";
        if(isEng){

            table= DatabaseContract.TABLE_KAMUS_ENG;
        }else{
            table= TABLE_KAMUS_IN;
        }

        return database.delete(table, DatabaseContract.KamusColumns._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void insertBulk(boolean isEng, ArrayList<Kamus> data) {
        String queryInsert = "";
        if(isEng){
            queryInsert = DatabaseHelper.SQL_INSERT_STATEMENT_ENG;

        }else{
            queryInsert = DatabaseHelper.SQL_INSERT_STATEMENT_IND;

        }
        database.beginTransaction();
        SQLiteStatement stmt = database.compileStatement(queryInsert);
        for (Kamus k : data) {
            stmt.bindString(1, k.getKata());
            stmt.bindString(2, k.getDeskripsi());
            stmt.execute();
            stmt.clearBindings();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}
