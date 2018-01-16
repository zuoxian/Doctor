package com.yjm.doctor.ui;

import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.MessageInfo;
import com.yjm.doctor.model.SMessage;
import com.yjm.doctor.model.SMessageBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.RestAdapterUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2018/1/11.
 */

public class SMessageInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.time)
    TextView time;


    private UserAPI userAPI;
    private int id;

    @Override
    public int initView() {
        return R.layout.activity_smessageinfo;
    }

    @Override
    public void finishButton() {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id",2);
        userAPI = RestAdapterUtils.getRestAPI(Config.MESSAGE,UserAPI.class,this);
        userAPI.getMessageInfo(id, new Callback<MessageInfo>() {
            @Override
            public void success(MessageInfo sMessageBean, Response response) {
                if(null != sMessageBean && true == sMessageBean.getSuccess() && null != sMessageBean.getObj()){
                    if(null != title) title.setText(sMessageBean.getObj().getTitle());
                    if(null != time) time.setText(longToString(sMessageBean.getObj().getCreateTime(),"MM-dd HH:mm"));
                    SMessage message = sMessageBean.getObj();

                    if(!TextUtils.isEmpty(message.getMtype()) && ("MT01".equals(message.getMtype()) || "MT03".equals(message.getMtype()) )){
                        if(null != content) content.setText(message.getContent());
                    }else{
                        if(null != content) content.setText(message.getPushContent());
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return sDateTime;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }
}
