package br.com.god.dev_buster.UserAuthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.god.dev_buster.MainActivity;
import br.com.god.dev_buster.R;

public class Login extends AppCompatActivity {

    EditText et_UserName, et_PassWordName;
    Button btn_SignIn, btn_SignOn;
    TextView btn_forgot_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.et_UserName = findViewById(R.id.username);
        this.et_PassWordName = findViewById(R.id.newPassword);
        this.btn_SignIn = findViewById(R.id.btn_update_login);
        this.btn_SignOn = findViewById(R.id.btn_back_forgot);
        this.btn_forgot_login = findViewById(R.id.btn_forgot_login);

        this.OnClickListenerSignIn(btn_SignIn, et_UserName, et_PassWordName);
        this.OnClickListenerSignOn(btn_SignOn);
        this.OnClickListenerForgotLogin(btn_forgot_login);

        SharedPreferences sharedPreferences = getSharedPreferences("initialCredentials", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("admin", "admin");
        editor.clear();
        editor.commit();
    }

    private void OnClickListenerSignIn(Button btn_SignIn, EditText et_UserName, EditText et_PassWordName) {
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences credentials = getSharedPreferences("credentials", Context.MODE_PRIVATE);

                String credential = credentials.getString(et_UserName.getText().toString(), "");
                if(credential.isEmpty()){
                    Toast.makeText(Login.this, "Login inexistente!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!credential.equals(et_PassWordName.getText().toString())){
                    Toast.makeText(Login.this, "Login ou senha incorretos!", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void OnClickListenerSignOn(Button btn_SignOn) {
        btn_SignOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, RegisterLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void OnClickListenerForgotLogin(TextView btn_forgot_login){
        btn_forgot_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgottenLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

}