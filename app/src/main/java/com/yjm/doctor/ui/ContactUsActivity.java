package com.yjm.doctor.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.DataType;
import com.yjm.doctor.model.DataTypeBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.RestAdapterUtils;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/24.
 */

public class ContactUsActivity extends BaseActivity implements Callback<DataTypeBean>{

    @BindView(R.id.tel)
    TextView mTel;

    @BindView(R.id.mail)
    TextView mMail;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.user_icon)
    SimpleDraweeView mUserAddressImage;

    private MainAPI mainAPI;
    @Override
    public int initView() {
        mainAPI = RestAdapterUtils.getRestAPI(Config.MAIN_BASEDATA, MainAPI.class,this,"");
        return R.layout.activity_contact_us;
    }

    @Override
    public void finishButton() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainAPI.baseData("CU",this);
    }


    @Override
    public void success(DataTypeBean dataTypeBean, Response response) {
        if(null != dataTypeBean && true == dataTypeBean.getSuccess()){
            if(null != dataTypeBean.getObj() && dataTypeBean.getObj().size()>0){
                for(DataType type : dataTypeBean.getObj()){
                    if("CU04".equals(type.getId()) && null != mUserAddressImage && !TextUtils.isEmpty(type.getIcon())){
                        mUserAddressImage.setImageURI(Uri.parse(type.getIcon()));
                    }

                    if((!TextUtils.isEmpty(type.getDescription())) && "电话".equals(type.getDescription()) && null != mTel && !TextUtils.isEmpty(type.getName())){
                        mTel.setText(type.getName());
                    }

                    if((!TextUtils.isEmpty(type.getDescription())) && "邮箱".equals(type.getDescription()) && null != mMail && !TextUtils.isEmpty(type.getName())){
                        mMail.setText(type.getName());
                    }

                    if((!TextUtils.isEmpty(type.getDescription())) && "联系地址".equals(type.getDescription()) && null != mAddress && !TextUtils.isEmpty(type.getName())){
                        mAddress.setText(type.getName());
                    }

                }
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
