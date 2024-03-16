package com.sinhvien.anhemtoicodedienthoai.API;

import com.sinhvien.anhemtoicodedienthoai.API.type.Chapter.ChapterDetailRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Chapter.ChapterImageRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Chapter.ChapterRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Manga.MangaDetailRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Manga.MangaRespone;
import com.sinhvien.anhemtoicodedienthoai.API.type.Static.StaticRespone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/manga")
    Call<MangaRespone> getManga(
            @Query("includes[]") String[] includes,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("includedTags[]") String[] includeTagId,
            @Query("contentRating[]") String[] contentRate,
            @Query("ids[]") String[] includeIds,
            @Query("order[followedCount]") String orderFollow,
            @Query("order[rating]") String orderRate,
            @Query("order[latestUploadedChapter]") String orderNew,
            @Query("title") String title,
            @Query("includedTags[]") String[] tag
    );
    @GET("/manga/{id}")
    Call<MangaDetailRespone> getMangaDetail(
            @Path(value = "id", encoded = true) String id,
            @Query("includes[]") String[] query
    );
    @GET("/manga/tag")
    Call<MangaRespone> getMangaTag(
            @Query("limit") Integer limit,
            @Query("offset") Integer offset
    );
    @GET("/manga/{id}/feed")
    Call<ChapterRespone> getMangaChapters(
            @Path(value = "id", encoded = true) String id,
            @Query("translatedLanguage[]") String[] languages,
            @Query("includes[]") String[] includes,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("order[chapter]") String order
    );
    @GET("/manga/random")
    Call<MangaDetailRespone> getMangaRandom(
            @Query("includes[]") String[] includes,
            @Query("contentRating[]") String[] contentRate
    );
    @GET("/chapter/{id}")
    Call<ChapterDetailRespone> getChapter(
            @Path(value = "id", encoded = true) String id,
            @Query("includes[]") String[] includes
    );
    @GET("/at-home/server/{id}")
    Call<ChapterImageRespone> getChapterImageUrl(
            @Path(value = "id", encoded = true) String id
    );
    @GET("/statistics/manga/{id}")
    Call<StaticRespone> getStatistic(@Path(value = "id", encoded = true) String id);

}


