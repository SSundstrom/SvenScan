package com.example.svenscan.svenscan.repositories;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class FirebaseMediaRepository implements IMediaRepository{

    private StorageReference storageRef;

    private File soundDir;
    private File imageDir;

    public FirebaseMediaRepository() {
        storageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public void addSound(String absSoundPath) {

    }

    @Override
    public void addImage(String absImagePath) {

    }

    @Override
    public void getSoundUri(String soundName, IMediaHandler handler) {
        getMediaUri("sounds", soundName, soundDir, handler);
    }

    @Override
    public void getImageUri(String imageName, IMediaHandler handler) {
        getMediaUri("images", imageName, imageDir, handler);
    }

    private void getMediaUri(String mediaType, String mediaName, File mediaDir, IMediaHandler handler) {
        mediaName = "/" + mediaName;
        File mediaFile = new File(mediaDir + mediaName); // Local storage
        if (mediaFile.exists()){
            handler.onMediaAvailable(Uri.fromFile(mediaFile));
        } else {
            downloadMedia(mediaType + mediaName, mediaFile, handler);
        }
    }

    public void initialize(File dataDir) {
        soundDir = new File(dataDir.getPath() + "/sounds");
        if (!soundDir.exists()) soundDir.mkdir();

        imageDir = new File(dataDir.getPath() + "/images");
        if (!imageDir.exists()) imageDir.mkdir();

    }

    private void downloadMedia(String path, File localFile, IMediaHandler handler) {
        StorageReference mediaRef = storageRef.child(path);

        mediaRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                handler.onMediaAvailable(Uri.fromFile(localFile));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                System.out.println(exception.getMessage());
                // Handle any errors
            }
        });
    }
}
