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
import com.yjm.doctor.application.baseInterface.IAdd;
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
    TextView toolAdd = null;
    TextView update = null;
    TextView add = null ;
    TextView bar_num = null ;

//    public ActivityLifecycle(String title) {
//        this.mTitle = title;
//    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        Log.i("activity", "life onActivityCreated");
        Log.i("Main", "====================");
        if (activity instanceof IActivity) {
            YjmApplication.tooAdd = false;
            YjmApplication.update = false;
            YjmApplication.toolFinish = false;
            YjmApplication.add = false;
            activity.setContentView(((IActivity) activity).initView());
        }

        toolbar = activity.findViewById(R.id.toolbar);
        toolTitle = (TextView) activity.findViewById(R.id.tooltitle);
        toolIcon = (RelativeLayout) activity.findViewById(R.id.toolicon);
        toolFinishButton = (TextView) activity.findViewById(R.id.toolfinish);
        toolAdd = (TextView) activity.findViewById(R.id.tool_add);
        update = (TextView) activity.findViewById(R.id.toolupdate);
        add = (TextView)activity.findViewById(R.id.add);
        bar_num = (TextView)activity.findViewById(R.id.bar_num);
        if(null != bar_num)bar_num.setVisibility(View.GONE);

        if (null != toolAdd) {
            toolAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((IAdd) activity).add();
                }
            });
        }

        if (null != add) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((IAdd) activity).add();
                }
            });
        }
        if (null != toolFinishButton) {
            toolFinishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IActivity) activity).finishButton();
                }
            });
        }


        if (null != update) {
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IActivity) activity).finishButton();
                }
            });
        }



        if (toolbar != null) { //找到 Toolbar 并且替换 Actionbar
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
        if (toolTitle != null) { //找到 Toolbar 的标题栏并设置标题名
//            if(TextUtils.isEmpty(mTitle)){
            toolTitle.setText(activity.getTitle());
            Log.i("mTitle", "标题: " + activity.getTitle());
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
        Log.i("activity", "life onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i("activity", "life onActivityResumed");

        if (YjmApplication.toolBackIcon) {
            if (null != toolIcon) toolIcon.setVisibility(View.VISIBLE);
        } else {
            if (null != toolIcon) toolIcon.setVisibility(View.GONE);
        }

        if (YjmApplication.tooAdd) {
            if (null != toolAdd) toolAdd.setVisibility(View.VISIBLE);
        } else {
            if (null != toolAdd) toolAdd.setVisibility(View.GONE);
        }

        if (YjmApplication.update) {
            if (null != update) update.setVisibility(View.VISIBLE);
        } else {
            if (null != update) update.setVisibility(View.GONE);
        }
        if (YjmApplication.add) {
            if (null != add) add.setVisibility(View.VISIBLE);
        } else {
            if (null != add) add.setVisibility(View.GONE);
        }

        if (null != toolFinishButton) {
            if (YjmApplication.toolFinish) {
                toolFinishButton.setVisibility(View.VISIBLE);
            } else {
                toolFinishButton.setVisibility(View.GONE);
            }

        }
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
