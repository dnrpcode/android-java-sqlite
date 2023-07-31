package com.example.androidsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button tambahData;
    ArrayList<Objek> list;

    SQLiteDatabase database;
    Cursor cursor;
    BiodataTable biodataTable;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        tambahData = findViewById(R.id.tambah_data);

        biodataTable = new BiodataTable(getApplicationContext());

        tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TambahData.class));
            }
        });

        ambilData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ambilData();
    }

    void ambilData() {
        list = new ArrayList<Objek>();
        cursor = biodataTable.tampilData();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String nama = cursor.getString(1);
                String alamat = cursor.getString(2);
                list.add(new Objek(id, nama, alamat));
            }
            adapter = new CustomAdapter(getApplicationContext(), list, MainActivity.this);
            listView.setAdapter(adapter);
        }
    }
}

class CustomAdapter extends BaseAdapter {

    Activity activity;
    BiodataTable biodataTable;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Objek> model;

    CustomAdapter(Context context, ArrayList<Objek> list, Activity activity) {
        this.context = context;
        model = list;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(context);
        biodataTable = new BiodataTable(context);
    }


    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView id, nama, alamat;
    Button edit, hapus;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1 = layoutInflater.inflate(R.layout.list_data, null);
        id = view1.findViewById(R.id.id);
        nama = view1.findViewById(R.id.nama);
        alamat = view1.findViewById(R.id.alamat);

        id.setText(model.get(position).getId());
        nama.setText(model.get(position).getNama());
        alamat.setText(model.get(position).getAlamat());

        edit = view1.findViewById(R.id.edit);
        hapus = view1.findViewById(R.id.hapus);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditData.class);
                intent.putExtra("id", model.get(position).getId());
                intent.putExtra("nama", model.get(position).getNama());
                intent.putExtra("alamat", model.get(position).getAlamat());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin akan menghapus data ini?");
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        biodataTable.deleteData(model.get(position).getId());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view1;
    }
}