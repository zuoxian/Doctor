package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.Account;
import com.yjm.doctor.model.AccountBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountinfoActivity extends BaseActivity implements Callback<AccountBean>{
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

    private UserAPI userAPI;

    private Account account;


    @Override
    public int initView() {
        YjmApplication.update = true;
        YjmApplication.tooAdd = false;
        return R.layout.activity_accountinfo; }

    //返回上一级界面
    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userAPI = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class,this);

        mListviewTop=(ListView)findViewById(R.id.listview_top);
        mListviewBottom=(ListView)findViewById(R.id.listview_bottom);

        modelListTop=new ArrayList<ListLayoutModel>();
//        modelListTop.add(new ListLayoutModel(R.string.name,"",R.drawable.comein));
//        modelListTop.add(new ListLayoutModel(R.string.id_card,"",R.drawable.comein));
        mLayoutAdapterTop=new ListLayoutAdapter(this,modelListTop);
        mListviewTop.setAdapter(mLayoutAdapterTop);


        modelListBottom=new ArrayList<ListLayoutModel>();
//        modelListBottom.add(new ListLayoutModel(R.string.alipay_account,"",R.drawable.comein));
//        modelListBottom.add(new ListLayoutModel(R.string.wechatpay_account,"",R.drawable.comein));
        mLayoutAdapterBottom=new ListLayoutAdapter(this);
        mListviewBottom.setAdapter(mLayoutAdapterBottom);

    }

    @Override
    protected void onResume() {
        super.onResume();

        userAPI.getAccount(this);


    }

    @Override
    public void finishButton() {
        if(null != account)
            ActivityJumper.getInstance().buttonObjectJumpTo(this,UpateAccountActivity.class,account);
        finish();
    }

    @Override
    public void success(AccountBean accountBean, Response response) {
        if(null != accountBean && true == accountBean.getSuccess()){
            if(null == accountBean.getObj())return;
//            modelListTop.clear();
            account = accountBean.getObj();
            modelListTop.add(new ListLayoutModel(R.string.name,account.getBankAccount(),R.drawable.toolicon));
            modelListTop.add(new ListLayoutModel(R.string.id_card,account.getBankCard(),R.drawable.toolicon));
            mLayoutAdapterTop.setData(modelListTop);
            mLayoutAdapterTop.notifyDataSetChanged();
//            modelListBottom.clear();
            modelListBottom.add(new ListLayoutModel(R.string.alipay_account,account.getAlipay(),R.drawable.toolicon));
            modelListBottom.add(new ListLayoutModel(R.string.wechatpay_account,account.getBankName(),R.drawable.toolicon));
            modelListBottom.add(new ListLayoutModel(R.string.wechatpay_card,account.getBankIdNo(),R.drawable.toolicon));
            mLayoutAdapterBottom.setData(modelListBottom);
            mLayoutAdapterBottom.notifyDataSetChanged();
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
