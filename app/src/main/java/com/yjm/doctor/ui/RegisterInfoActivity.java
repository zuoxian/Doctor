package com.yjm.doctor.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.DepartMent;
import com.yjm.doctor.model.DepartMentBean;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Hospital;
import com.yjm.doctor.model.HospitalBean;
import com.yjm.doctor.model.Level;
import com.yjm.doctor.model.LevelBean;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.ObjectCheck;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.auth.UserService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2017/12/8.
 */

public class RegisterInfoActivity extends BaseActivity implements Callback<UserBean>{

    private UserAPI userAPI;

    private UserAPI levelAPI;

    private Level level;

    @BindView(R.id.positionalname)
    TextView mPositionalName;

    @BindView(R.id.name)
    EditText mName;

    @BindView(R.id.hospitalname)
    TextView mHospitalName;

    @BindView(R.id.departmentname)
    TextView mDepartmentName;

    private User userBasicInfo ;

    private UserAPI userAPIHospital;

    private List<Hospital> hospitalList;

    private List<DepartMent> departMentList;

    private int hospitalId = 0;
    private int departmentId = 0;
    private int levelId = 0;


    @Override
    public int initView() {
        return R.layout.activity_register_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        level = new Level(1480562081846l,0,"0",1482214042373l,"无职位");

        levelAPI = RestAdapterUtils.getRestAPI(Config.USER_LEVELS_API,UserAPI.class,this,"");
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API,UserAPI.class,this,"");
        userAPIHospital = RestAdapterUtils.getRestAPI(Config.DOCTORSERVICE,UserAPI.class,this);
        EventBus.getDefault().register(this);


        getHospitals();
        getDepartments();

    }

    private void getHospitals(){
        userAPIHospital.hospitalList(new Callback<HospitalBean>() {
            @Override
            public void success(HospitalBean levelBean, Response response) {
                closeDialog();

                if(null != levelBean && true == levelBean.getSuccess()){
                    if(null == levelBean.getObj()) {
                        SystemTools.show_msg(RegisterInfoActivity.this, R.string.level_fail);
                        return;
                    }

//                        Hospital hospital = new Hospital(1480562081846l,0,"0",1482214042373l,"无职位");

                    hospitalList = levelBean.getObj();
//                        levelList.add(hospital);
                    if(null != hospitalList && 0 < hospitalList.size()) {
                        Collections.sort(hospitalList);
                        if(null != mHospitalName && !TextUtils.isEmpty(hospitalList.get(0).getHospitalName())){
                            mHospitalName.setText(hospitalList.get(0).getHospitalName());
                            hospitalId = hospitalList.get(0).getId();
                        }
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                closeDialog();
            }
        });
    }

    private void getDepartments(){
        userAPIHospital.departmentList(new Callback<DepartMentBean>() {
            @Override
            public void success(DepartMentBean levelBean, Response response) {
                closeDialog();
                if(null != levelBean && true == levelBean.getSuccess()){
                    if(null == levelBean.getObj()) {
                        SystemTools.show_msg(RegisterInfoActivity.this, R.string.level_fail);
                        return;
                    }

//                        Hospital hospital = new Hospital(1480562081846l,0,"0",1482214042373l,"无职位");

                    departMentList = levelBean.getObj();
//                        levelList.add(hospital);
                    if(null != departMentList && 0 < departMentList.size()) {
                        Collections.sort(departMentList);
                        if(null != mDepartmentName && !TextUtils.isEmpty(departMentList.get(0).getName())){
                            mDepartmentName.setText(departMentList.get(0).getName());
                            departmentId = departMentList.get(0).getId();
                        }
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                closeDialog();
            }
        });
    }

    @Override
    public void finishButton() {

    }

    private Hospital hospital;
    private DepartMent departMent ;


    public void onEventMainThread(EventType event) {
        if(Config.LEVEL_EVENTTYPE.equals(event.getType())){
            if(null == mPositionalName){
                return;
            }
            if(null == event.getObject())
                return;
            level =(Level) event.getObject();

            mPositionalName.setText(level.getName());
        }
        if(Config.HOSPITAL_EVENTTYPE.equals(event.getType())){
            if(null == mHospitalName){
                return;
            }
            if(null == event.getObject())
                return;
            hospital =(Hospital) event.getObject();

            mHospitalName.setText(hospital.getHospitalName());
        }
        if(Config.DEPARTMENT_EVENTTYPE.equals(event.getType())){
            if(null == mDepartmentName){
                return;
            }
            if(null == event.getObject())
                return;
            departMent =(DepartMent) event.getObject();

            mDepartmentName.setText(departMent.getName());
        }
    }

    @OnClick(R.id.department)
    void getDepartment(){
        if(NetworkUtils.isNetworkAvaliable(this)){
            if(null != departMentList && 0 < departMentList.size()) {

                ActivityJumper.getInstance().buttoListJumpTo(RegisterInfoActivity.this, DepartmentActivity.class, departMentList);
            }else{
                showDialog("正在加载~");
                getDepartments();
            }

        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    @OnClick(R.id.hospital)
    void getHospital(){
        if(NetworkUtils.isNetworkAvaliable(this)){
            if(null != hospitalList && 0 < hospitalList.size()) {

                ActivityJumper.getInstance().buttoListJumpTo(RegisterInfoActivity.this, HospitalActivity.class, hospitalList);
            }else{
                showDialog("正在加载~");
                getHospitals();
            }

        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    @OnClick(R.id.positional)
    void getLevels(){
        if(NetworkUtils.isNetworkAvaliable(this)){
            showDialog("提取级别中~");
            levelAPI.getLevels(new Callback<LevelBean>() {
                @Override
                public void success(LevelBean levelBean, Response response) {
                    closeDialog();
                    if(null != levelBean && true == levelBean.getSuccess()){
                        if(null == levelBean.getObj()) {
                            SystemTools.show_msg(RegisterInfoActivity.this, R.string.level_fail);
                            return;
                        }



                        List<Level> levelList = levelBean.getObj();
                        levelList.add(level);
                        Collections.sort(levelList);
                        ActivityJumper.getInstance().buttoListJumpTo(RegisterInfoActivity.this,LevelActivity.class,levelList);
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    closeDialog();
                }
            });
        }else{
            SystemTools.show_msg(this, R.string.toast_msg_no_network);
        }
    }

    @OnClick(R.id.register)
    void register(){
        if(null == mName || null == mHospitalName || null == mDepartmentName) {
            SystemTools.show_msg(this,R.string.register_fail);

            return;
        }
        if(TextUtils.isEmpty(mName.getText())) {
            mName.setError("请输入真实姓名");
            mName.findFocus();
            return;
        }
        if(TextUtils.isEmpty(mHospitalName.getText())) {
            mHospitalName.setError("请输入医院名称");
            mHospitalName.findFocus();
            return;
        }
        if(TextUtils.isEmpty(mDepartmentName.getText())) {
            mDepartmentName.setError("请输入科室名");
            mDepartmentName.findFocus();
            return;
        }


        userBasicInfo = (User) this.getIntent().getSerializableExtra("object");
        if(null == userBasicInfo){
            userBasicInfo = UserService.getInstance(this).getActiveAccountInfo();
        }


        if(null !=hospital){
            hospitalId = hospital.getId();
        }

        if(null != departMent){
            departmentId = departMent.getId();
        }

        if(null != level){
            levelId = level.getId();
        }
        userAPI.addDoctorInfo(userBasicInfo.getId(),mName.getText().toString(),hospitalId,departmentId,levelId,this);
        showDialog("正在注册中~");
    }
//    finishLogin(user);
    @Override
    public void success(UserBean userBean, Response response) {
        closeDialog();
        if (null != userBean && true == userBean.getSuccess()) {
            if (null == userBean.getObj()) {
                SystemTools.show_msg(this, userBean.getMsg());
                return;
            }
            User user = userBean.getObj();

            finishLogin(user);
            ActivityJumper.getInstance().buttonJumpTo(this,ToExamineActivity.class);
        } else {
            if (TextUtils.isEmpty(userBean.getMsg())) {
                SystemTools.show_msg(this, R.string.register2_fail_info);
            } else {
                SystemTools.show_msg(this, userBean.getMsg());
            }
        }
    }

    private void finishLogin(User user) {
        UserService.getInstance(this).signIn(user.getUsername(), userBasicInfo.getPwd(), user);
        UserService.getInstance(this).setPwd(userBasicInfo.getId(), userBasicInfo.getPwd());
        if(!TextUtils.isEmpty(user.getTokenId())) {
            if(!TextUtils.isEmpty(user.getTokenId()))
                UserService.getInstance(this).setTokenId(user.getId(),user.getTokenId());

        }
        Config.userId = user.getId();
        Config.mobile = user.getMobile();
        setResult(11);

        if (!(EMClient.getInstance().isLoggedInBefore())){
            EMClient.getInstance().login("2-" + user.getMobile(), user.getHxPassword(), new EMCallBack() {
                @Override
                public void onSuccess() {

                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Log.i("EMClient", "login is success");
                }

                @Override
                public void onError(int i, String s) {
                    Log.i("EMClient", "login error i=" + i + ",s=" + s);
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.i("EMClient", "login onProgress i=" + i + ",s=" + s);
                }
            });
        }
    }

    @Override
    public void failure(RetrofitError error) {
        closeDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
