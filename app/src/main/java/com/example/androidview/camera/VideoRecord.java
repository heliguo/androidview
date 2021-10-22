package com.example.androidview.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 * @author lgh on 2021/10/21 19:38
 * @description
 */
public interface VideoRecord {

    Camera getCamera();

    SurfaceHolder getSurfaceHolder();


}
