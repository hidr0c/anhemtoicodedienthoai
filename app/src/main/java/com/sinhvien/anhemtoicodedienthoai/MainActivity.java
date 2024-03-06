package com.sinhvien.anhemtoicodedienthoai;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.sinhvien.anhemtoicodedienthoai.model.Manga;

import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

public class MainActivity extends AppCompatActivity {



    FragmentManager fm = getSupportFragmentManager();
    BottomNavigationView navBar;
    FrameLayout frame;
    public String DATABASE_NAME="TruyenTranh";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database=null;
    ListView lvmanga;
    ArrayAdapter<Manga> adapterManga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(fm,new HomeFragment());
        frame = findViewById(R.id.frame_Layout);
        navBar = findViewById(R.id.bottomNavigationView);
        addEvent();
    /*    processCopy();*/

    }
   /* @Override
    protected void onResume(){
        super.onResume();
        adapterManga.clear();
        loadData();
    }

    private void loadData() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor= database.rawQuery("select* from TruyenTranh",null );
        while (cursor.moveToNext()){
            String IDTruyen=cursor.getString(0);
            String MangaName=cursor.getString(1);
            byte[] MangaImage=cursor.getBlob(2);
            String MangaDes=cursor.getString(3);
            Manga u=new Manga(IDTruyen,MangaName,MangaImage,MangaDes);
            adapterManga.add(u);
        }
        cursor.close();
    }
    public  String getDataBasePath()
    {
        return  getApplicationInfo().dataDir+ DB_SUFFIX_PATH+DATABASE_NAME;
    }

    private void processCopy() {
        try {
            File file=getDatabasePath(DATABASE_NAME);
            if(!file.exists()) {
                copyDatabaseFromAssest();
                Toast.makeText(this, "Copy Database Successful", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex){
            Toast.makeText(this, "Copy Database Fail", Toast.LENGTH_SHORT).show();
        }
    }
    private void copyDatabaseFromAssest() {
        try {
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName = getDataBasePath();
            File file = new File(getApplicationInfo().dataDir+DB_SUFFIX_PATH);
            if(!file.exists())
                file.mkdir();
            OutputStream outFile= new FileOutputStream(outputFileName);
            byte []buffer=new byte[1024];
            int length;
            while ((length=inputFile.read(buffer))>0)outFile.write(buffer,0,length);
            outFile.flush();
            outFile.close();
            inputFile.close();
        }
        catch (Exception ex){

            Log.e("Error",ex.toString());
        }
    }*/

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
                    return false;
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