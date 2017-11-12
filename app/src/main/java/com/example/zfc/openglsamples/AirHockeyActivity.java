package com.example.zfc.openglsamples;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AirHockeyActivity extends AppCompatActivity {

    private final String TAG = "AirHockeyActivity";
    private GLSurfaceView glSurfaceView;
    private boolean renderSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //1.创建openGlView对象
        glSurfaceView = new GLSurfaceView(this);
        setContentView(glSurfaceView);
        //配置surfaceview
        configSurfaceView();
    }


    private void configSurfaceView() {
        //2.检查系统是否支持opengl2
        if (checkSupportGl2InEmulator()) {
            //3.设置合适的上下文环境
            glSurfaceView.setEGLContextClientVersion(2);
            //4.指定渲染器
            glSurfaceView.setRenderer(new AirHockeyRender());


        } else {
            Toast.makeText(this, "不支持 openGL 2.0", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSupportGl2() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = manager.getDeviceConfigurationInfo();
        Log.e(TAG, "support version 2 : " + (configurationInfo.reqGlEsVersion >= 0x20000));
        return configurationInfo.reqGlEsVersion >= 0x20000;
    }


    /**
     * 如果是在模拟器上，也要增加几个判断条件
     * @return
     */
    private boolean checkSupportGl2InEmulator() {
        return checkSupportGl2()||(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1&&
                (Build.FINGERPRINT.startsWith("generic")||
                Build.FINGERPRINT.startsWith("unknown")||
                Build.MODEL.contains("google_sdk")||
                Build.MODEL.contains("Emulator")||
                Build.MODEL.contains("Android SDK built for x86")));
    }

    //5. 关联生命周期
    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != glSurfaceView) {
            glSurfaceView.onPause();
        }
    }
}
