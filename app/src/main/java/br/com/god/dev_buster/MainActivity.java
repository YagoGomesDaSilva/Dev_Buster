package br.com.god.dev_buster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.god.dev_buster.CRUD.CreateMedia;

public class MainActivity extends AppCompatActivity {

    Button btnCreateMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnCreateMedia = findViewById(R.id.btn_CreateMedia);

        OnClickListenerSetCRUD(this.btnCreateMedia, CreateMedia.class);
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