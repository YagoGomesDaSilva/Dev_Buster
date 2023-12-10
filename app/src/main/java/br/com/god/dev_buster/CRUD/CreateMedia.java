package br.com.god.dev_buster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.com.god.dev_buster.DataBase.BancoAdmin;
import br.com.god.dev_buster.MainActivity;
import br.com.god.dev_buster.R;

public class CreateMedia extends AppCompatActivity {

    TextView idMedia, mediaName, mediaDuration;

    RadioGroup radioGroup;

    Button btnRegisterMedia, btnBackCreate;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_media);

        this.idMedia = findViewById(R.id.idMediaC);
        this.mediaName = findViewById(R.id.mediaNameC);
        this.mediaDuration = findViewById(R.id.mediaDurationC);
        this.radioGroup = findViewById(R.id.radioGroupU);
        this.btnRegisterMedia = findViewById(R.id.btnUpdate);
        this.image = findViewById(R.id.imgMediaC);
        this.btnBackCreate = findViewById(R.id.btnBackUpdateC);

        this.setOnClickListenerRegister(btnRegisterMedia, idMedia, mediaName, mediaDuration, radioGroup, image);
        this.setOnClickListenerImg(image);
        this.setOnClickListenerBack(btnBackCreate);

    }

    private void setOnClickListenerRegister(Button btnRegisterMedia, TextView idMedia, TextView mediaName, TextView mediaDuration, RadioGroup radioGroup, ImageView image){
        btnRegisterMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoAdmin admin = new BancoAdmin(CreateMedia.this, "Media", null, 1);
                SQLiteDatabase banco = admin.getWritableDatabase();

                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                ContentValues transaction = new ContentValues();
                transaction.put("ID", Integer.valueOf(idMedia.getText().toString()));
                transaction.put("NAME", mediaName.getText().toString());
                transaction.put("DURATION", Integer.valueOf(mediaDuration.getText().toString()));
                transaction.put("GENRE", radioButton.getText().toString());
                transaction.put("IMAGE", convertImageViewToByteArray(image));

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
                image.setImageURI(dados.getData());
            }
        }
    }

    private byte[] convertImageViewToByteArray(ImageView imageView) {
        // Get the drawable from the ImageView
        Drawable drawable = imageView.getDrawable();

        if (drawable != null) {
            // Convert the drawable to a Bitmap
            Bitmap bitmap = drawableToBitmap(drawable);

            // Convert the Bitmap to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        return null;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // If the drawable is not a BitmapDrawable, create a new Bitmap and draw the drawable onto it
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(new Canvas(bitmap));
        return bitmap;
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