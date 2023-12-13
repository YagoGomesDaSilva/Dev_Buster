package br.ufrn.imd.devbuster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.ufrn.imd.devbuster.MainActivity;
import br.ufrn.imd.devbuster.R;
import br.ufrn.imd.devbuster.db.BancoAdmin;

public class UpdateMedia extends AppCompatActivity {

    Button btn_Update_CRUD, btn_BackUpdate_CRUD;

    TextView idMediaU, mediaNameU, mediaDurationU;

    ImageView imgMediaU;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_media);

        this.idMediaU = findViewById(R.id.idMediaU);
        this.mediaNameU = findViewById(R.id.mediaNameU);
        this.mediaDurationU = findViewById(R.id.mediaDurationU);
        this.radioGroup = findViewById(R.id.radioGroupU);

        this.imgMediaU = findViewById(R.id.imgMediaU);

        this.btn_Update_CRUD = findViewById(R.id.btnUpdate);
        this.btn_BackUpdate_CRUD = findViewById(R.id.btnBackUpdateU);

        setOnClickListenerUpdate(btn_Update_CRUD, idMediaU, mediaNameU, mediaDurationU, radioGroup);
        this.setOnClickListenerBack(btn_BackUpdate_CRUD);

        this.setOnClickListenerImg(imgMediaU);
    }

    private void setOnClickListenerUpdate(Button btn, TextView idMediaU,TextView mediaNameU, TextView  mediaDurationU, RadioGroup radioGroup){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BancoAdmin admin = new BancoAdmin(UpdateMedia.this, "DevBuster", null, 1);
                SQLiteDatabase banco = admin.getWritableDatabase();

                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                if( ! idMediaU.getText().toString().isEmpty() )
                {
                    ContentValues transaction = new ContentValues();
                    transaction.put("ID", idMediaU.getText().toString());
                    transaction.put("NAME", mediaNameU.getText().toString());
                    transaction.put("DURATION", mediaDurationU.getText().toString());
                    transaction.put("GENRE", radioButton.getText().toString());

                    banco.update("MEDIA", transaction,"ID =" + idMediaU.getText().toString(),null);
                    banco.close();

                    Toast.makeText(UpdateMedia.this, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                    idMediaU.setText("");
                    mediaNameU.setText("");
                    mediaDurationU.setText("");
                }
                else {
                    Toast.makeText(UpdateMedia.this, "Preencha o codigo da mídia!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void setOnClickListenerImg(ImageView image){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua imagem"), 1);

            }
        });
    }

    protected void onActivityResult(int RequestCode, int ResultCode, Intent dados) {
        super.onActivityResult(RequestCode, ResultCode, dados);
        if (ResultCode == Activity.RESULT_OK) {
            if (ResultCode == 1) {
                imgMediaU.setImageURI(dados.getData());
            }
        }
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