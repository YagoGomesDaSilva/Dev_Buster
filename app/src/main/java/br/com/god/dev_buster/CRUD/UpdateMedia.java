package br.com.god.dev_buster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.god.dev_buster.MainActivity;
import br.com.god.dev_buster.R;

public class UpdateMedia extends AppCompatActivity {

    Button btnBackUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_media);

        this.btnBackUpdate = findViewById(R.id.btnBackUpdateU);

        this.setOnClickListenerBack(btnBackUpdate);
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