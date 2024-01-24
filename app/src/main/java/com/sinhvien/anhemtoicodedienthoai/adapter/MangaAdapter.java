package com.sinhvien.anhemtoicodedienthoai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sinhvien.anhemtoicodedienthoai.R;
import com.sinhvien.anhemtoicodedienthoai.object.manga;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends ArrayAdapter<manga> {
    private Context ct;
    private ArrayList<manga> arr;
    public MangaAdapter(@NonNull Context context, int resource, @NonNull List<manga> objects) {
        super(context, resource, objects);
        this.ct=context;
        this.arr=new ArrayList<>(objects);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        return super.getView(position, convertView, parent);
    }
}
