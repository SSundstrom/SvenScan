package com.example.svenscan.svenscan.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IWordRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayListAdapter extends BaseAdapter{

    ArrayList<String> arrayList;
    FavoriteItemViewHolder holder;
    IWordRepository wordManager;

    public ArrayListAdapter(ArrayList<String> al, SvenScanApplication application){
        arrayList = al;
        wordManager = application.getWordRepository();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);

            holder = new FavoriteItemViewHolder();
            holder.word = (TextView) convertView.findViewById(R.id.favoriteWord);
            holder.wordImage = (ImageView) convertView.findViewById(R.id.wordImage);
            holder.moreInfo = (Button) convertView.findViewById(R.id.moreInfo);

            convertView.setTag(holder);
        } else {
            holder = (FavoriteItemViewHolder) convertView.getTag();
        }

        Word word = wordManager.getWordFromID(getItem(position));
        holder.word.setText(word.getWord());
        //holder.wordImage.

        return convertView;
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
