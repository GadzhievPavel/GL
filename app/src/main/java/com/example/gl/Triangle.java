package com.example.gl;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_FRONT_AND_BACK;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glLinkProgram;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private FloatBuffer vertexBuffer;
    static final int COORDS_PER_VERTEX = 3;
    private int positionHandle;
    private int colorHandle;
    private final int mProgram;
    private int vPMatrixHandle;
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private Context context;
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.0f, 0.0f, // top
            0.5f, 0.5f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    float color[]={ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    public Triangle(Context context) {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        mProgram = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        glLinkProgram(mProgram);

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }


    public void draw(float[] matrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "v_Position");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "u_Color");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHandle,1,false,matrix,0);
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        glLineWidth(20);//size points
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
