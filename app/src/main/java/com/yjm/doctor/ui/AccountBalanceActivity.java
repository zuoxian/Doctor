package com.yjm.doctor.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountBalanceActivity extends BaseActivity {


    @BindView(R.id.tv_balance)
    TextView mTvBalance;

    @Override
    public int initView() { return R.layout.activity_account_balance; }


    @Override
    protected void onResume() {
        super.onResume();

        mTvBalance.setText("0");



    }

    @Override
    public void finishButton() {

    }

}
