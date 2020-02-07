package com.custom.hermes.app;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Build;
import android.os.Process;

import com.custom.hermes.util.MyLog;

import java.util.List;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class MyApplication extends Application {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            MyLog.d(TAG, "MyApplication.onCreate：当前进程的名称 = " + getProcessName());
        } else {
            int i = Process.myPid();
            ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            if (manager!=null){
                List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo process : processes) {
                    if (process.pid == i) {
                        MyLog.d(TAG, "MyApplication.onCreate：当前进程的名称 = " + process.processName);
                    }
                }
            }
        }
    }
}
