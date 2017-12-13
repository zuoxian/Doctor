package com.yjm.doctor.ui;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;

import butterknife.OnClick;

/**
 * Created by zx on 2017/12/10.
 */

public class ToExamineActivity extends BaseActivity{

    @Override
    public int initView() {
        return R.layout.activity_to_examine;
    }

    @Override
    public void finishButton() {

    }

    @OnClick(R.id.to_main)
    void toMain(){
        ActivityJumper.getInstance().buttonJumpTo(this,MainActivity.class);
    }
}
