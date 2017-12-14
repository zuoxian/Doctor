package com.yjm.doctor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.R;
import com.yjm.doctor.ui.MainAccountActivity;
import com.yjm.doctor.ui.BusinessSettingActivity;
import com.yjm.doctor.ui.base.BaseFragment;
import com.yjm.doctor.ui.view.layout.ListLayoutAdapter;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */

public class UserFragment extends BaseFragment {


    @BindView(R.id.user_icon)
    SimpleDraweeView mUserIcon;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.positional_hospital)
    TextView mPositionalHospital;

    @BindView(R.id.listview_layout)
    ListView mListviewLayout;
    ListLayoutAdapter mLayoutAdapter;


    @Override
    protected int getLayoutRes() { return R.layout.fragment_user; }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2017/12/14  这里的图片显示有点错位，图片底部切图空白多大，故增加了 marginTop="5dp"
        List<ListLayoutModel> modelList=new ArrayList<ListLayoutModel>();
        modelList.add(new ListLayoutModel(R.drawable.business_setting,R.string.business_setting,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.patient,R.string.patient,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.account,R.string.account,R.drawable.comein));
        modelList.add(new ListLayoutModel(R.drawable.evaluate,R.string.evaluate,R.drawable.comein));
        mLayoutAdapter=new ListLayoutAdapter(getContext(),modelList);
        mListviewLayout.setAdapter(mLayoutAdapter);
        mLayoutAdapter.setOnListItemOnClickListener(new ListLayoutAdapter.OnListItemOnClickListener() {
            @Override
            public void OnItemClick(int position, ListLayoutModel model) {
                switch (position) {
                    case 0: {
                        startActivity(new Intent(getActivity(), BusinessSettingActivity.class));
                        break;
                    }
                    case 1: {

                        break;
                    }
                    case 2: {
                        startActivity(new Intent(getActivity(), MainAccountActivity.class));
                        break;
                    }
                    case 3: {

                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void onLoadData() {

    }


    @Override
    public void success(Object o, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {

    }


}
