package com.example.svenscan.svenscan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;

import java.util.ArrayList;

public class ArrayListAdapter extends BaseAdapter{

    ArrayList<String> arrayList;
    FavoriteItemViewHolder holder;
    IWordRepository wordManager;
    IMediaRepository mediaRepository;

    public ArrayListAdapter(ArrayList<String> al, SvenScanApplication application){
        arrayList = al;
        System.out.println(al);
        wordManager = application.getWordRepository();
        mediaRepository = application.getMediaRepository();
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

        System.out.println(position);
        System.out.println(getItem(position));
        Word word = wordManager.getWordFromID(getItem(position));
        holder.word.setText(word.getWord());
        mediaRepository.getImageUri(word.getImagePath(), holder.wordImage::setImageURI);
        holder.moreInfo.setTag(word.getWordID());

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
