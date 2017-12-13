package com.yjm.doctor.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yjm.doctor.application.base.ActivityLifecycle;

/**
 * Created by zx on 2017/12/4.
 */

public class YjmApplication extends Application {


    public static String title;

    public static boolean toolBackIcon = true;

    public static boolean toolFinish = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置 toolbar
        registerActivityLifecycleCallbacks(new ActivityLifecycle(title));
        Fresco.initialize(this);
    }

}
