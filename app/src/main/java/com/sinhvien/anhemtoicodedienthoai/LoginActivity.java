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
import com.sinhvien.anhemtoicodedienthoai.model.Accounts;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
Button btnlogin, btnregister, btnforgotpass , btnasguest ;
SQLiteDatabase db;
EditText loginusername;
    EditText loginpassword;
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
    }
    private void addEvents() {
        dbActive();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountControl.checkTaiKhoan(String.valueOf(loginusername.getText()), String.valueOf(loginpassword.getText()))) {
                    ProfileFragment profileFragment= new  ProfileFragment();
                    Accounts accounts=new Accounts();
                    accounts.setUsername(loginusername.getText().toString());
                    accounts.setPassword(loginpassword.getText().toString());
                    ArrayList<Accounts> arrayList=new ArrayList<>();
                    arrayList.add(accounts);
                    profileFragment.LSAccounts= arrayList;
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
                ProfileFragment profileFragment= new  ProfileFragment();
                Accounts accounts=new Accounts();
                String loginusername="Guest";
                accounts.setUsername(loginusername);
                ArrayList<Accounts> arrayList=new ArrayList<>();
                arrayList.add(accounts);
                profileFragment.LSAccounts= arrayList;
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

    }    public void dbActive(){
        accountControl = new AccountControl(getApplicationContext(), AccountControl.DATABASE_NAME, null, 1);
        accountControl.onCreate(db);}
}