package com.yjm.doctor.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.model.DataType;
import com.yjm.doctor.model.DataTypeBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/7.
 */

public class LoginFailureActivity extends BaseActivity {

    private MainAPI mainAPI;
    @Override
    public int initView() {
        mainAPI = RestAdapterUtils.getRestAPI(Config.MAIN_BASEDATA, MainAPI.class,this,"");
        return R.layout.activity_login_fail;
    }

    @Override
    public void finishButton() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        ActivityJumper.getInstance().buttonIntJumpTo(this,LoginActivity.class,1);
        finish();
    }

    @OnClick(R.id.login_fail_button)
    void buttonClick(){
        ActivityJumper.getInstance().buttonJumpTo(this,RegisterInfoActivity.class);
//        finish();
    }

    @OnClick(R.id.login_fail_help)
    void help(){
        showDialog("连线中~");
        mainAPI.baseData("CU", new Callback<DataTypeBean>() {
            @Override
            public void success(DataTypeBean dataTypeBean, Response response) {
                closeDialog();
                if(null != dataTypeBean && true == dataTypeBean.getSuccess()){
                    if(null != dataTypeBean.getObj() && dataTypeBean.getObj().size()>0){
                        for(DataType type : dataTypeBean.getObj()){
                            if((!TextUtils.isEmpty(type.getDescription())) && "电话".equals(type.getDescription()) && !TextUtils.isEmpty(type.getName())){
                                onCall(type.getName());
                            }
                        }
                    }else {
                        SystemTools.show_msg(LoginFailureActivity.this,"连线失败，请重试~");
                    }
                }else {
                    SystemTools.show_msg(LoginFailureActivity.this,"连线失败，请重试~");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                closeDialog();
                SystemTools.show_msg(LoginFailureActivity.this,"连线失败，请重试~");
            }
        });

    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE=123;
    public void onCall(String mobile) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.CALL_PHONE
                }, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                 callDirectly(mobile);
            }
        } else {
            callDirectly(mobile);
        }

    }
    //动态权限申请后处理
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    callDirectly(mobile);
                }else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                }break;
            default:super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } }

    private void callDirectly(String mobile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        startActivity(intent);
    }
}
