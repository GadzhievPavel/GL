package com.example.gl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class MyGLSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX[][]= new float[4][2];
    private float previousY[][]= new float[4][2];
    double scaleLast = 0;
    private final MyGLRenderer renderer;
    boolean inTouch = false;
    int downPI = 0;
    private int upPI = 0;
    private double globalScale = 1;
    private boolean isCameraRotate = false;
    private ScaleGestureDetector scaleGestureDetector;
    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer(context);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        scaleGestureDetector = new ScaleGestureDetector(context, new ClassListener());
    }

    public MyGLSurfaceView(Context context, AttributeSet attr){
        super(context,attr);
        setEGLContextClientVersion(2);
        renderer = new MyGLRenderer(context);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        scaleGestureDetector = new ScaleGestureDetector(context, new ClassListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        int pointerIndex = event.getActionIndex();
        int pointerCount = event.getPointerCount();
        float xStart=0, yStart=0, xEnd=0, yEnd=0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inTouch = true;
            case MotionEvent.ACTION_POINTER_DOWN:
                downPI = pointerIndex;
                break;
            case MotionEvent.ACTION_UP:
                inTouch = false;
            case MotionEvent.ACTION_POINTER_UP:
                upPI = pointerIndex;
                break;
            case MotionEvent.ACTION_MOVE:
                switch (pointerCount){
                    case 1:
                            float dx = event.getX(0) - previousX[0][0];
                            float dy = event.getY(0) - previousY[0][1];

                            // reverse direction of rotation above the mid-line
                            if (y > getHeight() / 2) {
                                dx = dx * -1 ;
                            }

                            // reverse direction of rotation to left of the mid-line
                            if (x < getWidth() / 2) {
                                dy = dy * -1 ;
                            }
                            Log.e("Position", "dx : "+dx+" dy "+dy);

                            if(!isCameraRotate){
                                renderer.setAngleZ(
                                        renderer.getAngleZ() +
                                                ((dx) * TOUCH_SCALE_FACTOR));
                                renderer.setAngleY(renderer.getAngleY() + ((dy) * TOUCH_SCALE_FACTOR));
                            }else{
                                renderer.setAngleCameraZ(renderer.getAngleCameraZ()+ ((dx) * TOUCH_SCALE_FACTOR));
                                renderer.setAngleCameraY(renderer.getAngleCameraY()+ ((dy) * TOUCH_SCALE_FACTOR));
                                Log.e("Z", String.valueOf(renderer.getAngleCameraZ()));
                                Log.e("Y", String.valueOf(renderer.getAngleCameraY()));
                            }


                            break;
                    case 2:

                        break;
                    }
                }

                requestRender();


        for (int i = 0; i<pointerCount;i++){
            previousX[i][0] = event.getX(i);
            previousY[i][1] = event.getY(i);
        }

        return true;
    }

    public boolean isCameraRotate() {
        return isCameraRotate;
    }

    public void setCameraRotate(boolean cameraRotate) {
        isCameraRotate = cameraRotate;
        renderer.setCameraRotate(isCameraRotate);
    }

    private class ClassListener extends SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            Log.e("Scale", String.valueOf(setScale(scale)));
            renderer.setScale((float) globalScale);
            return true;
        }

        private double setScale(double scale){
            globalScale = (globalScale * scale);

            return globalScale;
        }
    }
}
