package com.example.gl;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Context context;
    private int programId;
    private FloatBuffer vertexData;
    private int uColorLocation;
    private int aPositionLocation;
    private Triangle tr;
    private Point point;
    private float angleZ = 0;
    private float angleY = 0;
    private boolean isCameraRotate = false;
    private float angleCameraZ = 1;
    private float angleCameraY = 1;
    private float scale = 1;
    private final static long TIME = 10000;
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];
    public  MyGLRenderer(Context context){
        this.context = context;

    }

    public void setScale(float scale){
        this.scale = scale;
    }
    public void setAngleZ(float angle){
        this.angleZ = angle;
    }

    public float getAngleZ(){
        return angleZ;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        glClearColor(1.0f, 1.0f, 0.5f, 0.1f);
        point = new Point(context,new float[]{0.0f,0.0f,0.0f,0.5f,0.5f,0.45f,-0.5f,-0.5f,0.5f,
                0.5f,-0.5f,1.0f,0.5f,0.5f,0.45f,-0.5f,-0.5f,0.5f,2.0f,1.0f,0.3f});

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio = 1.0f;
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f;
        float top = 1.0f;
        float near = 1.0f;
        float far = 9.0f;
        if (width > height) {
            ratio = (float) width / height;
            left *= ratio;
            right *= ratio;
        } else {
            ratio = (float) height / width;
            bottom *= ratio;
            top *= ratio;
        }

        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
        glClearColor(0f, 0f, 0f, 1f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        createViewMatrix();
        Matrix.multiplyMM(vPMatrix,0,projectionMatrix,0,viewMatrix,0);
        Matrix.setRotateM(rotationMatrix, 0, angleZ, 0, 0, -1.0f);
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
        Matrix.setRotateM(rotationMatrix,0, angleY, 0, -1.0f,0);
        Matrix.multiplyMM(scratch, 0, scratch, 0, rotationMatrix, 0);
        Matrix.scaleM(scratch,0,scratch,0,scale,scale,scale);
        //tr.draw(vPMatrix);
        point.draw(scratch);
    }

    private void createViewMatrix() {

        if(isCameraRotate){
            // точка положения камеры
            float eyeX = 2f;
            float eyeY = 2f;
            float eyeZ = 2f;

            // точка направления камеры
            float centerX = 0;
            float centerY = 0;
            float centerZ = 0;

            // up-вектор
            float upX = 0;
            float upY = 0;
            float upZ = 0.1f;
            Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        }else{
            // точка положения камеры
            float eyeX = 2f;
            float eyeY = 2f;
            float eyeZ = 2f;

            // точка направления камеры
            float centerX = 0;
            float centerY = 0;
            float centerZ = 0;

            // up-вектор
            float upX = 0;
            float upY = 0;
            float upZ = 0.1f;
            Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        }



    }


    public float getAngleCameraZ() {
        return angleCameraZ;
    }

    public void setAngleCameraZ(float angleCameraZ) {
        this.angleCameraZ = angleCameraZ;
    }

    public float getAngleCameraY() {
        return angleCameraY;
    }

    public void setAngleCameraY(float angleCameraY) {
        this.angleCameraY = angleCameraY;
    }
    public void setCameraRotate(boolean b){
        isCameraRotate = b;
    }
    public boolean isCameraRotate(){
        return isCameraRotate;
    }
}
