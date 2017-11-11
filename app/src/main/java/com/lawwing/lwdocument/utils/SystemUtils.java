package com.lawwing.lwdocument.utils;

import com.lawwing.lwdocument.LWDApp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lawwing on 2017/11/11.
 */

public class SystemUtils {
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = LWDApp.get().getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    LWDApp.get().getApplicationContext().getPackageName(),
                    0);
            String version = info.versionName;
            return "版本：" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "找不到版本号";
        }
    }


}
