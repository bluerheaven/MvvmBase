package com.example.myapplication.application;

import android.app.Application;

import com.example.myapplication.loadsir.CustomCallback;
import com.example.myapplication.loadsir.EmptyCallback;
import com.example.myapplication.loadsir.ErrorCallback;
import com.example.myapplication.loadsir.LoadingCallback;
import com.example.myapplication.loadsir.TimeoutCallback;
import com.example.myapplication.network.NetWorkApi;
import com.kingja.loadsir.core.LoadSir;

public class MvvmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkApi.init(new NetworkRequestInfo(this));
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

    }
}
