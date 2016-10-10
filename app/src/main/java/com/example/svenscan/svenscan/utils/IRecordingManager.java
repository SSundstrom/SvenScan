package com.example.svenscan.svenscan.utils;

import android.net.Uri;
import android.view.View;

public interface IRecordingManager {

    /**
     * Used to turn recording on or off
     */
    void toggleRecording(String fileName, IOnRecordingComplete handler);

    /**
     * Used to turn recording on or off
     * @param view can be added for visual feedback
     */
    void toggleRecording(String fileName, IOnRecordingComplete handler, View view);
    /**
     *
     * @return the path to a recorded file
     */
    Uri getUri();

    /**
     *
     * @return if there is a recorded file
     */
    boolean hasRecording();

    interface IOnRecordingComplete {
        void recordingDone(Uri soundFileUri, String fileName);
    }
}
