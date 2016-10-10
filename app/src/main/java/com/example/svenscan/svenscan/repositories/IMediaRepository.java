package com.example.svenscan.svenscan.repositories;

import android.net.Uri;

import java.io.File;

public interface IMediaRepository {
    /**
     * Adds sound to media repo
     * @param filePath absPath to sound file
     */
    void addSound(Uri filePath, IUploadHandler handler);

    /**
     * Adds image to media repo
     * @param filePath abs path to image file
     */
    void addImage(Uri filePath, IUploadHandler handler);

    /**
     * Returns the Uri for a sound to the handler when the sound is available
     * @param soundName file name for sound file
     * @param handler Most likely a lambda-expression for what to do with the sounds uri
     */
    void getSoundUri(String soundName, IMediaHandler handler);

    /**
     * Returns the Uri for an image to the handler when the image is available
     * @param imageName file name for image file
     * @param handler Most likely a lambda-expression for what to do with the sounds uri
     */
    void getImageUri(String imageName, IMediaHandler handler);

    /**
     * Used to initialize the local directories for media
     * @param dataPath the path to the parent directory of the media directories
     */
    void initialize(File dataPath);

    File getSoundDir();

    /**
     * Used to access the Uri when available.
     */
    interface IMediaHandler {
        void onMediaAvailable(Uri path);
    }

    interface IUploadHandler {
        void onUploadResult(boolean isUploaded);
    }
}
