package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.ui.adapter.MainAppointmentAdapter;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.fragment.MainAppointmentFragment;
import com.yjm.doctor.ui.fragment.MainConsultationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zx on 2017/12/10.
 */

public class MainConsultationsActivity extends BaseActivity{

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

        Log.i("EMClient",EMClient.getInstance().isLoggedInBefore()+"");
        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.APPOINTMENT_TYPE, Config.APPOINTMENT_MAKE);//未回复
        Fragment fragment1 = new EaseConversationListFragment();
        fragment1.setArguments(bundle);
        Bundle bundle1 = new Bundle();
        bundle.putInt(Config.APPOINTMENT_TYPE, Config.APPOINTMENT_REPLY);//已回复
        Fragment fragment2 = new MainConsultationFragment();
        fragment2.setArguments(bundle1);

        fragments.add((Fragment)(fragment1));
        fragments.add((Fragment)(fragment2));

        MainAppointmentAdapter adapter = new MainAppointmentAdapter(getSupportFragmentManager(), fragments, new String[]{"未回复", "已回复"});
        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);

    }

    @Override
    public void finishButton() {

    }
}
