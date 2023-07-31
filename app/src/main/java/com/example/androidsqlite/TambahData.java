package com.example.androidsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TambahData extends AppCompatActivity {

    BiodataTable biodataTable;
    EditText nama, alamat;
    Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        biodataTable = new BiodataTable(getApplicationContext());
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        simpan = findViewById(R.id.simpan_data);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanData();
                finish();
            }
        });
    }

    void simpanData(){
         biodataTable.simpanData(nama.getText().toString(), alamat.getText().toString());
    }
}