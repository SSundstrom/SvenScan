package com.example.svenscan.svenscan.favorite;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;


import com.example.svenscan.svenscan.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HashMapAdapter extends BaseAdapter{

    HashMap<String,Word> hashMap;
    ArrayList<String> arrayList;

    public HashMapAdapter(FavoriteWords fw){
        this.hashMap=(HashMap<String, Word>)fw.getFavorites();
        arrayList = new ArrayList<>();
        arrayList.addAll(hashMap.keySet());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
