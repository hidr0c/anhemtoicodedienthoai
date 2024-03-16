package com.sinhvien.anhemtoicodedienthoai;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {



    FragmentManager fm = getSupportFragmentManager();
    BottomNavigationView navBar;
    FrameLayout frame;
    ListView lvmanga;
    enum Screen {
        Library,
        Browse,
        Search,
        More,
        Filter
    }
    Screen screen=Screen.Library;


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
                    loadFragment(fm,new BookmarksFragment());
                  
                   /* Toast.makeText(MainActivity.this, "You need an account to use this feature ", Toast.LENGTH_SHORT).show();*/
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

    public void filterManga(String id, String name) {
        screen = Screen.Filter;
        Bundle bundle = new Bundle();
        bundle.putString("filterId", id);
        Fragment fragment = new SearchFragment();
        fragment.setArguments(bundle);

        fm.beginTransaction()
                .hide(active)
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                .remove(fragmentManager.findFragmentByTag("5"))
                .add(R.id.flMain, fragment, "5")
                .show(fragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        active = fragment;
        toolbarMain.setTitle("Filter: " + name);
    }
}