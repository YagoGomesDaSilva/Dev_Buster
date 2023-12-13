package br.ufrn.imd.devbuster.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.ufrn.imd.devbuster.MainActivity;
import br.ufrn.imd.devbuster.R;
import br.ufrn.imd.devbuster.db.BancoAdmin;

public class CreateMedia extends AppCompatActivity {
    TextView idMedia, mediaName, mediaDuration;

    RadioGroup radioGroup;

    ImageView imgMediaC;

    Button btnRegisterMedia, btnBackCreate;

    String pathFolder = "images";

    String pathImage, fullPath;

    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_media);

        this.idMedia = findViewById(R.id.idMediaC);
        this.mediaName = findViewById(R.id.mediaNameC);
        this.mediaDuration = findViewById(R.id.mediaDurationC);
        this.radioGroup = findViewById(R.id.radioGroupC);

        this.imgMediaC = findViewById(R.id.imgMediaC);

        this.btnRegisterMedia = findViewById(R.id.btnUpdate);
        this.btnBackCreate = findViewById(R.id.btnBackUpdateC);

        this.setOnClickListenerRegister(btnRegisterMedia, idMedia, mediaName, mediaDuration, radioGroup, imgMediaC);
        this.setOnClickListenerBack(btnBackCreate);
        this.setOnClickListenerImg(imgMediaC);
    }

    private void setOnClickListenerRegister(Button btnRegisterMedia, TextView idMedia, TextView mediaName, TextView mediaDuration, RadioGroup radioGroup, ImageView img){
        btnRegisterMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFolder(v);
                saveImageToCustomFolder(imgUri);

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
                this.imgUri = dados.getData();

//                saveImageToCustomFolder(imgUri);
            }
        }
    }

    private void saveImageToCustomFolder(Uri imgUri) {
        try {
            String imagePath = getRealPathFromUri(imgUri);

            if (imagePath != null) {
                File sourceFile = new File(imagePath);
                File destinationFile = new File(fullPath, sourceFile.getName());

                if (copyFile(sourceFile, destinationFile)) {
                    this.pathImage = destinationFile.getAbsolutePath();
//                    Log.d("SavedImagePath", savedImagePath);
                } else {
//                    Log.e("SaveImage", "Failed to save image");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("SaveImage", "Exception: " + e.getMessage());
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }

        return null;
    }

    private boolean copyFile(File sourceFile, File destFile) {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    public void createFolder(View view) {
        String folderName = "imagesDevBuster";

        if (!folderName.isEmpty()) {
            File folder = new File("/cache", folderName);

            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    // Folder created successfully
                    Toast.makeText(this, "Folder created: " + folder.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    this.fullPath = folder.getAbsolutePath();
                } else {
                    // Failed to create folder
                    Toast.makeText(this, "Failed to create folder", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Folder already exists
                Toast.makeText(this, "Folder already exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Empty folder name
            Toast.makeText(this, "Please enter a folder name", Toast.LENGTH_SHORT).show();
        }
    }
}