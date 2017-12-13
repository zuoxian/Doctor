package com.yjm.doctor.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.List;


/**
 * Created by zx on 2017/12/5.
 */
public class ActivityJumper {

    private static ActivityJumper activityJumper;



    public static ActivityJumper getInstance(){
        if(null == activityJumper){
            activityJumper = new ActivityJumper();
        }
        return activityJumper;

    }

    public void buttonJumpTo(Context ctx ,Class activity){
        Intent intent = new Intent();
        intent.setClass(ctx, activity);
        ctx.startActivity(intent);

    }

    public void buttonObjectJumpTo(Context ctx , Class activity , Object object){
        Intent intent = new Intent();
        intent.setClass(ctx, activity);
        intent.putExtra("object", (Serializable) object);
        ctx.startActivity(intent);

    }

    public void buttoListJumpTo(Context ctx , Class activity , List list){
        Intent intent = new Intent();
        intent.setClass(ctx, activity);
        intent.putExtra("list", (Serializable) list);
        ctx.startActivity(intent);

    }
}
