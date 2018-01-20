package com.yjm.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IActivity;
import com.yjm.doctor.model.Banner;
import com.yjm.doctor.model.BannerBean;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.ContactUsActivity;
import com.yjm.doctor.ui.LoginActivity;
import com.yjm.doctor.ui.MainActivity;
import com.yjm.doctor.ui.MainAppointmentsActivity;
import com.yjm.doctor.ui.MainConsultationsActivity;
import com.yjm.doctor.ui.MainConsultationsInfoActivity;
import com.yjm.doctor.ui.MainConversationActivity;
import com.yjm.doctor.ui.base.BaseFragment;
import com.yjm.doctor.ui.view.BannerView;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.Helper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
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

    @BindView(R.id.bar_num3)
    TextView barNum3;

    private User user;
    private SharedPreferencesUtil sharedPreferencesUtil;

    @OnClick(R.id.toolfinish)
    void logout(){
        if(null != getActivity()) {

            UserService.getInstance(getActivity()).logout();
            sharedPreferencesUtil.del("user");
            ActivityJumper.getInstance().buttonIntJumpTo(getActivity(), LoginActivity.class, 1);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {

            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
//			for (EMMessage message : messages) {
//				EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//				final String action = cmdMsgBody.action();//获取自定义action
//				if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//					RedPacketUtil.receiveRedPacketAckMessage(message);
//				}
//			}
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };



    public void onEventMainThread(EventType type){

        if(Config.MESSAGE_NUM.equals(type.getType())){
            refreshUIWithMessage();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mian;
    }


    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        if(null != getActivity()) {
            sharedPreferencesUtil = SharedPreferencesUtil.instance(getActivity());
        }
        if(null != mIscheck && null != getActivity()){
            try {
                user = (User) sharedPreferencesUtil.deSerialization(sharedPreferencesUtil.getObject("user"));
            }catch (Exception e){
                Log.d("serial", "share2   ="+e.getMessage());
            }

            if(null != user && 2 == user.getStatus())
                mIscheck.setVisibility(View.VISIBLE);
            else
                mIscheck.setVisibility(View.GONE);
        }
        if(null != mTooltitle)mTooltitle.setText("医家盟");
        if(null != mToolicon)mToolicon.setVisibility(View.GONE);
        if(null != mToolFinish)mToolFinish.setText("退出登录");
        updateUnreadLabel();
    }

    private void refreshUIWithMessage() {
        if(null == getActivity()) return;
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();

            }
        });
    }

    public void updateUnreadLabel() {
        if(null == barNum3)return;
        int count = getUnreadMsgCountTotal();

        if (count > 0) {
            barNum3.setText(String.valueOf(count));
            barNum3.setVisibility(View.VISIBLE);
        } else {
            barNum3.setVisibility(View.INVISIBLE);
        }
    }

    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    @Override
    protected void onLoadData() {

        if(null != getActivity())
            mainAPI = RestAdapterUtils.getRestAPI(Config.HOME_BANNERS,MainAPI.class,getActivity(),"");

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
        if (null != getActivity()) {
            if (null != user) {
                if (2 == user.getStatus()) {
                    SystemTools.show_msg(getActivity(), "账号审核中，请等待~");
                } else {

                    ActivityJumper.getInstance().buttonJumpTo(getActivity(), MainAppointmentsActivity.class);
                }
            }else{
                SystemTools.show_msg(getActivity(), "加载异常，请重试~");
            }

        }
    }

    @OnClick(R.id.main_image_text_consulting)
    void image_text_consulting(){
        if(null != getActivity()) {
            if (null != user) {
                if (2 == user.getStatus()) {
                    SystemTools.show_msg(getActivity(), "账号审核中，请等待~");
                } else {
                    ActivityJumper.getInstance().buttonJumpTo(getActivity(), MainConversationActivity.class);
                }
            }else{
                SystemTools.show_msg(getActivity(), "加载异常，请重试~");
            }
        }
//        ActivityJumper.getInstance().buttonJumpTo(getActivity(), MainConsultationsActivity.class);
    }

    @OnClick(R.id.main_consulting_statistics)
    void consulting_statistics(){

    }

    @OnClick(R.id.main_information_service)
    void information_service(){
        if (null != user) {
        if ( 2 == user.getStatus()) {
            SystemTools.show_msg(getActivity(), "账号审核中，请等待~");
        } else {
            EventBus.getDefault().post(new EventType(Config.MAIN_SERVICE_TYPE, Config.MAIN_SERVICE_TYPE_VALUE));
        } }else{
            SystemTools.show_msg(getActivity(), "加载异常，请重试~");
        }

    }


    @OnClick(R.id.main_contact_us)
    void contact_us(){
        if(null != getActivity())
        ActivityJumper.getInstance().buttonJumpTo(getActivity(), ContactUsActivity.class);
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
