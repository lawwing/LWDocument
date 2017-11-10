package com.lawwing.lwdocument;

import com.tencent.bugly.crashreport.CrashReport;

import android.app.Application;

/**
 * Created by lawwing on 2017/11/10.
 */

public class LWDApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        _instance = (LWDApp) getApplicationContext();
        // QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
        //
        // @Override
        // public void onViewInitFinished(boolean arg0) {
        // // TODO Auto-generated method stub
        // //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        // Log.d("app", " onViewInitFinished is " + arg0);
        // }
        //
        // @Override
        // public void onCoreInitFinished() {
        // // TODO Auto-generated method stub
        // }
        // };
        // //x5内核初始化接口
        // QbSdk.initX5Environment(getApplicationContext(), cb);
        
        CrashReport
                .initCrashReport(getApplicationContext(), "9d5501966c", false);
    }
    
    private static LWDApp _instance;
    
    public static LWDApp get()
    {
        if (_instance == null)
        {
            synchronized (LWDApp.class)
            {
                if (_instance == null)
                {
                    _instance = new LWDApp();
                }
            }
        }
        return _instance;
        
    }
    
}
