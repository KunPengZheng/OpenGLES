/*
 *
 * PermissionUtils.java
 *
 * Created by Wuwang on 2016/11/14
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.example.opengles.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;

/**
 * Description:
 */
public class PermissionUtils {


    public static void askPermission(Activity context, String[] permissions, int req, Runnable runnable) {
        // 判断sdk版本是否大于23（6.0）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查自身的权限
            int result = ActivityCompat.checkSelfPermission(context, permissions[0]);
            if (result == PackageManager.PERMISSION_GRANTED) {
                // 该权限已授予
                runnable.run();
            } else {
                // 申请权限
                ActivityCompat.requestPermissions(context, permissions, req);
            }
        } else {
            // 23以下不需要申请权限
            runnable.run();
        }
    }

    public static void onRequestPermissionsResult(boolean isReq, int[] grantResults, Runnable okRun, Runnable deniRun) {
        if (isReq) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                okRun.run();
            } else {
                deniRun.run();
            }
        }
    }

}
