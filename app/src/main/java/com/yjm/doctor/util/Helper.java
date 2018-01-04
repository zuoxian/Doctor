package com.yjm.doctor.util;

import android.app.Activity;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.model.EaseNotifier;
import com.yjm.doctor.application.YjmApplication;

/**
 * Created by zx on 2018/1/4.
 */



public class Helper {

    private static Helper instance = null;

    public synchronized static Helper getInstance() {
        if (instance == null) {
            instance = new Helper();
        }
        return instance;
    }

    /**
     * logout
     *
     * @param unbindDeviceToken
     *            whether you need unbind your device token
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
//        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
//                Log.d(TAG, "logout: onSuccess");
//                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
//                Log.d(TAG, "logout: onSuccess");
//                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    public void popActivity(Activity activity) {
        YjmApplication.easeUI.popActivity(activity);
    }


    /**
     * get instance of EaseNotifier
     * @return
     */
    public EaseNotifier getNotifier(){
        return YjmApplication.easeUI.getNotifier();
    }


    public void pushActivity(Activity activity) {
        YjmApplication.easeUI.pushActivity(activity);
    }




    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
