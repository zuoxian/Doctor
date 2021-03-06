package com.yjm.doctor.ui;

import android.content.Intent;
import android.widget.ListView;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainAccountActivity extends BaseActivity {


    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    ListLayoutAdapter mLayoutAdapter;

    @Override
    public int initView() { return R.layout.activity_main_account; }
    //返回上一级界面
    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<ListLayoutModel> modelList=new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.drawable.business_setting,R.string.account_info,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.patient,R.string.account_balance,R.drawable.comein));
//        modelList.add(new ListLayoutModel(R.drawable.account,R.string.account_withdrawal,R.drawable.comein));
        mLayoutAdapter=new ListLayoutAdapter(this,modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);
        mLayoutAdapter.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {
                switch (position) {
                    case 0: {
                        startActivity(new Intent(MainAccountActivity.this,AccountinfoActivity.class));
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(MainAccountActivity.this,AccountBalanceActivity.class));

                        break;
                    }
//                    case 2: {
//                        break;
//                    }
                }
            }
        });
    }


    @Override
    public void finishButton() {

    }


}
