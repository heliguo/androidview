package com.example.androidview.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author lgh on 2020/6/17 20:27
 * @description 3D翻转效果
 */
public class Rotate3dAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;
    private RotateMode rotateMode;

    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space,definied by a pair
     * of X and Y coordinates,called centerX and centerY. When the animation
     * starts,a translation on the Z axis (depth) is performed. The length
     * of the translation can be specified,as well as whether the translation
     * should be reversed in time.
     *
     * @param fromDegrees the start angle of the 3D rotation
     * @param toDegrees   the end angle of the 3D rotation
     * @param centerX     the X center of the 3D rotation
     * @param centerY     the Y center of the 3D rotation
     * @param reverse     true if the translation should be reversed,false
     *                    otherwise
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees,
                             float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int
            parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();
        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        if (rotateMode == RotateMode.X) {
            camera.rotateX(degrees);
        } else if (rotateMode == RotateMode.Y) {
            camera.rotateY(degrees);
        } else if (rotateMode == RotateMode.Z){
            camera.rotateZ(degrees);
        }
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }

    enum RotateMode {
        X,
        Y,
        Z
    }

    public void setRotateMode(RotateMode rotateMode) {
        this.rotateMode = rotateMode;
    }
}