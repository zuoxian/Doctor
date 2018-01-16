package com.yjm.doctor.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.AppointmentBean;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.MainAppointmentsInfoActivity;
import com.yjm.doctor.ui.adapter.MainAppointmentInfoAdapter;
import com.yjm.doctor.ui.base.BaseLoadFragment;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.auth.UserService;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */
public class MainAppointmentFragment extends BaseLoadFragment<AppointmentBean> implements MainAppointmentInfoAdapter.ListAdapterListener, TextWatcher {

    private int mPage = 1;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;

    @BindView(R.id.search)
    EditText search;

    MainAppointmentInfoAdapter mAdapter;

    private Handler mHandler;

    private SearchTask mSearchTask;

    private int mType ;//是未回复  还是已回复


    private boolean isSearch = false;
    MainAPI mainAPI ;

    public MainAppointmentFragment() {

    }

    private LinearLayoutManager mLayoutManager;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initTwoWayView();
        super.onActivityCreated(savedInstanceState);

        if(null ==search)
            return;
        search.addTextChangedListener(this);
        initData();
    }

    private void initData(){
        mHandler = new Handler();
        mSearchTask = new SearchTask();
    }

    private void initTwoWayView() {
        mAdapter = new MainAppointmentInfoAdapter(this.getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if(mRecyclerView!=null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLongClickable(false);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setListener(this);
    }


    @Override
    protected int getLayoutRes() {
        Bundle bundle = this.getArguments();
        if("appointment_make".equals(bundle.getString("appointment_make"))){
            mType = 0;
        }
        if("appointment_reply".equals(bundle.getString("appointment_reply"))){
            mType = 1;
        }
        mainAPI = RestAdapterUtils.getRestAPI(Config.HOME_APPOINTMENT, MainAPI.class, getActivity(),"");
        return R.layout.fragment_main_appointment_content;
    }

    @Override
    protected void onLoadData() {
        showLoad();
        if(null == getActivity())return ;
        if (mPage != 1) {
            if(mProgressbar!=null)mProgressbar.setVisibility(View.VISIBLE);
        }
        Log.i("main",UserService.getInstance(getActivity()).getTokenId(Config.userId));
        if(isSearch){
            if(null != search && !TextUtils.isEmpty(search.getText())) {
                mPage = 1;
                isSearch = true;
                mainAPI.appointments(UserService.getInstance(getActivity()).getTokenId(Config.userId), mType, search.getText().toString(), mPage, 10, MainAppointmentFragment.this);

            }
        }else{
        isSearch = false;
        mainAPI.appointments(UserService.getInstance(getActivity()).getTokenId(Config.userId),mType,"",mPage,10, this);
        }
        isLoading = true;
    }


    @Override
    public void onRefresh() {
        max =false;
        mPage = 1;
        onLoadData();
    }

    @Override
    protected void onInitLoadData(AppointmentBean pageData) {
        Log.i("main","数据分析");
        hideEmptyView();

        if(pageData==null)return ;
        List<AppointmentInfo> list = pageData.getObj().getRows();
        if (mPage == 1) {

            if(mAdapter!=null)mAdapter.updateItems(list);
        } else if (mPage > 1 && !mAdapter.containsAll(list)) {
            if(mAdapter!=null)mAdapter.addItems(list);
        } else return;


    }
    private boolean max = false;

    @Override
    public void success(AppointmentBean subListPage, Response response) {
        stopRefresh();

        if (null != subListPage && subListPage.getSuccess()) {
            if(null == subListPage.getObj() || null == subListPage.getObj().getRows() || !(null != subListPage.getObj().getRows() && subListPage.getObj().getRows().size()>0)){
                showConnectionRetry("无新消息");
                return;
            }
            if (mPage == 1) {
                setPageData(subListPage);
            } else {
                onInitLoadData(subListPage);
            }
            if(!(0 < subListPage.getObj().getTotal() && subListPage.getObj().getTotal()<=10) && (mPage * 10)<subListPage.getObj().getTotal())
                mPage = mPage + 1;
            if((mPage * 10) >= subListPage.getObj().getTotal()){
                max = true;
            }
        } else {
            showConnectionRetry("请求异常，请重试");
        }

        if (mProgressbar != null) mProgressbar.setVisibility(View.GONE);

        isLoading = false;
    }

    @Override
    public void failure(RetrofitError error) {

        Log.i("main","失败"+error.getUrl()+","+error.getMessage());
        if(null != error && error.getMessage().contains("path $.obj")){
            if(null != getActivity()) {
                UserAPI userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, getActivity(),"");
                final UserService userService = UserService.getInstance(getActivity());
                final User user = userService.getActiveAccountInfo();
                userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                    @Override
                    public void success(UserBean userBean, Response response) {
                        if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                            userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                            if(isSearch){
                                if(null != search && !TextUtils.isEmpty(search.getText())) {
                                    mPage = 1;
                                    isSearch = true;
                                    mainAPI.appointments(UserService.getInstance(getActivity()).getTokenId(Config.userId), mType, search.getText().toString(), mPage, 10, MainAppointmentFragment.this);

                                }
                            }else {
                                isSearch = false;
                                mainAPI.appointments(UserService.getInstance(getActivity()).getTokenId(Config.userId), mType, "", mPage, 10, MainAppointmentFragment.this);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        }
        stopRefresh();
        showConnectionRetry();
        if (mProgressbar != null) mProgressbar.setVisibility(View.GONE);
        isLoading = false;

    }

    @Override
    public void onListItemClick(AppointmentInfo item) {
        Log.i("app","AppointmentInfo:"+item.toString());
        ActivityJumper.getInstance().buttonObjectJumpTo(getActivity(), MainAppointmentsInfoActivity.class,item,mType);
    }

    @Override
    public void onListEnded() {

        if (mPage > 1) {
            if(max && null != getActivity()) {
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                return;
            }
            onLoadData();
        } else {
//            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() > 0){
            mHandler.removeCallbacks(mSearchTask);
            mHandler.postDelayed(mSearchTask,500);
        }else{
            mHandler.removeCallbacks(mSearchTask);
        }
    }

    @Override
    public void afterTextChanged(Editable s) { }

    class SearchTask implements Runnable{
        @Override
        public void run() {
            if(null != search && !TextUtils.isEmpty(search.getText())) {
                mPage = 1;
                isSearch = true;
                mainAPI.appointments(UserService.getInstance(getActivity()).getTokenId(Config.userId), mType, search.getText().toString(), mPage, 10, MainAppointmentFragment.this);

            }
        }
    }
}
