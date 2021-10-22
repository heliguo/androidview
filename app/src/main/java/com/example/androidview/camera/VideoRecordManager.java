package com.example.androidview.camera;

import android.media.MediaRecorder;

/**
 * @author lgh on 2021/10/21 19:58
 * @description
 */
public class VideoRecordManager {

    private final VideoRecord mVideoRecord;

    public VideoRecordManager(VideoRecord videoRecord) {
        mVideoRecord = videoRecord;
    }

    MediaRecorder mMediaRecorder;

    private void start() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        mVideoRecord.getCamera().unlock();
        mMediaRecorder.setCamera(mVideoRecord.getCamera());
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//视频源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);//音频源
    }

}
