package com.sinhvien.anhemtoicodedienthoai.model;

import java.io.Serializable;
import java.sql.Blob;

public class Manga implements Serializable {
    private String IDTruyen;
    private String MangaName;
    private Blob MangaImage;
    private String MangaDes;

    public String getIDTruyen() {
        return IDTruyen;
    }

    public void setIDTruyen(String IDTruyen) {
        this.IDTruyen = IDTruyen;
    }

    public String getMangaName() {
        return MangaName;
    }

    public void setMangaName(String mangaName) {
        MangaName = mangaName;
    }

    public Blob getMangaImage() {
        return MangaImage;
    }

    public void setMangaImage(Blob mangaImage) {
        MangaImage = mangaImage;
    }

    public String getMangaDes() {
        return MangaDes;
    }

    public void setMangaDes(String mangaDes) {
        MangaDes = mangaDes;
    }


    public Manga(String IDTruyen, String mangaName, Blob mangaImage, String mangaDes) {
        this.IDTruyen = IDTruyen;
        MangaName = mangaName;
        MangaImage = mangaImage;
        MangaDes = mangaDes;
    }

    public Manga() {
    }
}
