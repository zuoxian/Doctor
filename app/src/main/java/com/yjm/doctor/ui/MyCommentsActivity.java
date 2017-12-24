package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.model.Comment;
import com.yjm.doctor.model.CommentBean;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.adapter.MainAppointmentInfoAdapter;
import com.yjm.doctor.ui.adapter.MyCommentsAdapter;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.base.BaseLoadActivity;
import com.yjm.doctor.ui.fragment.MainAppointmentFragment;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.auth.UserService;

import java.util.List;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/24.
 */

public class MyCommentsActivity extends BaseLoadActivity<CommentBean> implements MyCommentsAdapter.ListAdapterListener, Callback<CommentBean>{

    private int mPage = 1;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;

    MyCommentsAdapter mAdapter;

    private UserAPI userAPI;

    private LinearLayoutManager mLayoutManager;

    @Override
    public int initView() {
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class);

        return R.layout.activity_my_comment;
    }

    @Override
    public void finishButton() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTwoWayView();
    }

    private void initTwoWayView() {
        mAdapter = new MyCommentsAdapter(this);

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
    public void onListItemClick(Comment item) {

    }

    @Override
    public void onListEnded() {

    }

    @Override
    protected void onLoadData() {
        showLoad();
        if (mPage != 1) {
            if(mProgressbar!=null)mProgressbar.setVisibility(View.VISIBLE);
        }
        userAPI.getComments(this);
        //加载数据
        isLoading = true;
    }

    @Override
    protected void onInitLoadData(CommentBean pageData) {
        hideEmptyView();

        if(pageData==null)return ;
        List<Comment> list = pageData.getObj().getRows();
        if (mPage == 1) {

            if(mAdapter!=null)mAdapter.updateItems(list);
        } else if (mPage > 1 && !mAdapter.containsAll(list)) {
            if(mAdapter!=null)mAdapter.addItems(list);
        } else return;

    }


    @Override
    public void onRefresh() {
        mPage = 1;
        onLoadData();
    }

    @Override
    public void success(CommentBean commentBean, Response response) {
        stopRefresh();

        if (null != commentBean && commentBean.getSuccess()) {
            if(null == commentBean.getObj() || null == commentBean.getObj().getRows() || !(null != commentBean.getObj().getRows() && commentBean.getObj().getRows().size()>0)){
                showConnectionRetry("无新消息");
                return;
            }
            if (mPage == 1) {
                setPageData(commentBean);
            } else {
                onInitLoadData(commentBean);
            }
            if(!(0 < commentBean.getObj().getTotal() && commentBean.getObj().getTotal()<=10) && (mPage * 10)<commentBean.getObj().getTotal())
                mPage = mPage + 1;
        } else {
            showConnectionRetry("请求异常，请重试");
        }

        if (mProgressbar != null) mProgressbar.setVisibility(View.GONE);

        isLoading = false;
    }

    @Override
    public void failure(RetrofitError error) {
        if(null != error && error.getMessage().contains("path $.obj")){

                final UserAPI userAPI1 = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class, this);
                final UserService userService = UserService.getInstance(this);
                final User user = userService.getActiveAccountInfo();
                userAPI1.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                    @Override
                    public void success(UserBean userBean, Response response) {
                        if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                            userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                            userAPI.getComments(MyCommentsActivity.this);
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
