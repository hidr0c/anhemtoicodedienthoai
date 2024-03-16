package com.sinhvien.anhemtoicodedienthoai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sinhvien.anhemtoicodedienthoai.API.ApiService;
import com.sinhvien.anhemtoicodedienthoai.API.type.Manga.MangaDetailRespone;
import com.sinhvien.anhemtoicodedienthoai.Home.HomeFragment;
import com.sinhvien.anhemtoicodedienthoai.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeLayout;

    ActivityMainBinding binding;
    androidx.appcompat.widget.Toolbar toolbarMain;

    final FragmentManager fm = getSupportFragmentManager();
    Fragment currFragment;
    Fragment active;
    BottomNavigationView navBar;
    FrameLayout frame;
    ListView lvmanga;

    enum Screen {
        Home,
        Bookmarks,
        Search,
        Profile,
        Filter
    }
    Screen screen=Screen.Home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navBar = findViewById(R.id.bottomNavigationView);

        navBar.setItemBackgroundResource(R.drawable.bottom_nav_bar_item_bg);
        Menu menu = navBar.getMenu();

        toolbarMain = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);

        currFragment = fm.findFragmentById(R.id.flMain);
        final Fragment homeFragment = new HomeFragment();
        final Fragment bookmarksFragment = new BookmarksFragment();
        final Fragment searchFragment = new SearchFragment();
        final Fragment profileFragment = new ProfileFragment();
        final Fragment filterFragment = new SearchFragment();
        if(currFragment == null){
            addAllFragment(homeFragment, bookmarksFragment ,searchFragment, profileFragment ,filterFragment);
            active = fm.findFragmentByTag("1");
        }
//        updateNavBar();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(MainActivity.this, "Refreshing list...", Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);

                //refresh stuff here

                if(screen == Screen.Home) {
                    Fragment fragment = new HomeFragment();
                    fm.beginTransaction()
                            .detach(fm.findFragmentByTag("1"))
                            .add(R.id.flMain, fragment, "1")
                            .commit();
                    active = fragment;
                }

                if(screen == Screen.Bookmarks) {
                    Fragment fragment = new BookmarksFragment();
                    fm.beginTransaction()
                            .remove(fm.findFragmentByTag("2"))
                            .add(R.id.flMain, fragment, "2")
                            .commit();
                    active = fragment;
                }
                if(screen == Screen.Search) {
                    Fragment fragment = new SearchFragment();
                    fm.beginTransaction()
                            .remove(fm.findFragmentByTag("3"))
                            .add(R.id.flMain, fragment, "3")
                            .commit();
                    active = fragment;
                }
                if (screen== Screen.Profile){
                    Fragment fragment = new SearchFragment();
                    fm.beginTransaction()
                            .remove(fm.findFragmentByTag("4"))
                            .add(R.id.flMain, fragment, "4")
                            .commit();
                    active = fragment;
                }
                if(screen == Screen.Filter) {
                    Fragment fragment = new SearchFragment();
                    String filterId = fm.findFragmentByTag("5").getArguments().getString("filterId");
                    Bundle bundle = new Bundle();
                    bundle.putString("filterId", filterId);
                    fragment.setArguments(bundle);

                    fm.beginTransaction()
                            .remove(fm.findFragmentByTag("5"))
                            .add(R.id.flMain, fragment, "5")
                            .commit();
                    active = fragment;
                }
            }

        });

    }

    private void addAllFragment(Fragment fragment1, Fragment fragment2, Fragment fragment3,Fragment fragment4, Fragment fragment5) {
        fm.beginTransaction()
                .add(R.id.flMain, fragment5, "5")
                .hide(fragment5)
                .add(R.id.flMain, fragment4, "4")
                .hide(fragment4)
                .add(R.id.flMain, fragment3, "3")
                .hide(fragment3)
                .add(R.id.flMain, fragment2, "2")
                .hide(fragment2)
                .add(R.id.flMain, fragment1, "1")
                .commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maintoolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemRandom) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.mangadex.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<MangaDetailRespone> mangaCall = apiService.getMangaRandom(null, new String[] {"safe", "suggestive"});
            mangaCall.enqueue(new Callback<MangaDetailRespone>() {
                @Override
                public void onResponse(Call<MangaDetailRespone> call, Response<MangaDetailRespone> response) {
                    if (response.isSuccessful()) {
                        MangaDetailRespone res = response.body();
                        Intent intent = new Intent(MainActivity.this, MangaDetailRespone.class);
                        intent.putExtra("mangaId", res.data.id);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<MangaDetailRespone> call, Throwable t) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
            onBackPressedFragment();

        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
    private void onBackPressedFragment(){
        if(fm.findFragmentByTag("1").isVisible()){
//            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(fm.findFragmentByTag("2").isVisible()){
//            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

            navBar.setSelectedItemId(R.id.home);
            fm.beginTransaction()
                    .hide(fm.findFragmentByTag("5"))
                    .hide(fm.findFragmentByTag("4"))
                    .hide(fm.findFragmentByTag("3"))
                    .hide(fm.findFragmentByTag("2"))
                    .show(fm.findFragmentByTag("1"))
                    .commit();
            active = fm.findFragmentByTag("1");
            toolbarMain.setTitle("Home");
            screen = Screen.Home;
        }
        else if(fm.findFragmentByTag("3").isVisible()){
//            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();

            navBar.setSelectedItemId(R.id.bookmarks);
            fm.beginTransaction()
                    .hide(fm.findFragmentByTag("5"))
                    .hide(fm.findFragmentByTag("4"))
                    .hide(fm.findFragmentByTag("3"))
                    .show(fm.findFragmentByTag("2"))
                    .hide(fm.findFragmentByTag("1"))
                    .commit();
            active = fm.findFragmentByTag("2");
            toolbarMain.setTitle("Bookmarks");
            screen = Screen.Bookmarks;
        }
        else if(fm.findFragmentByTag("3").isVisible()){
//            Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();

            navBar.setSelectedItemId(R.id.profile);
            fm.beginTransaction()
                    .hide(fm.findFragmentByTag("5"))
                    .hide(fm.findFragmentByTag("4"))
                    .show(fm.findFragmentByTag("3"))
                    .hide(fm.findFragmentByTag("2"))
                    .hide(fm.findFragmentByTag("1"))
                    .commit();
            active = fm.findFragmentByTag("3");
            toolbarMain.setTitle("Profile");
            screen = Screen.Profile;
        }
        else if(fm.findFragmentByTag("4").isVisible()){
//            Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();

            navBar.setSelectedItemId(R.id.search);
            fm.beginTransaction()
                    .hide(fm.findFragmentByTag("5"))
                    .show(fm.findFragmentByTag("4"))
                    .hide(fm.findFragmentByTag("3"))
                    .hide(fm.findFragmentByTag("2"))
                    .hide(fm.findFragmentByTag("1"))
                    .commit();
            active = fm.findFragmentByTag("3");
            toolbarMain.setTitle("Search");
            screen = Screen.Search;
        }
        else if (fm.findFragmentByTag("5").isVisible()) {
            navBar.setSelectedItemId(R.id.search);
            fm.beginTransaction()
                    .hide(fm.findFragmentByTag("5"))
                    .hide(fm.findFragmentByTag("4"))
                    .hide(fm.findFragmentByTag("3"))
                    .show(fm.findFragmentByTag("2"))
                    .hide(fm.findFragmentByTag("1"))
                    .commit();
            active = fm.findFragmentByTag("2");
            toolbarMain.setTitle("Search");
            screen = Screen.Search;
        }
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
                .remove(fm.findFragmentByTag("5"))
                .add(R.id.flMain, fragment, "5")
                .show(fragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        active = fragment;
        toolbarMain.setTitle("Filter: " + name);
    }
}