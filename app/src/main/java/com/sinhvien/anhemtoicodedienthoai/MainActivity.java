package com.sinhvien.anhemtoicodedienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sinhvien.anhemtoicodedienthoai.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

     ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*Issue here*/
        /*binding.*/
            
        });




    }
}