package com.lawwing.lwdocument;

import com.lawwing.lwdocument.gen.DaoMaster;
import com.lawwing.lwdocument.gen.DaoSession;
import com.tencent.bugly.crashreport.CrashReport;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lawwing on 2017/11/10.
 */

public class LWDApp extends Application
{
    private DaoMaster.DevOpenHelper mHelper;
    
    private SQLiteDatabase db;
    
    private DaoMaster mDaoMaster;
    
    private DaoSession mDaoSession;
    
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
                .initCrashReport(getApplicationContext(), "7f85e93995", false);
        
        setDatabase();
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
    
    public static EventBus getEventBus()
    {
        return EventBus.getDefault();
    }
    
    /**
     * 设置greenDao
     */
    private void setDatabase()
    {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "lwd-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    
    public DaoSession getDaoSession()
    {
        return mDaoSession;
    }
    
    public SQLiteDatabase getDb()
    {
        return db;
    }
}
