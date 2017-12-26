package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.ServiceAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IAdd;
import com.yjm.doctor.model.Grid;
import com.yjm.doctor.model.GridBean;
import com.yjm.doctor.model.GridInfo;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.GridAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GridActivity extends BaseActivity implements GridAdapter.OnListItemOnClickListener,Callback<GridBean>,IAdd{

    private static final String TAG = "GridActivity";

    @BindView(R.id.listview_layout)
    ListView mListviewLayout;

    @BindView(R.id.empty)
    TextView mEmpty;

    private List<GridInfo> modelList=null;
    private GridAdapter mLayoutAdapter;
    private ServiceAPI serviceAPI=null;
    private UserAPI userAPI = null ;
    private User mUser;
    private String tokenID;
    private SharedPreferencesUtil sharedPreferencesUtil;


    @Override
    public int initView() {
        YjmApplication.add = true;
        return R.layout.activity_user_info; }

    @OnClick(R.id.img_operation)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelList=new ArrayList<GridInfo>();
        mLayoutAdapter=new GridAdapter(this);
        mListviewLayout.setAdapter(mLayoutAdapter);

        mLayoutAdapter.setOnListItemOnClickListener(this);
        serviceAPI = RestAdapterUtils.getRestAPI(Config.SERVICE_GRID, ServiceAPI.class,this);




    }


    private void initData(){
        if (NetworkUtils.isNetworkAvaliable(this)) {
            serviceAPI.getGrid(this);
            showDialog("加载中");
        } else {
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
            if(null != mEmpty){
                mEmpty.setText("网络异常，点我重新加载");
                mEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }


    @Override
    public void success(GridBean gridBean, Response response) {
        closeDialog();
        if(null != mEmpty){
            mEmpty.setVisibility(View.GONE);
        }
        Log.i("gird","=1===");

        if (null != gridBean && null != gridBean.getObj()){
            UpdateUI(gridBean.getObj());
        }else {
            if(null != mEmpty){
                mEmpty.setText("加载失败，点我重新加载");
                mEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.empty)
    void empty(){
        initData();
    }

    private void UpdateUI(Grid gridList){
        if(null == gridList) {
            if(null != mEmpty){
                mEmpty.setText("加载失败，点我重新加载");
                mEmpty.setVisibility(View.VISIBLE);
            }
            return;}
        if(null != gridList.getRows() && 0 == gridList.getRows().size()){
            if(null != mEmpty){
                mEmpty.setText("您没有发布停诊信息");
                mEmpty.setVisibility(View.VISIBLE);
            }
            return;
        }
        modelList = gridList.getRows();
        mLayoutAdapter.setData(modelList);
        mLayoutAdapter.notifyDataSetChanged();

    }

    @Override
    public void failure(RetrofitError error) {

        closeDialog();
        Log.i("gird","=1===");
        if(null != error && error.getMessage().contains("path $.obj")){
            userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class,this);
            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        serviceAPI.getGrid(GridActivity.this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }

    }

    @Override
    public void finishButton() {

    }

    @Override
    public void OnItemClick(int position, final GridInfo model) {
        if(null == model) return;

        serviceAPI.delGrid(model.getId(), new Callback<GridBean>(){

            @Override
            public void success(GridBean message, Response response) {
                if(null != message){
                    SystemTools.show_msg(GridActivity.this,message.getMsg());
                    if(true == message.getSuccess()){
                        modelList.remove(model);
                        mLayoutAdapter.setData(modelList);
                        mLayoutAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SystemTools.show_msg(GridActivity.this,"删除失败，请重试~");
            }
        } );
    }


    @Override
    public void add() {
        ActivityJumper.getInstance().buttonJumpTo(this,AddGridActivity.class);
    }
}
