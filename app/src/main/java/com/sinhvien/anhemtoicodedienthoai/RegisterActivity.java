package com.sinhvien.anhemtoicodedienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sinhvien.anhemtoicodedienthoai.control.AccountControl;

public class RegisterActivity extends AppCompatActivity {
    Button buttonBackR, btnregi;
    EditText regiusername, regipassword, regiemail;
    AccountControl accountControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();

    }

    private void addEvents() {
        buttonBackR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentback= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intentback);
            }
        });
        btnregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(regiusername.getText()).length() >= 4 && String.valueOf(regipassword.getText()).length() >=4 ) {
                    accountControl= new AccountControl(getApplicationContext(), AccountControl.DATABASE_NAME, null, 1);
                    accountControl.insertData(String.valueOf(regiusername.getText()), String.valueOf(regipassword.getText()), String.valueOf(regiemail.getText()));
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("username",String.valueOf(regiusername.getText()));
                            intent.putExtra("password",String.valueOf(regipassword.getText()));
                            intent.putExtra("email",String.valueOf(regiemail.getText()));
                            startActivity(intent);
                        }
                    },2000);
                }else Toast.makeText(RegisterActivity.this, "The password is too weak", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        buttonBackR=findViewById(R.id.buttonBackR);
        btnregi=findViewById(R.id.btnregi);
        regiusername=findViewById(R.id.regiusername);
        regipassword=findViewById(R.id.regipassword);
        regiemail=findViewById(R.id.regiemail);
    }
}