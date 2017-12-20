package com.yjm.doctor.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IActivity;
import com.yjm.doctor.model.Banner;
import com.yjm.doctor.model.BannerBean;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.MainAppointmentsActivity;
import com.yjm.doctor.ui.MainConsultationsActivity;
import com.yjm.doctor.ui.MainConsultationsInfoActivity;
import com.yjm.doctor.ui.base.BaseFragment;
import com.yjm.doctor.ui.view.BannerView;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/10.
 */

public class MainFragment extends BaseFragment<BannerBean> implements IActivity{

    @BindView(R.id.tooltitle)
    TextView mTooltitle;

    private MainAPI mainAPI;

    @BindView(R.id.bannerView)
    BannerView bannerView;

    @BindView(R.id.toolicon)
    RelativeLayout mToolicon;

    @BindView(R.id.toolfinish)
    TextView mToolFinish;

    @BindView(R.id.ischeck)
    TextView mIscheck;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mian;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(null != mTooltitle)mTooltitle.setText("医家盟");
        if(null != mToolicon)mToolicon.setVisibility(View.GONE);
        if(null != mToolFinish)mToolFinish.setVisibility(View.GONE);
    }

    @Override
    protected void onLoadData() {
        if(null != mIscheck){
            User user = UserService.getInstance().getActiveAccountInfo();
            if(null != user && 2 == user.getStatus())
                mIscheck.setVisibility(View.VISIBLE);
        }
        if(null != getActivity())
            mainAPI = RestAdapterUtils.getRestAPI(Config.HOME_BANNERS,MainAPI.class,getActivity());

        if(null != mainAPI && null != getActivity()){
            if(NetworkUtils.isNetworkAvaliable(getActivity())){
                mainAPI.banner(Config.DEFAULT_TOKENID,Config.SOURCE,this);
            }else{
                SystemTools.show_msg(getActivity(), R.string.toast_msg_no_network);
            }

        }
    }

    @Override
    public void success(BannerBean pageData, Response response) {
        if(null != pageData && true == pageData.getSuccess()) {
            if(null == pageData.getObj()){
                SystemTools.show_msg(getActivity(), pageData.getMsg());
                return;
            }
            List<Banner> banners= pageData.getObj();
            if(null != bannerView) bannerView.setData(banners);
        }else{
            if(TextUtils.isEmpty(pageData.getMsg())) {
                SystemTools.show_msg(getActivity(),R.string.login_fail_info);
            }else{
                SystemTools.show_msg(getActivity(), pageData.getMsg());
            }
        }
    }

    @OnClick(R.id.main_appointments)
    void appointments(){
        if(null != getActivity())
            ActivityJumper.getInstance().buttonJumpTo(getActivity(), MainAppointmentsActivity.class);
    }

    @OnClick(R.id.main_image_text_consulting)
    void image_text_consulting(){
        ActivityJumper.getInstance().buttonJumpTo(getActivity(), MainConsultationsActivity.class);
    }

    @OnClick(R.id.main_consulting_statistics)
    void consulting_statistics(){

    }

    @OnClick(R.id.main_information_service)
    void information_service(){
        EventBus.getDefault().post(new EventType(Config.MAIN_SERVICE_TYPE, Config.MAIN_SERVICE_TYPE_VALUE));

    }


    @OnClick(R.id.main_contact_us)
    void contact_us(){

    }

    @Override
    public void failure(RetrofitError error) {

    }

    @Override
    public int initView() {
        return 0;
    }

    @Override
    public void finishButton() {

    }
}
