package com.yjm.doctor.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.yjm.doctor.application.base.ActivityLifecycle;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zx on 2017/12/4.
 */

public class YjmApplication extends Application {

    private static YjmApplication instance;
    public static String title;

    public static boolean toolBackIcon = true;

    public static boolean toolFinish = false;

    public static boolean tooAdd =false;

    public static boolean update = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //设置 toolbar
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        Fresco.initialize(this);

        initEMClient();
    }

    public static YjmApplication getInstance(){
        return instance;
    }


    private void initEMClient(){

        EMOptions options = new EMOptions();
        options.setAutoLogin(false);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("EMClient", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回

            return;
        }


        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        EMClient.getInstance().login("2-18964025491", "7C97A82686944C8E8D15328F3DA27511", new EMCallBack() {
            @Override
            public void onSuccess() {

                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.i("EMClient","login is success");
            }

            @Override
            public void onError(int i, String s) {
                Log.i("EMClient","login error i="+i+",s="+s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.i("EMClient","login onProgress i="+i+",s="+s);
            }
        });
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}
