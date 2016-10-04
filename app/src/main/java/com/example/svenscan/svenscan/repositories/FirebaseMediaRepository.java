package com.example.svenscan.svenscan.repositories;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class FirebaseMediaRepository implements IMediaRepository{

    private StorageReference storageRef;

    private File soundDir;
    private File imageDir;
    private Context context;

    public FirebaseMediaRepository(File dataDir) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    }

    @Override
    public void addSound(String absSoundPath) {

    }

    @Override
    public void addImage(String absImagePath) {

    }

    @Override
    public Uri getSoundUri(String soundPath) {
        return null;
    }

    @Override
    public Uri getImageUri(String imagePath) {
        return null;
    }

    private void initDirs() {

    }
}
