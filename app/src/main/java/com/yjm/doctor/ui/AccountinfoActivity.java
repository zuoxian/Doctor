package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ListView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.Account;
import com.yjm.doctor.model.AccountBean;
import com.yjm.doctor.model.DataType;
import com.yjm.doctor.model.DataTypeBean;
import com.yjm.doctor.model.DepartMent;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Hospital;
import com.yjm.doctor.model.HospitalBean;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountinfoActivity extends BaseActivity implements Callback<AccountBean>,ListLayoutAdapter.OnListItemOnClickListener{
    private static final String TAG = "c";


    private ListView mListviewTop,mListviewBottom;
    private List<ListLayoutModel> modelListTop,modelListBottom;
    private ListLayoutAdapter mLayoutAdapterTop,mLayoutAdapterBottom;

    private UserAPI userAPI;

    private MainAPI mainAPI;

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

        mainAPI = RestAdapterUtils.getRestAPI(Config.MAIN_BASEDATA, MainAPI.class,this);

        getBCs();
        EventBus.getDefault().register(this);

        mListviewTop=(ListView)findViewById(R.id.listview_top);
        mListviewBottom=(ListView)findViewById(R.id.listview_bottom);

        modelListTop=new ArrayList<ListLayoutModel>();
        mLayoutAdapterTop=new ListLayoutAdapter(this,modelListTop);
        mListviewTop.setAdapter(mLayoutAdapterTop);
        mLayoutAdapterTop.setOnListItemOnClickListener(this);


        modelListBottom=new ArrayList<ListLayoutModel>();
        mLayoutAdapterBottom=new ListLayoutAdapter(this);
        mListviewBottom.setAdapter(mLayoutAdapterBottom);
        mLayoutAdapterBottom.setOnListItemOnClickListener(this);



    }
    private List<DataType> dataTypes;

    private void getBCs(){
        mainAPI.baseData("BC",new Callback<DataTypeBean>() {
                @Override
                public void success(DataTypeBean levelBean, Response response) {
                    closeDialog();

                    if(null != levelBean && true == levelBean.getSuccess()){
                        if(null == levelBean.getObj()) {
                            SystemTools.show_msg(AccountinfoActivity.this, R.string.level_fail);
                            return;
                        }
                        dataTypes = levelBean.getObj();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    closeDialog();
                }
            });

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
            modelListTop.clear();
            mLayoutAdapterTop.notifyDataSetChanged();
            account = accountBean.getObj();
            modelListTop.add(new ListLayoutModel(R.string.name,account.getBankAccount(),R.drawable.toolicon));
            modelListTop.add(new ListLayoutModel(R.string.id_card,account.getBankCard(),R.drawable.toolicon));
            mLayoutAdapterTop.setData(modelListTop);
            mLayoutAdapterTop.notifyDataSetChanged();
//            mLayoutAdapterTop.setOnListItemOnClickListener(this);
            modelListBottom.clear();
            mLayoutAdapterBottom.notifyDataSetChanged();
            modelListBottom.add(new ListLayoutModel(R.string.alipay_account,account.getAlipay(),R.drawable.toolicon));
            modelListBottom.add(new ListLayoutModel(R.string.wechatpay_account,account.getBankName(),R.drawable.toolicon));
            modelListBottom.add(new ListLayoutModel(R.string.wechatpay_card,account.getBankIdNo(),R.drawable.toolicon));
            mLayoutAdapterBottom.setData(modelListBottom);
            mLayoutAdapterBottom.notifyDataSetChanged();
//            mLayoutAdapterBottom.setOnListItemOnClickListener(this);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if(null != error && error.getMessage().contains("path $.obj")){

            final UserAPI userAPI1 = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");
            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI1.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());

                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }

    private int bank_index = 1;
    public void onEventMainThread(EventType event) {
        if(Config.BC_EVENTTYPE.equals(event.getType()) && null != event && null != event.getObject()){
            userAPI.updateAccount(account.getBankAccount(), account.getBankPhone(), account.getBankIdNo(), account.getBankCode(), ((DataType) event.getObject()).getName(), account.getBankCard(), account.getAlipay(), new Callback<Message>() {
                @Override
                public void success(Message message, Response response) {
                    if(null != message && true == message.getSuccess()){

                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }

    }

    @Override
    public void OnItemClick(int position, ListLayoutModel model) {
        if(null !=model && R.string.wechatpay_account == model.getTitle()){
            bank_index = position;
                if(null != dataTypes && 0 < dataTypes.size()) {
                    ActivityJumper.getInstance().buttoListJumpTo(this, BCActivity.class, dataTypes);
                }else{
                    showDialog("正在加载~");
                    getBCs();
                }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
