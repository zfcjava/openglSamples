package com.example.zfc.openglsamples;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.zfc.openglsamples.util.ShaderHelper;
import com.example.zfc.openglsamples.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zfc on 2017/11/11.
 */

class AirHockeyRender implements GLSurfaceView.Renderer {
    private final Context context;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int a_PositionLocation;
    //由于在gl中基本单元只有 点 , 线, 三角形，所以不能直接定义矩形顶点
//    float[] tableVertices = {
//            0f, 0f,
//            9f, 14f,
//            0f, 14,
//            9f, 0f
//    };
//    float[] tableVerticesWithTriangles = {
//            //triangle1
//            -0.5f, -0.5f,
//            0.5f, 0.5f,
//            -0.5f, 0.5f,
////            triagnle2
//            -0.5f, -0.5f,
//            0.5f, -0.5f,
//            0.5f, 0.5f,
//            // line
//            -0.5f, 0f,
//            0.5f, 0f,
//            //point 1
//            0f, -0.25f,
//            //point 2
//            0f, 0.25f,
//
//    };


    float[] tableVerticesWithTriangles = {
            //triangle1
            -0f, -0f,
            1f, 1f,
            -1f, 1f,
//            triagnle2
//            -0.5f, -0.5f,
//            0.5f, -0.5f,
//            0.5f, 0.5f,
//            // line
//            -0.5f, 0f,
//            1f, 0f,
//            //point 1
//            0f, -0.25f,
//            //point 2
//            0f, 0.25f,

    };


    private final FloatBuffer vertexData;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPNENT_COUNT = 4;
    public AirHockeyRender(Context context) {
        this.context = context;
        //将java层的数组传递给native层
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

    }

    @Override
    /**
     * 并不是只会调用一次，有可能在其他activity切换回来的时候也会调用
     */
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //1.设置清空屏幕用的颜色
        GLES20.glClearColor(1.0f, 1.0f, 0.0f, 0.0f);
        //读取着色器
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        //编译着色器代码，获取着色器对象
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        int programId = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        //验证programId的有效性
        boolean invalide = ShaderHelper.valideProgram(programId);
        //告诉程序，在绘制任何内容时，使用programId
        GLES20.glUseProgram(programId);
        //获取第一个Uniform
        uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR);
        //获取属性的位置
        a_PositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);
        //关联属性和顶点数组
        vertexData.position(0);
        GLES20.glVertexAttribPointer(a_PositionLocation, POSITION_COMPNENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData);
        //使能顶点
        GLES20.glEnableVertexAttribArray(a_PositionLocation);

    }

    @Override
    /**
     * 大小发生变化的时候进行callback
     */
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //2.告诉OpenGl需要渲染的surface是多大
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //一定要绘制内容，即使是空屏幕，否则在进行缓冲区交换时，闪烁
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        //绘制桌子
        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 2);
//        //绘制直线
//        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f);
//        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
//        //绘制木槌
//        GLES20.glUniform4f(uColorLocation, 0f, 0f, 1f, 1f);
//        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
//        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f);
//        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}
