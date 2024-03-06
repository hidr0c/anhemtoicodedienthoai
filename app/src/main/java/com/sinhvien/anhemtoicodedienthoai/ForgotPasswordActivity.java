package com.sinhvien.anhemtoicodedienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPasswordActivity extends AppCompatActivity {
Button buttonBackF,btnforgotpasssumbit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        addControls();



        buttonBackF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        buttonBackF=findViewById(R.id.buttonBackF);
    }
}