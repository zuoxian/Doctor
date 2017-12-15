package com.yjm.doctor.application.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IActivity;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.ui.base.BaseActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by zx on 2017/12/5.
 */

public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

//    private String mTitle;

    View toolbar = null;
    TextView toolTitle = null;
    RelativeLayout toolIcon = null;
    TextView toolFinishButton = null;

//    public ActivityLifecycle(String title) {
//        this.mTitle = title;
//    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        Log.i("activity","life onActivityCreated");
        Log.i("Main","====================");
        if(activity instanceof IActivity){
            activity.setContentView(((IActivity)activity).initView());
        }

        toolbar = activity.findViewById(R.id.toolbar);
        toolTitle = (TextView)activity.findViewById(R.id.tooltitle);
        toolIcon = (RelativeLayout)activity.findViewById(R.id.toolicon);
        toolFinishButton = (TextView)activity.findViewById(R.id.toolfinish);

        if(null != toolFinishButton) {
            toolFinishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IActivity) activity).finishButton();
                }
            });
        }

        if(YjmApplication.toolBackIcon){
            if(null != toolIcon)toolIcon.setVisibility(View.VISIBLE);
        }else{
            if(null != toolIcon)toolIcon.setVisibility(View.GONE);
        }

        if(null != toolFinishButton) {
            if (YjmApplication.toolFinish) {
                toolFinishButton.setVisibility(View.VISIBLE);
            } else {
                toolFinishButton.setVisibility(View.GONE);
            }
        }

        if ( toolbar != null) { //找到 Toolbar 并且替换 Actionbar
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).setSupportActionBar((Toolbar) toolbar);
                ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    activity.setActionBar((android.widget.Toolbar) toolbar);
                    activity.getActionBar().setDisplayShowTitleEnabled(false);
                }
            }
        }
        if ( toolTitle != null) { //找到 Toolbar 的标题栏并设置标题名
//            if(TextUtils.isEmpty(mTitle)){
                toolTitle.setText(activity.getTitle());
            Log.i("mTitle", "标题: "+ activity.getTitle() );
//            } else {
//                toolTitle.setText(mTitle);
//            }
        }



        if (toolIcon != null) { //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
            toolIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });

        }


    }



    @Override
    public void onActivityStarted(Activity activity) {
        Log.i("activity","life onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i("activity","life onActivityResumed");
//        if(null != toolTitle){
//            toolTitle.setText(mTitle);
//        }

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i("activity","life onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i("activity","life onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i("activity","life onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i("activity","life onActivityDestroyed");
    }
}
