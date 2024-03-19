package com.sinhvien.anhemtoicodedienthoai;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreditActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbarSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        setSupportActionBar(toolbarSettings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemID=item.getItemId();
        if(itemID==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
