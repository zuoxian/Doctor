package com.yjm.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainAccountActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolicon)
    RelativeLayout mToolicon;
    @BindView(R.id.tooltitle)
    TextView mTooltitle;
    @BindView(R.id.toolfinish)
    TextView mToolfinish;

    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    ListLayoutAdapter mLayoutAdapter;

    @Override
    public int initView() { return R.layout.activity_account; }

    @Override
    protected void onResume() {
        super.onResume();

        setSupportActionBar(mToolbar);
        mTooltitle.setText(R.string.account);
        mToolicon.setVisibility(View.GONE);
        mToolfinish.setVisibility(View.GONE);
        List<ListLayoutModel> modelList=new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.drawable.business_setting,R.string.account_info,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.patient,R.string.account_balance,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.account,R.string.account_withdrawal,R.drawable.comein));
        mLayoutAdapter=new ListLayoutAdapter(this,modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);
        mLayoutAdapter.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {
                switch (position) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                }
            }
        });
    }

    //返回上一级界面
    @OnClick(R.id.img_operation)
    public void onViewClicked() {

    }

    @Override
    public void finishButton() {

    }


}
