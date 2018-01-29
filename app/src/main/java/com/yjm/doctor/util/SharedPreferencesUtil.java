package com.yjm.doctor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zx on 2017/12/14.
 */

public class SharedPreferencesUtil {

    private static SharedPreferencesUtil sharedPreferencesUtil;

    long startTime = 0l;
    long endTime = 0l;

    Context mContext;
    public SharedPreferencesUtil(Context context) {
        this.mContext = context;
    }

   public static SharedPreferencesUtil  instance(Context context){
       if(null == sharedPreferencesUtil){
           sharedPreferencesUtil = new SharedPreferencesUtil(context);
       }
       return sharedPreferencesUtil;
   }

    /**
     * 序列化对象
     *
     * @param person
     * @return
     * @throws IOException
     */
    public String serialize(Object person) throws IOException {
        startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(person);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        endTime = System.currentTimeMillis();
        Log.d("serial", "序列化耗时为:" + (endTime - startTime));
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
        if(null == str)return null;
        startTime = System.currentTimeMillis();
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object person = (Object) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        endTime = System.currentTimeMillis();
        Log.d("serial", "反序列化耗时为:" + (endTime - startTime));
        return person;
    }

    public void saveObject(String objName,String strObject) {
        SharedPreferences sp = mContext.getSharedPreferences(objName, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(objName, strObject);
        edit.commit();
    }

    public String getObject(String objName) {
        SharedPreferences sp = mContext.getSharedPreferences(objName, 0);
        return sp.getString(objName, null);
    }

    public void del(String objName){
        SharedPreferences sp = mContext.getSharedPreferences(objName, 0);
        sp.edit().clear().commit();
    }
}
