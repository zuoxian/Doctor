package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.ui.adapter.MainAppointmentAdapter;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.fragment.MainAppointmentFragment;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by zx on 2017/12/10.
 */

public class MainAppointmentsActivity extends BaseActivity{

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
        bundle.putString("appointment_make", "appointment_make");//未回复
        Fragment fragment1 = new MainAppointmentFragment();
        fragment1.setArguments(bundle);
        fragments.add((Fragment)(fragment1));
        Bundle bundle1 = new Bundle();
        bundle1.putString("appointment_reply", "appointment_reply");//已回复
        Fragment fragment2 = new MainAppointmentFragment();
        fragment2.setArguments(bundle1);
        fragments.add((Fragment)(fragment2));

        MainAppointmentAdapter adapter = new MainAppointmentAdapter(getSupportFragmentManager(), fragments, new String[]{"未回复", "已回复"});
        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);

    }

    @Override
    public void finishButton() {

    }
}
