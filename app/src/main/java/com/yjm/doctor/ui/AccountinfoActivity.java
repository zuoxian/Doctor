package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountinfoActivity extends BaseActivity {
    private static final String TAG = "c";
    
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
//
//    @BindView(R.id.img_operation)
//    ImageView mImgOperation;
//
//    @BindView(R.id.toolicon)
//    RelativeLayout mToolicon;
//
//    @BindView(R.id.tooltitle)
//    TextView mTooltitle;
//
//    @BindView(R.id.toolfinish)
//    TextView mToolfinish;

    private ListView mListviewTop,mListviewBottom;
    private List<ListLayoutModel> modelListTop,modelListBottom;
    private ListLayoutAdapter mLayoutAdapterTop,mLayoutAdapterBottom;


    @Override
    public int initView() { return R.layout.activity_accountinfo; }

    //返回上一级界面
    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


//        setSupportActionBar(mToolbar);
//        mTooltitle.setText(R.string.account_info);
//        mToolicon.setVisibility(View.GONE);
//        mToolfinish.setVisibility(View.GONE);
        mListviewTop=(ListView)findViewById(R.id.listview_top);
        mListviewBottom=(ListView)findViewById(R.id.listview_bottom);

        modelListTop=new ArrayList<ListLayoutModel>();
        modelListTop.add(new ListLayoutModel(R.string.name,"",R.drawable.comein));
        modelListTop.add(new ListLayoutModel(R.string.id_card,"",R.drawable.comein));
        mLayoutAdapterTop=new ListLayoutAdapter(this,modelListTop);
        mListviewTop.setAdapter(mLayoutAdapterTop);


        modelListBottom=new ArrayList<ListLayoutModel>();
        modelListBottom.add(new ListLayoutModel(R.string.alipay_account,"",R.drawable.comein));
        modelListBottom.add(new ListLayoutModel(R.string.wechatpay_account,"",R.drawable.comein));
        mLayoutAdapterBottom=new ListLayoutAdapter(this,modelListBottom);
        mListviewBottom.setAdapter(mLayoutAdapterBottom);

        //顶部
        mLayoutAdapterTop.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {

            }
        });

        mLayoutAdapterBottom.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {

            }
        });

    }

    @Override
    public void finishButton() {

    }

}
