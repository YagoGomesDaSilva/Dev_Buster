package br.ufrn.imd.devbuster.CRUD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import br.ufrn.imd.devbuster.Adapter;
import br.ufrn.imd.devbuster.MainActivity;
import br.ufrn.imd.devbuster.Media;
import br.ufrn.imd.devbuster.R;
import br.ufrn.imd.devbuster.db.BancoAdmin;

public class ListMedia extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Media> items;

    Button btn_BackList_CRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_media);

        this.btn_BackList_CRUD = findViewById(R.id.btnBackListL);

        this.prepararItens();
        recyclerView = findViewById(R.id.listMediaL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, items);
        recyclerView.setAdapter(adapter);

        this.setOnClickListenerBack(btn_BackList_CRUD);
    }

    private void prepararItens() {
        BancoAdmin admin = new BancoAdmin(ListMedia.this, "DevBuster", null, 1);
        SQLiteDatabase banco = admin.getWritableDatabase();

        ContentValues transaction = new ContentValues();
//        transaction.put("ID", "1");
//        transaction.put("NAME", "O Rei Leão");
//        transaction.put("GENRE", "Comédia");
//        transaction.put("DURATION", "90");
//
//        banco.insert("MEDIA", null, transaction);
//
//        transaction.put("ID", "2");
//        transaction.put("NAME", "Barbie");
//        transaction.put("GENRE", "Musical");
//        transaction.put("DURATION", "120");

        banco.insert("MEDIA", null, transaction);

        Cursor consulta = banco.query("MEDIA", null, null, null, null, null, null);

        items = new ArrayList<>();
        while(consulta.moveToNext()) {
            int id = consulta.getInt(consulta.getColumnIndexOrThrow("ID"));
            String name = consulta.getString(consulta.getColumnIndexOrThrow("NAME"));
            String genre = consulta.getString(consulta.getColumnIndexOrThrow("GENRE"));
            int duration = consulta.getInt(consulta.getColumnIndexOrThrow("DURATION"));

            this.items.add(new Media(id, name, genre, duration));
        }

        banco.close();
    }

    private void BackIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setOnClickListenerBack(Button btn ){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackIntent();
            }
        });
    }
}