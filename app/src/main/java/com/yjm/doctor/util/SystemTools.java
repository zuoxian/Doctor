package com.yjm.doctor.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zx on 2017/12/7.
 */

public class SystemTools {

    public static void show_msg(Context context, int msg_res) {
        show_msg(context, context.getString(msg_res));
    }

    public static void show_msg(Context context, String msg) {
        if (context == null) return;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
