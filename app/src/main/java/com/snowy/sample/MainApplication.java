package com.snowy.sample;

import android.app.Application;

import com.snowy.sample.opensource.okhttp.OkHttpUtils;

import org.ayo.app.AyoViewLib;

/**
 * Created by zx on 17-3-30.
 */

public class MainApplication extends Application {
    public static Application app;
    private static MainApplication mInstance;

    public static MainApplication instance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mInstance = this;

        //初始化Ayo view
        AyoViewLib.init(this);

        //初始化全局异常处理
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);

        //初始化okhttpconfig
        OkHttpUtils.initOkHttpConfig(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        exit();
    }

    public void exit() {
        System.exit(0);
    }
}
