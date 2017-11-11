package com.example.zfc.openglsamples;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by zfc on 2017/11/11.
 */

class FirstOpelglRender implements GLSurfaceView.Renderer {

    @Override
    /**
     * 并不是只会调用一次，有可能在其他activity切换回来的时候也会调用
     */
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //1.设置清空屏幕用的颜色
        GLES20.glClearColor(1.0f, 1.0f, 0.0f, 0.0f);
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
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
