package com.yjm.doctor.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.RequestQueue;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.model.VersionBean;
import com.yjm.doctor.model.VersionInfo;
import com.yjm.doctor.ui.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2018/2/1.
 */

public class UpdateManager {
    private static final String VERSION_PATH = "http://192.168.11.140:8088/version/version.html";
    private static final int DOWNLOAD_ING = 1;
    private static final int DOWNLOAD_OVER = 2;
    private String version_is_force;
    private String version_file_path;
    private  String version_update_log;
    private boolean version_update_mark;

    private String downloadPath;
    private String version;
    private Context mContext;
    private String mSavePath;
//    private ProgressBar mProgress;
    private int progress;
    private Dialog downDialog;
    private Boolean isCancle = false;
    public UpdateManager(Context context){
        mContext = context;
    }

    private Handler versionHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            VersionInfo jsonObject = (VersionInfo) msg.obj;
            try {
                version_is_force = jsonObject.getIsForce();
                version_file_path = jsonObject.getFilePath();
                version_update_log = jsonObject.getUpdateLog();
                version_update_mark = jsonObject.isUpdateMark();
                downloadPath = jsonObject.getDownloadPath();
                version = jsonObject.getVersion();
                if (version_update_mark && isUpdate()){
//                    Toast.makeText(mContext, "需要更新", Toast.LENGTH_SHORT).show();
                    // 显示提示更新对话框
//                    if("1".equals(version_is_force)) {
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(version_file_path);
//                        intent.setData(content_url);
//                        mContext.startActivity(intent);
//                    }else  {
                        showNoticeDialog();
//                    }
                } else{
//                    Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };



    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     *检测软件是否需要更新
     * */
    public void checkUpdate() throws Exception {

        String v = "1.0.0";
        String v1 = getVersionName();
        if(!TextUtils.isEmpty(v1))
            v = v1;
        MainAPI mainAPI = RestAdapterUtils.getRestAPI(Config.UPDATE_URL,MainAPI.class,mContext);
        mainAPI.checkUpdate(v,2,new Callback<VersionBean>(){

            @Override
            public void success(VersionBean versionBean, Response response) {
                if(null != versionBean && true == versionBean.getSuccess()){
                    if(null != versionBean.getObj()){
                        Message msg = Message.obtain();
                        msg.obj = versionBean.getObj();
                        versionHandler.sendMessage(msg);
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    /**
     * 是否需要更新
     * @return
     */
    protected boolean isUpdate() {

        try {
            String v1 = getVersionName();
            if(1 == VersionComparison(version,v1)){
                return  true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return  false;
    }


    public static int VersionComparison(String versionServer, String versionLocal) {
        String version1 = versionServer;
        String version2 = versionLocal;
        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0)
            throw new IllegalArgumentException("Invalid parameter!");

        int index1 = 0;
        int index2 = 0;
        while (index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            int[] number2 = getValue(version2, index2);

            if (number1[0] < number2[0]){
                return -1;
            }
            else if (number1[0] > number2[0]){
                return 1;
            }
            else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }
        if (index1 == version1.length() && index2 == version2.length())
            return 0;
        if (index1 < version1.length())
            return 1;
        else
            return -1;
    }


    public static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;

        return value_index;
    }

    /*
    * 有更新时显示提示对话框
    */
    protected void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("发现新版本");
        builder.setCancelable(false);
        builder.setMessage(version_update_log);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消对话框
                dialog.dismiss();
//                Intent intent = new Intent();
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(version_file_path);
                intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
                intent.setData(content_url);
                mContext.startActivity(intent);
                //下载文件
//            ActivityJumper.getInstance().buttonObjectJumpTo(mContext, WebViewActivity.class,version_file_path);
            }
        });

        if("0".equals(version_is_force)) {
            builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //取消对话框
                    dialog.dismiss();
                }
            });
        }
        builder.create().show();
    }


    /*
     * 显示正在下载对话框
     */
//    protected void showDownloadDialog() {
////        View view = LayoutInflater.from(mContext).inflate(R.layout.down_progress,null);
////        mProgress = (ProgressBar) view.findViewById(R.id.id_Progress);
////        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
////        builder.setTitle("正在下载");
////        builder.setView(view);
////        builder.setNegativeButton("取消下载", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                isCancle = true;
////            }
////        });
////        downDialog = builder.create();
////        downDialog.show();
//
//        downloadAPK();
//    }

    /*
    * 开启新线程下载文件
    */
//    private void downloadAPK() {
//        System.out.println(downloadPath);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                        mSavePath = sdPath +"deanDownload";
//                        File file = new File(mSavePath);
//                        if(!file.exists()){
//                            file.mkdir();
//                        }
//
//                        URL url = new URL(downloadPath);
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        connection.connect();
//                        InputStream is = connection.getInputStream();
//
//                        int len = connection.getContentLength();
//                        File apkFile = new File(mSavePath, "doctor"+version+".apk");
//                        FileOutputStream fos = new FileOutputStream(apkFile);
//                        int count = 0;
//                        byte[] buffer = new byte[1024];
//                        while(!isCancle){
//                            int numread = is.read(buffer);
//                            count += numread;
//                            // 计算进度条的当前位置
////                            progress = (int) (((float)count/len) * 100);
//                            // 更新进度条
//                            progressHandler.sendEmptyMessage(DOWNLOAD_ING);
//
//                            // 下载完成
//                            if (numread < 0){
//                                progressHandler.sendEmptyMessage(DOWNLOAD_OVER);
//                                break;
//                            }
//                            fos.write(buffer, 0, numread);
//                        }
//                    }else{
////                        System.out.println("00000000000000000000000000000");
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//    }

//
//    /*
//     * 下载到本地后执行安装
//     */
//    protected void installAPK() {
//        File apkFile = new File(mSavePath, "doctor"+version+".apk");
//        if (!apkFile.exists())
//            return;
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse("file://" + apkFile.toString());
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        mContext.startActivity(intent);
//    }
}

