package com.example.androidsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditData extends AppCompatActivity {

    BiodataTable biodataTable;
    EditText nama, alamat;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        update = findViewById(R.id.update_data);
        biodataTable = new BiodataTable(getApplicationContext());
        //mengambil data yang dikirim
        nama.setText(getIntent().getStringExtra("nama"));
        alamat.setText(getIntent().getStringExtra("alamat"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biodataTable.updateData(
                        getIntent().getStringExtra("id"),
                        nama.getText().toString(),
                        alamat.getText().toString()
                );
                finish();
            }
        });
    }
}