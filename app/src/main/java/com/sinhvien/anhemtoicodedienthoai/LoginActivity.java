package com.sinhvien.anhemtoicodedienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sinhvien.anhemtoicodedienthoai.control.AccountControl;

public class LoginActivity extends AppCompatActivity {
Button btnlogin, btnregister, btnforgotpass , btnasguest ;
SQLiteDatabase db;
EditText loginusername, loginpassword;
AccountControl accountControl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
        Intent intent = getIntent();
        loginusername.setText(intent.getStringExtra("username"));
        loginpassword.setText(intent.getStringExtra("password"));
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginpassword.getText().toString();
                String password= loginusername.getText().toString();
                if(username.equals("Admin") && password.equals("Admin"))
                {
                    Intent intent = new Intent(LoginActivity.this, HomeFragment.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void addEvents() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountControl.checkTaiKhoan(String.valueOf(loginusername.getText()), String.valueOf(loginpassword.getText()))) {
                    Intent intentCon3 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentCon3);
                } else{
                    Toast.makeText(LoginActivity.this, "Wrong username or password. Please check again", Toast.LENGTH_LONG).show();
                    loginusername.setText("");
                    loginpassword.setText("");
                }

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnasguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }




    public void addControls(){
        btnlogin=findViewById(R.id.login);
        btnregister=findViewById(R.id.register);
        btnforgotpass=findViewById(R.id.forgotpass);
        btnasguest=findViewById(R.id.asguest);
        loginusername=findViewById(R.id.LoginUsername);
        loginpassword=findViewById(R.id.LoginPassword);

    }
}