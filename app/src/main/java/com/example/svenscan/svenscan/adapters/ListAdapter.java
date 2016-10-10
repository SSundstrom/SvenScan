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

import java.util.List;

public class ListAdapter extends BaseAdapter{

    List<String> list;
    FavoriteItemViewHolder holder;
    IWordRepository wordManager;
    IMediaRepository mediaRepository;

    public ListAdapter(List<String> al, SvenScanApplication application){
        list = al;
        System.out.println(al);
        wordManager = application.getWordRepository();
        mediaRepository = application.getMediaRepository();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);

            holder = new FavoriteItemViewHolder();
            holder.view = convertView.findViewById(R.id.listItem);
            holder.word = (TextView) convertView.findViewById(R.id.favoriteWord);
            holder.wordImage = (ImageView) convertView.findViewById(R.id.wordImage);

            convertView.setTag(holder);
        } else {
            holder = (FavoriteItemViewHolder) convertView.getTag();
        }

        Word word = wordManager.getWordFromID(getItem(position));
        if (word!=null) {
            holder.view.setTag(word.getWordID());
            holder.word.setText(word.getWord());
            mediaRepository.getImageUri(word.getImagePath(), holder.wordImage::setImageURI);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }
}
