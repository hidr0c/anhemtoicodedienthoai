package com.sinhvien.anhemtoicodedienthoai;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    FragmentManager fm = getSupportFragmentManager();
    BottomNavigationView navBar;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(fm,new HomeFragment());
        frame = findViewById(R.id.frame_Layout);
        navBar = findViewById(R.id.bottomNavigationView);
        addEvent();
    }

    public void addEvent()
    {
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.home){
                    loadFragment(fm,new HomeFragment());
                    return true;
                }
                if(id==R.id.profile){
                    loadFragment(fm,new ProfileFragment());
                    return true;
                }
                if(id==R.id.bookmarks)
                {
                    /*loadFragment(fm,new BookmarksFragment());*/
                    Toast.makeText(MainActivity.this, "You need an account to use this feature ", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(id==R.id.settings)
                {
                    loadFragment(fm,new SettingsFragment());
                    return true;
                }
                if(id==R.id.search)
                {
                    loadFragment(fm,new SearchFragment());
                    return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(FragmentManager fm, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_Layout,fragment);
        ft.commit();
    }
}