package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.ServiceAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IActivity;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.CustomDatePicker;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/20.
 */

public class AddGridActivity extends BaseActivity implements View.OnClickListener , Callback<Message>{

    private RelativeLayout selectDate;
    private TextView currentDate;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    @BindView(R.id.doctor_time)
    RadioGroup mGroup;

    private String date;

    private int time = 0;

    private ServiceAPI serviceAPI ;


    @Override
    public int initView() {
        YjmApplication.toolFinish = true;
        return R.layout.activity_add_grid;
    }

    @Override
    public void finishButton() {
        if(!TextUtils.isEmpty(date)){
            serviceAPI = RestAdapterUtils.getRestAPI(Config.SERVICE_GRID,ServiceAPI.class,this);
            serviceAPI.addGrid(time,date,this);
        }else {
            SystemTools.show_msg(this, "日期不能为空~");
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        initDatePicker();
        if(null == mGroup) return;
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton)AddGridActivity.this.findViewById(radioButtonId);
                if(!TextUtils.isEmpty(rb.getText()))
                if("不限".equals(rb.getText())){
                    time = 0;
                }else if("上午".equals(rb.getText())){
                    time = 1;
                }else if("下午".equals(rb.getText())){
                    time = 2;
                }else{
                    time = 3;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                date = currentDate.getText().toString();
                break;

        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);
        date = now.split(" ")[0];

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动


    }


    @Override
    public void success(Message message, Response response) {

        if(null != message && !TextUtils.isEmpty(message.getMsg())){
            SystemTools.show_msg(AddGridActivity.this, message.getMsg());
        }


    }

    @Override
    public void failure(RetrofitError error) {
        SystemTools.show_msg(AddGridActivity.this, "添加失败，请重试~2");
        if(null != error && error.getMessage().contains("path $.obj")){
            UserAPI userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class,this);
            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        serviceAPI.addGrid(time,date,AddGridActivity.this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }
}
