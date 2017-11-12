package com.example.zfc.openglsamples.util;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by zfc on 2017/11/13.
 *
 * 编译着色器工具类
 */

public class ShaderHelper {

    public static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode){
        return compileVertexShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileVertexShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileVertexShader(int type, String shaderCode) {
        //1.创建一个着色器对象
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            //2.如果创建失败，重置
            if (LoggerConfig.ON) {
                Log.e(TAG, "could not create shader");
            }
            return 0;
        }

        //2.上传和编译代码
        GLES20.glShaderSource(shaderObjectId,shaderCode);
        //3.编译代码
        GLES20.glCompileShader(shaderObjectId);
        //4.取出编译状态
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        //5.取出着色器日志
        if (LoggerConfig.ON) {
            Log.e(TAG, "result of compile the shader soruce :" + GLES20.glGetShaderInfoLog(shaderObjectId));
        }
        //6.验证着色器状态，并返回着色器id
        if (compileStatus[0] == 0) {
            //if failed , delete it
            GLES20.glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.e(TAG, "compilation of shader failed");
            }
            return 0;
        }
        return shaderObjectId;
    }


    public static int linkProgram(int vertexShader, int fragmentShader) {
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.e(TAG, "creatation of program failed");
            }
            return 0;
        }

        //为应用程序附加着色器
        GLES20.glAttachShader(programObjectId,vertexShader);
        GLES20.glAttachShader(programObjectId,fragmentShader);
        //链接程序
        GLES20.glLinkProgram(programObjectId);
        //检查链接状态
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            return 0;
        }
        return programObjectId;
    }

}
