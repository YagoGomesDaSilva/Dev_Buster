package br.com.god.dev_buster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.god.dev_buster.CRUD.CreateMedia;
import br.com.god.dev_buster.CRUD.DeleteMedia;
import br.com.god.dev_buster.CRUD.ListMedia;
import br.com.god.dev_buster.CRUD.UpdateMedia;

public class MainActivity extends AppCompatActivity {

    Button btnCreateMedia,btnListMedia,btnDeleteMedia,btnUpdateMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnCreateMedia = findViewById(R.id.btn_CreateMedia);
        this.btnListMedia = findViewById(R.id.btn_ListMedia);
        this.btnDeleteMedia = findViewById(R.id.btn_DeleteMedia);
        this.btnUpdateMedia = findViewById(R.id.btn_UpdateMedia);

        OnClickListenerSetCRUD(this.btnCreateMedia, CreateMedia.class);
        OnClickListenerSetCRUD(this.btnListMedia, ListMedia.class);
        OnClickListenerSetCRUD(this.btnDeleteMedia, DeleteMedia.class);
        OnClickListenerSetCRUD(this.btnUpdateMedia, UpdateMedia.class);
    }

    private void OnClickListenerSetCRUD(Button btn, Class<?> cls ) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
                finish();
            }
        });
    }
}