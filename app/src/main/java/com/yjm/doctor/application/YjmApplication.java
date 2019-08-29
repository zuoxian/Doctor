package com.yjm.doctor.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.yjm.doctor.application.base.ActivityLifecycle;
import com.yjm.doctor.model.User;
import com.yjm.doctor.util.auth.UserService;

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

    public static boolean tooAdd =false;//保存

    public static boolean add = false; //添加

    public static boolean update = false;

    public static EaseUI easeUI;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //设置 toolbar
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        Fresco.initialize(this);

        initEMClient();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static YjmApplication getInstance(){
        return instance;
    }



    private void initEMClient(){

        EMOptions options = new EMOptions();
        options.setAutoLogin(true);
        options.setRequireAck(true);


//注意        8.使用 FCM 时需要在退出登录时解绑设备 token，调用EMClient.getInstance().logout(true)
        //json文件需要更换
// 或者EMClient.getInstance().logout(true,callback)方法，如果是被踢的情况下，则要求设置为 false。
        /**
         * NOTE:你需要设置自己申请的Sender ID来使用Google推送功能，详见集成文档
         */
        options.setFCMNumber("921300338324");


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


        if (EaseUI.getInstance().init(this, options)) {
            easeUI = EaseUI.getInstance();
        }
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        UserService userService = UserService.getInstance(getApplicationContext());
        if(null != userService){
            User user = userService.getActiveAccountInfo();
            if(!(EMClient.getInstance().isLoggedInBefore()) && null != user && !TextUtils.isEmpty(user.getMobile()) && !TextUtils.isEmpty(user.getHxPassword())){
                EMClient.getInstance().login("2-"+user.getMobile(), user.getHxPassword(), new EMCallBack() {
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
        }

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
