package com.example.svenscan.svenscan.repositories;

import android.net.Uri;

public interface IMediaRepository {
    void addSound(String absSoundPath);
    void addImage(String absImagePath);
    Uri getSoundUri(String soundPath);
    Uri getImageUri(String imagePath);
}
