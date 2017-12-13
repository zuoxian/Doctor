package com.yjm.doctor.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import retrofit.Callback;
import com.yjm.doctor.R;

/**
 * Created by zx on 2017/12/5.
 */
public abstract class BaseLoadFragment<T> extends BaseFragment<T> implements SwipeRefreshLayout.OnRefreshListener  {

    protected T mPageData ;
    protected boolean isLoading;

    @Nullable
    @BindView(R.id.swipe_refresh)SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable @BindView(android.R.id.empty)
    protected View mEmptyView;
    @Nullable @BindView(R.id.empty_text) TextView mEmptyText;
    @Nullable @BindView(R.id.empty_load) View mEmptyLoad;
    @Nullable @BindView(R.id.empty_button) Button mEmptyButton;
    @Nullable @BindView(R.id.empty_progressbar) View mEmptyProgressbar;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mPageData == null) {
//            onLoadData();
        }else{
            onInitLoadData(mPageData);
        }
        if(mSwipeRefreshLayout!=null) mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {//刷新数据
        onLoadData();
    }

//    protected abstract void onLoadData();

    protected abstract void onInitLoadData(T pageData);

    protected void stopRefresh(){
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);//停止刷新
        }
    }

    protected void setPageData(T page_data){
        this.mPageData = page_data;
        onInitLoadData(page_data);
    }

    protected void showLoad() {
        if(getActivity() == null) return ;
        showLoad(getString(R.string.string_loading));
    }

    protected void showLoad(String str) {
        if(getActivity() == null) return ;
        if(mPageData != null) return ;
        if (mEmptyView != null) mEmptyView.setVisibility(View.VISIBLE);
        if (mEmptyLoad!=null) mEmptyLoad.setVisibility(View.VISIBLE);
        if (mEmptyProgressbar != null) mEmptyProgressbar.setVisibility(View.VISIBLE);
        if (mEmptyText != null) mEmptyText.setText(R.string.string_loading);
        if (mEmptyButton != null) mEmptyButton.setVisibility(View.GONE);
    }



    protected void showConnectionFail() {

        showConnectionFail(getString(R.string.string_fail));
    }

    protected void showConnectionFail(String str) {
        if(getActivity() == null) return ;
        if (mPageData != null) return ;
        if (mEmptyView != null) mEmptyView.setVisibility(View.VISIBLE);
        if (mEmptyLoad!=null) mEmptyLoad.setVisibility(View.VISIBLE);
        if (mEmptyButton != null) mEmptyButton.setVisibility(View.GONE);
        if (mEmptyText != null) mEmptyText.setText(str);
        if (mEmptyProgressbar != null) mEmptyProgressbar.setVisibility(View.GONE);
    }

    protected void showConnectionRetry() {
        if(getActivity() == null) return ;
        showConnectionRetry(getString(R.string.string_retry));
    }

    protected void showConnectionRetry(String str) {
        if (getActivity() == null) return ;
        if (mPageData != null) return ;
        if (mEmptyLoad!= null) mEmptyLoad.setVisibility(View.GONE);

        if (mEmptyButton != null) {
            mEmptyButton.setText(str);
            mEmptyButton.setVisibility(View.VISIBLE);
            mEmptyButton.setOnClickListener(mOnClickListener);
        }
    }

    protected void hideEmptyView(){
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
    }

    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoad();
            onLoadData();
        }
    };


}
