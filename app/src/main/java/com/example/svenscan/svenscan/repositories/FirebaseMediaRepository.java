package com.example.svenscan.svenscan.repositories;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FirebaseMediaRepository implements IMediaRepository{

    private StorageReference storageRef;

    private File soundDir;
    private File imageDir;

    public FirebaseMediaRepository() {
        storageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public void addSound(Uri fileName, IUploadHandler handler) {
        uploadMedia("sounds/" + fileName.getLastPathSegment(), fileName, handler);
    }

    @Override
    public void addImage(Uri fileName, IUploadHandler handler) {
        uploadMedia("images/" + fileName.getLastPathSegment(), fileName, handler);
    }

    public File getSoundDir() {
        return soundDir;
    }

    @Override
    public File getImageDir() {
        return imageDir;
    }

    @Override
    public void getSoundUri(String soundName, IMediaHandler handler) {
        getMediaUri("sounds", soundName, soundDir, handler);
    }

    @Override
    public void getImageUri(String imageName, IMediaHandler handler) {
        getMediaUri("images", imageName, imageDir, handler);
    }

    public void initialize(File dataDir) {
        soundDir = new File(dataDir.getPath() + "/sounds");
        if (!soundDir.exists()) {
            boolean mkdir = soundDir.mkdir();
            if (!mkdir){
                // TODO: 2016-10-12 inform user directory failed to initialize
                System.out.println("System failed to create sound directory '/sounds'");
            }
        }

        imageDir = new File(dataDir.getPath() + "/images");
        if (!imageDir.exists()){
            boolean mkdir = imageDir.mkdir();
            if (!mkdir){
                // TODO: 2016-10-12 inform user directory failed to initialize
                System.out.println("System failed to create image directory '/images'");
            }
        }

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

    private void downloadMedia(String firebasePath, File localFile, IMediaHandler handler) {
        StorageReference mediaRef = storageRef.child(firebasePath);

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

    private void uploadMedia(String firebasePath, Uri localFileUri, IUploadHandler handler) {

        StorageReference mediaRef = storageRef.child(firebasePath);

        mediaRef.putFile(localFileUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                handler.onUploadResult(false);
                // Handle unsuccessful uploads
                // TODO: 2016-10-04 Show some nice "Upload fail" message
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                handler.onUploadResult(true);
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                // TODO: 2016-10-04 Show some nice "Upload complete" message
            }
        });
    }
}
