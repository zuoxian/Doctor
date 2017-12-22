package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.ui.adapter.MainAppointmentAdapter;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.fragment.MainAppointmentFragment;
import com.yjm.doctor.ui.fragment.UserPatientsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zx on 2017/12/22.
 */

public class PatientsActivity extends BaseActivity{


    @BindView(R.id.tab)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;


    @Override
    public int initView() {
        return R.layout.activity_main_appointment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.APPOINTMENT_TYPE, Config.APPOINTMENT_MAKE);//未回复
        Fragment fragment1 = new UserPatientsFragment();
        fragment1.setArguments(bundle);
        fragments.add((Fragment)(fragment1));


        MainAppointmentAdapter adapter = new MainAppointmentAdapter(getSupportFragmentManager(), fragments, new String[]{"未回复", "已回复"});
        viewpager.setAdapter(adapter);
        tabLayout.setVisibility(View.GONE);

//        tabLayout.setupWithViewPager(viewpager);

    }

    @Override
    public void finishButton() {

    }

}
