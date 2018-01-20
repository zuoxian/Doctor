package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.model.Comment;
import com.yjm.doctor.model.CommentBean;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.SMessage;
import com.yjm.doctor.model.SMessageBean;
import com.yjm.doctor.model.SMessageRow;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.adapter.MainAppointmentInfoAdapter;
import com.yjm.doctor.ui.adapter.MessagesAdapter;
import com.yjm.doctor.ui.adapter.MyCommentsAdapter;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.base.BaseLoadActivity;
import com.yjm.doctor.ui.fragment.MainAppointmentFragment;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.Helper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.auth.UserService;

import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/24.
 */

public class MessageActivity extends BaseLoadActivity<SMessageBean> implements MessagesAdapter.ListAdapterListener, Callback<SMessageBean>{

    private int mPage = 1;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;

    @BindView(R.id.bar_num)
    TextView barNum;

    MessagesAdapter mAdapter;

    private UserAPI userAPI;

    private LinearLayoutManager mLayoutManager;

    @Override
    public int initView() {
        userAPI = RestAdapterUtils.getRestAPI(Config.MESSAGE,UserAPI.class,this);
        YjmApplication.toolBackIcon = true;
        return R.layout.activity_message;
    }

    @Override
    public void finishButton() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTwoWayView();
        EventBus.getDefault().register(this);
    }



    private void initTwoWayView() {
        mAdapter = new MessagesAdapter(this);

        mLayoutManager = new LinearLayoutManager(this);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    public void onEventMainThread(EventType type){
        if(Config.PUSH_TYPE.equals(type.getType()) && !TextUtils.isEmpty(type.getObject()+"")){
            max =false;
            mPage = 1;
            onLoadData();
            if(null != barNum){
                readnum = readnum +1;
                barNum.setText(String.valueOf(readnum));
                barNum.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onListItemClick(SMessage item) {
        if(null != item){
            ActivityJumper.getInstance().buttonIntJumpTo(this,SMessageInfoActivity.class,item.getId());
        }
    }

    @Override
    public void onListEnded() {

        if (mPage > 1) {
            if(max) {
                Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                return;
            }
            onLoadData();
        } else {
//
        }
    }

    @Override
    protected void onLoadData() {

        showLoad();
;        if (mPage != 1) {
            if(mProgressbar!=null)mProgressbar.setVisibility(View.VISIBLE);
        }
        userAPI.getMessage(mPage,10,this);
        //加载数据
        isLoading = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        max =false;
        mPage = 1;
        onLoadData();
        getReadNum();
    }
    int readnum = 0;
    private void getReadNum(){
        userAPI = RestAdapterUtils.getRestAPI(Config.MESSAGE, UserAPI.class, this);
        userAPI.getMessage(1, 50, new Callback<SMessageBean>() {
            @Override
            public void success(SMessageBean sMessageBean, Response response) {
                if (null != sMessageBean && sMessageBean.getSuccess()) {
                    if(null == sMessageBean.getObj())return ;
                    List<SMessage> list = sMessageBean.getObj().getRows();
                    readnum = 0;
                    for(SMessage sMessage : list){
                        if(!sMessage.isRead()){
                            readnum = readnum + 1;
                        }
                    }
                    if(0 != readnum){
                        if(null != barNum){
                            barNum.setText(String.valueOf(readnum));
                            barNum.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    @Override
    protected void onInitLoadData(SMessageBean pageData) {
        hideEmptyView();

        if(pageData==null)return ;
        List<SMessage> list = pageData.getObj().getRows();

        if (mPage == 1) {
            if(mAdapter!=null)mAdapter.updateItems(list);
        } else if (mPage > 1 && !mAdapter.containsAll(list)) {
            if(mAdapter!=null)mAdapter.addItems(list);
        } else return;

    }


    @Override
    public void onRefresh() {
        max =false;
        mPage = 1;
        onLoadData();
    }

    private boolean max = false;
    @Override
    public void success(SMessageBean commentBean, Response response) {
        stopRefresh();

        if (null != commentBean && commentBean.getSuccess()) {
            if(null == commentBean.getObj() || null == commentBean.getObj().getRows() || !(null != commentBean.getObj().getRows() && commentBean.getObj().getRows().size()>0)){
                showConnectionRetry("无新消息");
                return;
            }

            onInitLoadData(commentBean);
//            if (mPage == 1) {
//                setPageData(commentBean);
//            } else {
//
//            }
            if(!(0 < commentBean.getObj().getTotal() && commentBean.getObj().getTotal()<=10) && (mPage * 10)<commentBean.getObj().getTotal())
                mPage = mPage + 1;
            if((mPage * 10) >= commentBean.getObj().getTotal()){
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
        if(null != error && error.getMessage().contains("path $.obj")){

            final UserAPI userAPI1 = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this,"");
            final UserService userService = UserService.getInstance(this);
            final User user = userService.getActiveAccountInfo();
            userAPI1.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                @Override
                public void success(UserBean userBean, Response response) {
                    if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                        userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                        userAPI.getMessage(mPage,10,MessageActivity.this);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
        stopRefresh();
        showConnectionRetry();
        if (mProgressbar != null) mProgressbar.setVisibility(View.GONE);
        isLoading = false;
    }
}
