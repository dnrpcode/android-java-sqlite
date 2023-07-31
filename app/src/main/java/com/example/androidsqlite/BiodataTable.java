package com.example.androidsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class BiodataTable extends SQLiteOpenHelper {

    Context context;
    Cursor cursor;
    SQLiteDatabase database;

    public static String nama_database = "data";
    public static String nama_table = "biodata";

    public BiodataTable(@Nullable Context context) {
        super(context, nama_database, null, 3);
        this.context = context;
        database = getReadableDatabase();
        database = getWritableDatabase();
    }

    String random(){
        int rdm = new Random().nextInt(888+1) + 100;
        return String.valueOf(rdm);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS " + nama_table + " (id varchar(20), nama varchar(50), alamat varchar(50))";
        sqLiteDatabase.execSQL(query);
    }

    void simpanData(String nama, String alamat){
        database.execSQL(
                "INSERT INTO " + nama_table + " values ('"+random()+"', '"+nama+"', '"+alamat+"')"
        );
        Toast.makeText(context, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
    }

    void updateData(String id, String nama, String alamat){
        database.execSQL("UPDATE " + nama_table +
                " SET nama='"+nama+"', alamat='"+alamat+"'" +
                " WHERE id='"+id+"'"
        );
        Toast.makeText(context, "Behasil Edit Data", Toast.LENGTH_SHORT).show();
    }

    void deleteData(String id){
        database.execSQL("DELETE FROM " + nama_table + " WHERE id='"+id+"'");
        Toast.makeText(context, "Behasil Delete Data", Toast.LENGTH_SHORT).show();
    }

    Cursor tampilData(){
        Cursor cursor = database.rawQuery("SELECT * FROM " + nama_table, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
