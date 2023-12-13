package br.ufrn.imd.devbuster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrn.imd.devbuster.MainActivity;
import br.ufrn.imd.devbuster.R;
import br.ufrn.imd.devbuster.db.BancoAdmin;

public class CreateMedia extends AppCompatActivity {
    TextView idMedia, mediaName, mediaDuration;

    RadioGroup radioGroup;

    Button btnRegisterMedia, btnBackCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_media);

        this.idMedia = findViewById(R.id.idMediaC);
        this.mediaName = findViewById(R.id.mediaNameC);
        this.mediaDuration = findViewById(R.id.mediaDurationC);
        this.radioGroup = findViewById(R.id.radioGroupU);

        this.btnRegisterMedia = findViewById(R.id.btnUpdate);
        this.btnBackCreate = findViewById(R.id.btnBackUpdateC);

        this.setOnClickListenerRegister(btnRegisterMedia, idMedia, mediaName, mediaDuration, radioGroup);
        this.setOnClickListenerBack(btnBackCreate);
    }

    private void setOnClickListenerRegister(Button btnRegisterMedia, TextView idMedia, TextView mediaName, TextView mediaDuration, RadioGroup radioGroup){
        btnRegisterMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoAdmin admin = new BancoAdmin(CreateMedia.this, "DevBuster", null, 1);
                SQLiteDatabase banco = admin.getWritableDatabase();

                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                ContentValues transaction = new ContentValues();
                transaction.put("ID", idMedia.getText().toString());
                transaction.put("NAME", mediaName.getText().toString());
                transaction.put("DURATION", mediaDuration.getText().toString());
                transaction.put("GENRE", radioButton.getText().toString());

                banco.insert("MEDIA", null, transaction);
                banco.close();

                Toast.makeText(CreateMedia.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                idMedia.setText("");
                mediaName.setText("");
                mediaDuration.setText("");
                BackIntent();
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