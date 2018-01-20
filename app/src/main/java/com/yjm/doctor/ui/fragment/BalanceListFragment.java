package com.yjm.doctor.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.base.BaseLoadFragment;
import com.yjm.doctor.ui.view.layout.BalanceListAdapter;
import com.yjm.doctor.ui.view.layout.BalanceListBean;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zs on 2017/12/16.
 */

public class BalanceListFragment extends BaseLoadFragment<BalanceListBean> implements BalanceListAdapter.ListAdapterListener{

    private static final String TAG = "BalanceListFragment";

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.empty_progressbar)
    ProgressBar mEmptyProgressbar;
    @BindView(R.id.empty_text)
    TextView mEmptyText;
    @BindView(R.id.empty_load)
    LinearLayout mEmptyLoad;
    @BindView(R.id.empty_button)
    Button mEmptyButton;
    @BindView(android.R.id.empty)
    RelativeLayout mEmpty;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;


    private int mPage = 1;
    private LinearLayoutManager mLayoutManager;
    private BalanceListAdapter mListAdapter;
    private List<BalanceListBean.ObjBean.RowsBean> mListBeans ;

    private UserAPI mUserAPI;
    private User mUser;
    private String tokenID;
    private boolean max = false;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    protected int getLayoutRes() {
        mUserAPI = RestAdapterUtils.getRestAPI(Config.USER_BALANCE, UserAPI.class, getContext(),"");
        return R.layout.layout_load;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListBeans = new ArrayList<BalanceListBean.ObjBean.RowsBean>();
        mListAdapter=new BalanceListAdapter(getContext(),0);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(false);
        mRecyclerView.setAdapter(mListAdapter);
        //这句就是添加我们自定义的分隔线
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mListAdapter.setListener(this);
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
    protected void onLoadData() {
            showLoad();
            if (mPage!=1) {
                if(mProgressbar!=null)mProgressbar.setVisibility(View.VISIBLE);
            }
//            mUser = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
//            tokenID = UserService.getInstance(getContext()).getTokenId(mUser.getId());
            mUserAPI.getBalanceList("2017-11",mPage,10,this);

            isLoading=true;
    }

    @Override
    public void onRefresh() {
        max =false;
        mPage=1;
        onLoadData();
    }

    @Override
    protected void onInitLoadData(BalanceListBean pageData) {
        Log.i(TAG, "初始化加载: "+pageData.toString());
        hideEmptyView();

        if(pageData==null)return ;

        mListBeans = pageData.getObj().getRows();
        if (mPage == 1) {

            if(mListAdapter!=null)mListAdapter.updateItems(mListBeans);
        } else if (mPage > 1 && !mListAdapter.containsAll(mListBeans)) {
            if(mListAdapter!=null)mListAdapter.addItems(mListBeans);
        } else return;
    }



    @Override
    public void success(BalanceListBean bean, Response response) {
        Log.i(TAG, "获取成功: "+ bean.toString());
        stopRefresh();
        if (bean!=null && bean.getSuccess()) {
            if(null == bean.getObj() || null == bean.getObj().getRows() || !(null != bean.getObj().getRows() && bean.getObj().getRows().size()>0)){
                showConnectionRetry("无新消息");
                return;
            }
            if (mPage == 1) {
                setPageData(bean);
            } else {
                onInitLoadData(bean);
            }
            if(!(0 < bean.getObj().getTotal() && bean.getObj().getTotal()<=10) && (mPage * 10)<bean.getObj().getTotal())
                mPage = mPage + 1;
            if((mPage * 10) >= bean.getObj().getTotal()){
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
        Log.i(TAG, "加载失败："+error.getUrl()+"\n"+error.getMessage());
        stopRefresh();
        showConnectionRetry();
        if (mProgressbar != null) mProgressbar.setVisibility(View.GONE);
        isLoading = false;
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        /**
         *
         * @param outRect 边界
         * @param view recyclerView ItemView
         * @param parent recyclerView
         * @param state recycler 内部数据管理
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(0, 0, 0, 5);
        }
    }
}
