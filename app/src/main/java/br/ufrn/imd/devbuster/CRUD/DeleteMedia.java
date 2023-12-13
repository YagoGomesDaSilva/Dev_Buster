package br.ufrn.imd.devbuster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrn.imd.devbuster.MainActivity;
import br.ufrn.imd.devbuster.R;
import br.ufrn.imd.devbuster.db.BancoAdmin;

public class DeleteMedia extends AppCompatActivity {

    Button btn_Delete_CRUD, btn_BackDelete_CRUD;

    TextView idMediaD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_media);

        this.btn_Delete_CRUD = findViewById(R.id.btnDelete);
        this.btn_BackDelete_CRUD = findViewById(R.id.btnBackDeleteD);

        this.idMediaD = findViewById(R.id.idMediaD);
        setOnClickListenerDelete(btn_Delete_CRUD);
        setOnClickListenerBack(btn_BackDelete_CRUD);
    }

    private void setOnClickListenerDelete(Button btn ){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoAdmin admin = new BancoAdmin(DeleteMedia.this, "DevBuster", null, 1);
                SQLiteDatabase banco = admin.getWritableDatabase();

                int deletedRows = banco.delete("MEDIA", "ID = ?", new String[]{idMediaD.getText().toString()});
                banco.close();

                if(deletedRows < 1) {
                    Toast.makeText(DeleteMedia.this, "ID da mídia não encontrado!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DeleteMedia.this, "Mídia excluído com sucesso!", Toast.LENGTH_LONG).show();
                    BackIntent();
                }
            }
        });
    }

    private void setOnClickListenerBack(Button btn ){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackIntent();
            }
        });
    }

    private void BackIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}