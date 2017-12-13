package com.yjm.doctor.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by zx on 2017/12/7.
 */

public class ObjectCheck {

    private static ObjectCheck objectCheck;

    public static ObjectCheck getInstance(){
        if(null == objectCheck){
            objectCheck = new ObjectCheck();
        }
        return objectCheck;
    }

    public boolean checkPhoneNum(CharSequence num) {
        if (TextUtils.isEmpty(num)) return false;
        Pattern pattern = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        return pattern.matcher(num).matches();
    }


    public boolean checkPwd(CharSequence pwd){
        if (TextUtils.isEmpty(pwd)) return false;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        return pattern.matcher(pwd).matches();
    }
}
