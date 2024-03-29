package com.sinhvien.anhemtoicodedienthoai.Detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sinhvien.anhemtoicodedienthoai.API.ApiService;
import com.sinhvien.anhemtoicodedienthoai.API.type.Chapter.Chapter;
import com.sinhvien.anhemtoicodedienthoai.API.type.Chapter.ChapterRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Manga.MangaDetailRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.AuthorArtist;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.CoverArt;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.Relationship;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.RelationshipDeserializer;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.ScanlationGroup;
import com.sinhvien.anhemtoicodedienthoai.API.type.Static.StaticRespone;
import com.sinhvien.anhemtoicodedienthoai.R;
import com.sinhvien.anhemtoicodedienthoai.Reader.ReaderActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangaInfoActivity extends AppCompatActivity {

    Chapter[] data;
    ArrayAdapter adapter;
    ListView listView;
    androidx.appcompat.widget.Toolbar toolbarMangaInfo;
    TextView tvMangaTitle, tvMangaAuthor, tvMangaArtist, tvMangaStatus, tvMangaRating, tvChapterList;
    ImageView ivMangaView;
    String mangaId = "5b93fa0f-0640-49b8-974e-954b9959929b", mangaName = "";
    Button btnMangaWebview, btnLangFilter, btnMangaAdd, btnResume;
    Resources resources;
    int language;
    SharedPreferences languagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_info);
        resources = getResources();

        tvMangaTitle = findViewById(R.id.tvMangaTitle);
        tvMangaAuthor = findViewById(R.id.tvMangaAuthor);
        tvMangaArtist = findViewById(R.id.tvMangaArtist);
        tvMangaRating = findViewById(R.id.tvMangaRating);
        tvChapterList = findViewById(R.id.tvChapterList);
        tvMangaStatus = findViewById(R.id.tvMangaStatus);

        btnMangaWebview = findViewById(R.id.btnMangaWebview);
        btnLangFilter = findViewById(R.id.btnLangFilter);
        btnMangaAdd = findViewById(R.id.btnMangaAdd);
        btnResume = findViewById(R.id.btnResume);

        ivMangaView = findViewById(R.id.ivMangaView);

        toolbarMangaInfo = findViewById(R.id.toolbarMangaInfo);
        setSupportActionBar(toolbarMangaInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);

        listView = findViewById(R.id.listMangaChapter);
        listView.setClickable(true);
        listView.setNestedScrollingEnabled(true);

        if (getIntent().getStringExtra("mangaId") != null) {
            mangaId = getIntent().getStringExtra("mangaId");
        }

        languagePref = MangaInfoActivity.this.getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        language = languagePref.getInt("language", 0);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Relationship.class, new RelationshipDeserializer());
        Gson gson = gsonBuilder.create();

        SharedPreferences prefs = MangaInfoActivity.this.getSharedPreferences("library",Context.MODE_PRIVATE);

        Set<String> set = prefs.getStringSet("library", null);
        List<String> libraryList = new ArrayList<>();
        if (set != null) {
            libraryList = new ArrayList<String>(set);
        }

        if (libraryList.contains(mangaId)) {
            btnMangaAdd.setText(R.string.Manga_save_btn_selected);
            btnMangaAdd.setTextColor(getColor(R.color.main_blue));
            btnMangaAdd.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_book_saved_selected), null, null, null);
            btnMangaAdd.setBackground(AppCompatResources.getDrawable(this, R.drawable.button_primary_selected));
        } else {
            btnMangaAdd.setText(R.string.Manga_save_btn);
            btnMangaAdd.setTextColor(getColor(R.color.white));
            btnMangaAdd.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_book_saved), null, null, null);
            btnMangaAdd.setBackground(AppCompatResources.getDrawable(this, R.drawable.button_primary_shape));
        }

        prefs = MangaInfoActivity.this.getSharedPreferences("chapter", Context.MODE_PRIVATE);

        String savedChapterId = prefs.getString("savedChapter" + mangaId, null);

        if (savedChapterId == null) {
            btnResume.setVisibility(View.GONE);
        }

        Retrofit retrofitRelate = new Retrofit.Builder()
                .baseUrl("https://api.mangadex.org/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://api.mangadex.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiRelateService = retrofitRelate.create(ApiService.class);
        ApiService apiService = retrofit.create(ApiService.class);

        Call<MangaDetailRespone> mangaApiCall = apiRelateService.getMangaDetail(mangaId, new String[]{"author", "artist", "cover_art"});
        Call<StaticRespone> ratingCall = apiService.getStatistic(mangaId);
        mangaApiCall.enqueue(new Callback<MangaDetailRespone>() {
            @Override
            public void onResponse(@NonNull Call<MangaDetailRespone> call, @NonNull Response<MangaDetailRespone> response) {
                if (response.isSuccessful()) {
                    MangaDetailRespone res = response.body();
                    if (res.data.attributes.title.en != null) {
                        mangaName = res.data.attributes.title.en;
                    } else if (res.data.attributes.title.ja != null) {
                        mangaName = res.data.attributes.title.ja;
                    } else if (res.data.attributes.title.ja_ro != null) {
                        mangaName = res.data.attributes.title.ja_ro;
                    }
                    tvMangaTitle.setText(mangaName);
                    expTv1.setText(res.data.attributes.description.en);
                    tvMangaStatus.setText("Status: " + res.data.attributes.status.substring(0, 1).toUpperCase() + res.data.attributes.status.substring(1));
                    Relationship[] relationship = res.data.relationships;
                    for (int i = 0; i < relationship.length; i++) {
                        switch (relationship[i].type) {
                            case "author":
                                tvMangaAuthor.setText(resources.getString(R.string.Manga_author, ((AuthorArtist)relationship[i].attribute).name));
                                break;
                            case "artist":
                                tvMangaArtist.setText(resources.getString(R.string.Manga_artist, ((AuthorArtist)relationship[i].attribute).name));
                                break;
                            case "cover_art":
                                Picasso.get().load("https://uploads.mangadex.org/covers/" + mangaId + "/" + ((CoverArt)relationship[i].attribute).fileName).into(ivMangaView);
                                break;
                        }
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<MangaDetailRespone> call, Throwable t) {
                Log.e("err", t.toString());
                Toast.makeText(MangaInfoActivity.this, "Unable to get manga info", Toast.LENGTH_SHORT).show();
            }
        });
        String[] lang = new String[]{"en"};

        if (language == 1) {
            lang = new String[]{"vi"};
        } else if (language == 2) {
            lang = new String[]{"ja", "ja-ro"};
        }

        getChapter(apiRelateService, lang);
        ratingCall.enqueue(new Callback<StaticRespone>() {
            @Override
            public void onResponse(Call<StaticRespone> call, Response<StaticRespone> response) {
                if (response.isSuccessful()) {
                    StaticRespone res = response.body();
                    tvMangaRating.setText(String.format("%.2f", res.statistics.get(mangaId).rating.avg));
                }
            }

            @Override
            public void onFailure(Call<StaticRespone> call, Throwable t) {
                Log.e("err", t.toString());
                Toast.makeText(MangaInfoActivity.this, "Unable to get manga rating", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MangaInfoActivity.this, ReaderActivity.class);
                ArrayList<String> chapterList = new ArrayList<String>();
                for (int j = 0; j < data.length; j++) {
                    chapterList.add(data[j].id);
                }

                intent.putExtra("id", data[i].id);
                intent.putExtra("mangaId", mangaId);
                intent.putExtra("chapterList", chapterList.toArray(new String[0]));
                intent.putExtra("mangaName", mangaName);

                startActivity(intent);
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangaInfoActivity.this, ReaderActivity.class);
                ArrayList<String> chapterList = new ArrayList<String>();
                for (int j = 0; j < data.length; j++) {
                    chapterList.add(data[j].id);
                }

                intent.putExtra("id", savedChapterId);
                intent.putExtra("mangaId", mangaId);
                intent.putExtra("chapterList", chapterList.toArray(new String[0]));
                intent.putExtra("mangaName", mangaName);

                startActivity(intent);
            }
        });

        btnMangaWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://mangadex.org/title/" + mangaId));
                startActivity(viewIntent);
            }
        });

        btnLangFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MangaInfoActivity.this);

                String[] items = new String[]{"English", "Vietnamese", "Japanese"};
                builder.setTitle("Filtered by Language");
                SharedPreferences.Editor editor = languagePref.edit();
                builder.setSingleChoiceItems(items,  language, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            editor.putInt("language", 0).apply();
                            getChapter(apiRelateService, new String[]{"en"});
                        } else if (i == 1) {
                            editor.putInt("language", 1).apply();
                            getChapter(apiRelateService, new String[]{"vi"});
                        } else if (i == 2) {
                            editor.putInt("language", 2).apply();
                            getChapter(apiRelateService, new String[]{"ja", "ja-ro"});
                        }
                    }
                });

                builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                builder.create();

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnMangaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnMangaAdd.getText().equals("ADD TO LIBRARY")) {
                    SharedPreferences prefs= MangaInfoActivity.this.getSharedPreferences("library",Context.MODE_PRIVATE);

                    Set<String> set = prefs.getStringSet("library", null);
                    List<String> libraryList = new ArrayList<>();
                    if (set != null) {
                        libraryList = new ArrayList<String>(set);
                    }

                    SharedPreferences.Editor edit = prefs.edit();
                    libraryList.add(mangaId);

                    set = new HashSet<String>();
                    set.addAll(libraryList);
                    edit.putStringSet("library", set);
                    edit.commit();
                    Toast.makeText(MangaInfoActivity.this, "Manga added to library!", Toast.LENGTH_SHORT).show();

                    btnMangaAdd.setText(R.string.Manga_save_btn_selected);
                    btnMangaAdd.setTextColor(getColor(R.color.main_blue));
                    btnMangaAdd.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(MangaInfoActivity.this, R.drawable.ic_book_saved_selected), null, null, null);
                    btnMangaAdd.setBackground(AppCompatResources.getDrawable(MangaInfoActivity.this, R.drawable.button_primary_selected));

                } else {
                    SharedPreferences prefs= MangaInfoActivity.this.getSharedPreferences("library",Context.MODE_PRIVATE);

                    Set<String> set = prefs.getStringSet("library", null);
                    List<String> libraryList = new ArrayList<>();
                    if (set != null) {
                        libraryList = new ArrayList<String>(set);
                    }

                    SharedPreferences.Editor edit = prefs.edit();
                    libraryList.remove(mangaId);

                    set = new HashSet<String>();
                    set.addAll(libraryList);
                    edit.putStringSet("library", set);
                    edit.commit();
                    Toast.makeText(MangaInfoActivity.this, "Manga removed from library!", Toast.LENGTH_SHORT).show();

                    btnMangaAdd.setText(R.string.Manga_save_btn);
                    btnMangaAdd.setTextColor(getColor(R.color.white));
                    btnMangaAdd.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(MangaInfoActivity.this, R.drawable.ic_book_saved), null, null, null);
                    btnMangaAdd.setBackground(AppCompatResources.getDrawable(MangaInfoActivity.this, R.drawable.button_primary_shape));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mangainfo_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.shareManga) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://mangadex.org/title/" + mangaId);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        } else if (itemID == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayAdapter getChapterAdapter() {
        return new ArrayAdapter(MangaInfoActivity.this,
                R.layout.chapter_list_item, R.id.textName, data) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View item = super.getView(position, convertView, parent);
                ((TextView) item.findViewById(R.id.textName)).setText("Chapter " + data[position].attributes.chapter + " - " + data[position].attributes.title);

                String scanlationGroupName = "";
                for (int i = 0; i < data[position].relationships.length; i++) {
                    if (data[position].relationships[i].type.equals("scanlation_group")) {
                        scanlationGroupName = ((ScanlationGroup) data[position].relationships[i].attribute).name;
                    }
                }
                ((TextView) item.findViewById(R.id.textScanlation)).setText(scanlationGroupName);

                return item;
            }
        };
    }

    private void getChapter(ApiService apiService, String[] language) {
        Call<ChapterRespone> chapterCall = apiService.getMangaChapters(mangaId, language, new String[]{"scanlation_group"}, 500, 0, "asc");

        chapterCall.enqueue(new Callback<ChapterRespone>() {
            @Override
            public void onResponse(Call<ChapterRespone> call, Response<ChapterRespone> response) {
                Log.i("call", response.toString());
                if (response.isSuccessful()) {
                    ChapterRespone res = response.body();
                    data = res.data;
                    tvChapterList.setText(data.length + " Chapters");
                    adapter = getChapterAdapter();
                    listView.setAdapter(adapter);
                    listView.invalidateViews();
                } else {
                    Toast.makeText(MangaInfoActivity.this, "Unable to get manga chapters", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChapterRespone> call, Throwable t) {
                Log.e("err", t.toString());
                Toast.makeText(MangaInfoActivity.this, "Unable to get manga chapters", Toast.LENGTH_SHORT).show();
            }
        });
    }
}