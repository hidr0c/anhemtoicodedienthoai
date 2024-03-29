package com.sinhvien.anhemtoicodedienthoai.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.anhemtoicodedienthoai.API.type.Manga.Manga;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.CoverArt;
import com.sinhvien.anhemtoicodedienthoai.API.type.Relationship.Relationship;
import com.sinhvien.anhemtoicodedienthoai.Detail.MangaInfoActivity;
import com.sinhvien.anhemtoicodedienthoai.R;
import com.squareup.picasso.Picasso;

public class MostRatedRecyclerViewAdapter extends RecyclerView.Adapter<MostRatedRecyclerViewAdapter.ViewHolder>{
    private Context context;
    private Manga[] manga;

    public MostRatedRecyclerViewAdapter(Context context, Manga[] manga) {
        this.context = context;
        this.manga = manga;
    }

    @NonNull
    @Override
    public MostRatedRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_item_row, parent, false);
        return new MostRatedRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostRatedRecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String coverLink = "";

        Relationship[] relationships = manga[position].relationships;
        for (int i = 0; i < relationships.length; i++) {
            if (relationships[i].type.equals("cover_art")) {
                coverLink = "https://uploads.mangadex.org/covers/" + manga[position].id + "/" + ((CoverArt)relationships[i].attribute).fileName;
            }
        }

        if (coverLink != "") {
            Picasso.get().load(coverLink).into(holder.ivMangaItem);
        } else {
            holder.ivMangaItem.setImageResource(R.drawable.solid_grey_svg);
        }
        if (manga[position].attributes.title.en != null)
            holder.tvMangaItemTitle.setText(manga[position].attributes.title.en);
        else if (manga[position].attributes.title.ja != null) {
            holder.tvMangaItemTitle.setText(manga[position].attributes.title.ja);
        } else {
            holder.tvMangaItemTitle.setText(manga[position].attributes.title.ja_ro);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), MangaInfoActivity.class);
                intent.putExtra("mangaId", manga[position].id);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return manga.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMangaItem;
        TextView tvMangaItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMangaItem = itemView.findViewById(R.id.ivManga);
            tvMangaItemTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
