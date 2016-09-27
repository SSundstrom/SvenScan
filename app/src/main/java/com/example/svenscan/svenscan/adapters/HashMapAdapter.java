package com.example.svenscan.svenscan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.svenscan.svenscan.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapAdapter extends BaseAdapter{

    HashMap<String,Object> hashMap;
    ArrayList<String> arrayList;

    public HashMapAdapter(HashMap<String,Object> hashMap){
        this.hashMap=hashMap;
        arrayList = new ArrayList<>();
        arrayList.addAll(hashMap.keySet());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);
        } else {
            result = convertView;
        }

        // TODO replace findViewById by FavoriteItemViewHolder
        //FavoriteItemViewHolder holder = new FavoriteItemViewHolder();
        //holder.textView = convertView.findViewById(R.id.favorites);
        //convertView.setTag(holder);
        ((TextView) result.findViewById(R.id.favoriteWord)).setText(getItem(position));



        return result;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public String getItem(int position) {
        return arrayList.get(position);
    }
}
