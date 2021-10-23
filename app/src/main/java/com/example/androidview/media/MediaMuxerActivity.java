package com.example.androidview.media;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidview.BaseActivity;
import com.example.androidview.databinding.ActivityMediaMuxerBinding;
import com.example.androidview.utils.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author lgh on 2021/10/22 15:50
 * @description 音视频分离MediaExtractor 音视频合成MediaMuxer
 */
public class MediaMuxerActivity extends BaseActivity {

    ActivityMediaMuxerBinding mBinding;
    private volatile boolean hasCopy;
    private String mPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMediaMuxerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mPath = getExternalFilesDir("muxer").getAbsolutePath();

        mBinding.copy.setOnClickListener(v -> new Thread(() -> {

            hasCopy = FileCopyUtils.copyDBToSD(getApplicationContext(), mPath, "test.mp4");
        }).start());

        mBinding.separate.setOnClickListener(v -> {
            if (new File(mPath + "/test.mp4").exists()) {
                hasCopy = true;
            }
            if (hasCopy) {
                new Thread(() -> doSeparate()).start();
            }

        });

        mBinding.play.setOnClickListener(v -> {
            play();
        });

    }

    private void play() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mPath + "/demo2.aac");
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doSeparate() {
        File file = new File(mPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String srcPath = mPath + "/test.mp4";
        String fPath1 = mPath + "/demo1.mp4";
        String fPath2 = mPath + "/demo2.aac";

        File file1 = new File(fPath1);
        File file2 = new File(fPath2);
        try {
            if (file1.exists()) {
                file1.delete();

            }
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaMuxer mMediaMuxer;
        MediaExtractor mediaExtractor = null;
        int mVideoTrackIndex = 0;
        int mAudioTrackIndex = 0;
        long frameRate;

        try {
            mediaExtractor = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor.setDataSource(srcPath);//媒体文件的位置
            Log.e("mediaExtractor", "==========getTrackCount()===================" + mediaExtractor.getTrackCount());
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                MediaFormat format = mediaExtractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (!TextUtils.isEmpty(mime) && mime.startsWith("audio")) {//获取音频轨道
                    ByteBuffer buffer = ByteBuffer.allocate(2 * 1024 * 1024);
                    mediaExtractor.unselectTrack(i);
                    mediaExtractor.selectTrack(i);//选择此音频轨道
                    mediaExtractor.readSampleData(buffer, 0);
                    long first_sampletime = mediaExtractor.getSampleTime();
                    mediaExtractor.advance();
                    long second_sampletime = mediaExtractor.getSampleTime();
                    frameRate = Math.abs(second_sampletime - first_sampletime);//时间戳
                    mediaExtractor.unselectTrack(i);
                    mediaExtractor.selectTrack(i);
                    Log.e("mediaExtractor", "==============frameRate111==============" + frameRate + "");
                    mMediaMuxer = new MediaMuxer(fPath2, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    mAudioTrackIndex = mMediaMuxer.addTrack(format);
                    mMediaMuxer.start();

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    info.presentationTimeUs = 0;

                    int sampleSize = 0;
                    while ((sampleSize = mediaExtractor.readSampleData(buffer, 0)) > 0) {
                        info.offset = 0;
                        info.size = sampleSize;
                        info.flags = mediaExtractor.getSampleFlags();
                        info.presentationTimeUs += frameRate;
                        mMediaMuxer.writeSampleData(mAudioTrackIndex, buffer, info);
                        mediaExtractor.advance();
                    }

                    mMediaMuxer.stop();
                    mMediaMuxer.release();

                }
                if (!TextUtils.isEmpty(mime) && mime.startsWith("video")) {
                    mediaExtractor.unselectTrack(i);
                    mediaExtractor.selectTrack(i);//选择此视频轨道
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
                    Log.e("mediaExtractor1", "==============frameRate222==============" + 1000 * 1000 / frameRate + "");
                    mMediaMuxer = new MediaMuxer(fPath1, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    mVideoTrackIndex = mMediaMuxer.addTrack(format);
                    mMediaMuxer.start();

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    info.presentationTimeUs = 0;
                    ByteBuffer buffer = ByteBuffer.allocate(2 * 1024 * 1024);
                    int sampleSize = 0;

                    while ((sampleSize = mediaExtractor.readSampleData(buffer, 0)) > 0) {
                        info.offset = 0;
                        info.size = sampleSize;
                        info.flags = mediaExtractor.getSampleFlags();
                        info.presentationTimeUs += 1000 * 1000 / frameRate;
                        mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info);
                        mediaExtractor.advance();
                    }

                    mMediaMuxer.stop();
                    mMediaMuxer.release();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mediaExtractor != null) {
                mediaExtractor.release();
                mediaExtractor = null;
            }

        }
    }

    private void muxermergedata() {
        File file = new File(mPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String desPath = mPath + "/test2.mp4";
        String fPath1 = mPath + "/demo1.mp4";
        String fPath2 = mPath + "/demo2.mp3";

        MediaMuxer mMediaMuxer = null;
        MediaExtractor mediaExtractor1 = null;
        MediaExtractor mediaExtractor2 = null;

        try {
            mMediaMuxer = new MediaMuxer(desPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mMediaMuxer == null) {
            return;
        }
        int mVideoTrackIndex = 0;
        int mAudioTrackIndex = 0;
        long frameRate1 = 0;
        long frameRate2 = 0;

        MediaFormat format1;
        MediaFormat format2;
        try {
            mediaExtractor1 = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor1.setDataSource(fPath1);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor1.getTrackCount());
            for (int i = 0; i < mediaExtractor1.getTrackCount(); i++) {
                format1 = mediaExtractor1.getTrackFormat(i);
                String mime = format1.getString(MediaFormat.KEY_MIME);

                if (!TextUtils.isEmpty(mime) && mime.startsWith("video")) {
                    mediaExtractor1.selectTrack(i);//选择此视频轨道
                    frameRate1 = format1.getInteger(MediaFormat.KEY_FRAME_RATE);
                    System.out.println("==============frameRate222==============" + 1000 * 1000 / frameRate1 + "");
                    mVideoTrackIndex = mMediaMuxer.addTrack(format1);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaExtractor2 = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor2.setDataSource(fPath2);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor2.getTrackCount());
            for (int i = 0; i < mediaExtractor2.getTrackCount(); i++) {
                format2 = mediaExtractor2.getTrackFormat(i);
                String mime = format2.getString(MediaFormat.KEY_MIME);
                if (!TextUtils.isEmpty(mime) && mime.startsWith("audio")) {//获取音频轨道
                    ByteBuffer buffer = ByteBuffer.allocate(2 * 1024 * 1024);
                    {
                        mediaExtractor2.selectTrack(i);//选择此音频轨道
                        mediaExtractor2.readSampleData(buffer, 0);
                        long first_sampletime = mediaExtractor2.getSampleTime();
                        mediaExtractor2.advance();
                        long second_sampletime = mediaExtractor2.getSampleTime();
                        frameRate2 = Math.abs(second_sampletime - first_sampletime);//时间戳
                        mediaExtractor2.unselectTrack(i);
                    }
                    mediaExtractor2.selectTrack(i);
                    System.out.println("==============frameRate111==============" + frameRate2 + "");
                    mAudioTrackIndex = mMediaMuxer.addTrack(format2);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        mMediaMuxer.start();
        MediaCodec.BufferInfo info1 = new MediaCodec.BufferInfo();
        info1.presentationTimeUs = 0;
        ByteBuffer buffer = ByteBuffer.allocate(2 * 1024 * 1024);
        int sampleSize1 = 0;
        while ((sampleSize1 = mediaExtractor1.readSampleData(buffer, 0)) > 0) {
            info1.offset = 0;
            info1.size = sampleSize1;
            info1.flags = mediaExtractor1.getSampleFlags();
            info1.presentationTimeUs += 1000 * 1000 / frameRate1;
            mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info1);
            mediaExtractor1.advance();
        }


        MediaCodec.BufferInfo info2 = new MediaCodec.BufferInfo();
        info2.presentationTimeUs = 0;

        int sampleSize2 = 0;
        while ((sampleSize2 = mediaExtractor2.readSampleData(buffer, 0)) > 0) {
            info2.offset = 0;
            info2.size = sampleSize2;
            info2.flags = mediaExtractor2.getSampleFlags();
            info2.presentationTimeUs += frameRate2;
            mMediaMuxer.writeSampleData(mAudioTrackIndex, buffer, info2);
            mediaExtractor2.advance();
        }

        try {
            if (mediaExtractor1 != null) {
                mediaExtractor1.release();
                mediaExtractor1 = null;
            }
            if (mediaExtractor2 != null) {
                mediaExtractor2.release();
                mediaExtractor2 = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private MediaExtractor mediaExtractor;

    private void initMediaDecode() {
        File file = new File(mPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String srcPath = mPath + "/test.mp4";
        String fPath1 = mPath + "/demo1.mp4";
        String fPath2 = mPath + "/demo2.aac";

        File file1 = new File(fPath1);
        File file2 = new File(fPath2);
        try {
            if (file1.exists()) {
                file1.delete();

            }
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaExtractor = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor.setDataSource(srcPath);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor.getTrackCount());
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {//遍历媒体轨道，包括视频和音频轨道
                MediaFormat format = mediaExtractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio")) {//获取音频轨道
                    mediaExtractor.selectTrack(i);//选择此音频轨道
                    System.out.println("====audio=====KEY_MIME=========" + format.getString(MediaFormat.KEY_MIME));
                    System.out.println("====audio=====KEY_CHANNEL_COUNT=======" + format.getInteger(MediaFormat.KEY_CHANNEL_COUNT) + "");
                    System.out.println("====audio=====KEY_SAMPLE_RATE===========" + format.getInteger(MediaFormat.KEY_SAMPLE_RATE) + "");
                    System.out.println("====audio=====KEY_DURATION===========" + format.getLong(MediaFormat.KEY_DURATION) + "");

                    System.out.println("====audio=====getSampleFlags===========" + mediaExtractor.getSampleFlags() + "");
                    System.out.println("====audio=====getSampleTime===========" + mediaExtractor.getSampleTime() + "");
                    //  System.out.println("====audio=====getSampleSize==========="+mediaExtractor.getSampleSize()+"");api28
                    System.out.println("====audio=====getSampleTrackIndex===========" + mediaExtractor.getSampleTrackIndex() + "");

                    try {
                        ByteBuffer inputBuffer = ByteBuffer.allocate(2 * 1024 * 1024);
                        FileOutputStream fe = new FileOutputStream(file2, true);

                        while (true) {
                            int readSampleCount = mediaExtractor.readSampleData(inputBuffer, 0);
                            if (readSampleCount < 0) {
                                break;
                            }
                            byte[] buffer = new byte[readSampleCount];
                            inputBuffer.get(buffer);
                            fe.write(buffer);
                            inputBuffer.clear();
                            mediaExtractor.advance();
                        }

                        fe.flush();
                        fe.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                    }
                }

                if (mime.startsWith("video")) {
                    mediaExtractor.selectTrack(i);//选择此视频轨道
                    System.out.println("====video=====KEY_MIME===========" + format.getString(MediaFormat.KEY_MIME));
                    System.out.println("====video=====KEY_DURATION===========" + format.getLong(MediaFormat.KEY_DURATION) + "");
                    System.out.println("====video=====KEY_WIDTH===========" + format.getInteger(MediaFormat.KEY_WIDTH) + "");
                    System.out.println("====video=====KEY_HEIGHT===========" + format.getInteger(MediaFormat.KEY_HEIGHT) + "");
                    System.out.println("====video=====getSampleFlags===========" + mediaExtractor.getSampleFlags() + "");
                    System.out.println("====video=====getSampleTime===========" + mediaExtractor.getSampleTime() + "");
                    // System.out.println("====video=====getSampleSize==========="+mediaExtractor.getSampleSize()+"");api28
                    System.out.println("====video=====getSampleTrackIndex===========" + mediaExtractor.getSampleTrackIndex() + "");

                    try {
                        ByteBuffer inputBuffer = ByteBuffer.allocate(2 * 1024 * 1024);
                        FileOutputStream fe = new FileOutputStream(file1, true);

                        while (true) {
                            int readSampleCount = mediaExtractor.readSampleData(inputBuffer, 0);
                            if (readSampleCount < 0) {
                                break;
                            }
                            byte[] buffer = new byte[readSampleCount];
                            inputBuffer.get(buffer);
                            fe.write(buffer);
                            inputBuffer.clear();
                            mediaExtractor.advance();

                        }

                        fe.flush();
                        fe.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mediaExtractor.release();
            mediaExtractor = null;
        }
    }


}
