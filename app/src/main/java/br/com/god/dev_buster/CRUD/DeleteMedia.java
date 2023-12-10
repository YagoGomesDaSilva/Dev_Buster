package br.com.god.dev_buster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.god.dev_buster.MainActivity;
import br.com.god.dev_buster.R;

public class DeleteMedia extends AppCompatActivity {

    Button btnBackDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_media);

        this.btnBackDelete = findViewById(R.id.btnBackDeleteD);

        this.setOnClickListenerBack(btnBackDelete);

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