package com.yjm.doctor.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IAdd;
import com.yjm.doctor.model.Account;
import com.yjm.doctor.model.DepartMent;
import com.yjm.doctor.model.DepartMentBean;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Hospital;
import com.yjm.doctor.model.HospitalBean;
import com.yjm.doctor.model.Level;
import com.yjm.doctor.model.LevelBean;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserBean;
import com.yjm.doctor.model.UserConfigBean;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.CustomDatePicker;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.NetworkUtils;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SharedPreferencesUtil;
import com.yjm.doctor.util.SystemTools;
import com.yjm.doctor.util.UploadImageUtils;
import com.yjm.doctor.util.auth.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by zx on 2017/12/24.
 */

public class UpateUserActivity extends BaseActivity implements Callback<Message>,IAdd, View.OnClickListener{

    @BindView(R.id.iv_image)
    SimpleDraweeView headImageFile;
    @BindView(R.id.realName)
    EditText realName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.department)
    TextView department;

    @BindView(R.id.des)
    EditText des;

    @BindView(R.id.level)
    TextView level;

    @BindView(R.id.male)
    RadioButton male;

    @BindView(R.id.femle)
    RadioButton femle;

    @BindView(R.id.speciality)
    EditText speciality;

    @BindView(R.id.rg)
    RadioGroup rg;

    private UserAPI userAPI;

    private User user;

//    private ImageView mImage;

    private Hospital hospital1;
    private DepartMent departMent;
    private Level level2;

    private RelativeLayout selectDate;
    private TextView currentDate;
    private CustomDatePicker customDatePicker1, customDatePicker2;



    @Override
    public int initView() {
        YjmApplication.tooAdd = true;
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class,this);
        return R.layout.activity_update_user_info;
    }

    @Override
    public void finishButton() {

    }

    private Level level1;

    private UserAPI levelAPI;
    private UserAPI userAPI1;

    private UserAPI userAPIHospital;

    private String date;

    private SharedPreferencesUtil sharedPreferencesUtil = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        sharedPreferencesUtil = SharedPreferencesUtil.instance(this);
        user = (User) getIntent().getSerializableExtra("object");
        level1 = new Level(1480562081846l,0,"0",1482214042373l,"无职位");
        levelAPI = RestAdapterUtils.getRestAPI(Config.USER_LEVELS_API,UserAPI.class,this,"");
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API,UserAPI.class,this,"");
        userAPI1 = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class,this,"");
        userAPIHospital = RestAdapterUtils.getRestAPI(Config.DOCTORSERVICE,UserAPI.class,this);
        if(null != user) {
            if (null != headImageFile) headImageFile.setImageURI(Uri.parse(user.getPicUrl()));;
            if (null != realName) realName.setText(null == user.getCustomer() ? "": user.getCustomer().getRealName());
            if (null != currentDate) currentDate.setText(null == user.getCustomer() ? "": user.getCustomer().getBirthdayStr());
            if (null != email) email.setText(user.getEmail());
            if (null != speciality) speciality.setText(null == user.getMemberDoctor()? "":user.getMemberDoctor().getSpeciality());
            if (null != des) des.setText(null == user.getMemberDoctor()? "":user.getMemberDoctor().getIntroduce());
            if(null != user.getCustomer()){
                if(1 == user.getCustomer().getSex()){
                    male.setChecked(true);
                }else{
                    femle.setChecked(true);
                }
            }

            if(null != user.getMemberDoctor()) {
                if (null != level) level.setText(TextUtils.isEmpty(user.getMemberDoctor().getLevelName())?"职称":user.getMemberDoctor().getLevelName());
                if (null != hospital) hospital.setText(TextUtils.isEmpty(user.getMemberDoctor().getHospitalName())?"医院":user.getMemberDoctor().getHospitalName());
                if(null != department) department.setText(TextUtils.isEmpty(user.getMemberDoctor().getDepartmentName())?"科室":user.getMemberDoctor().getDepartmentName());
            }else{
                if (null != level) level.setText("职称");
                if (null != hospital) hospital.setText("医院");
                if(null != department) department.setText("科室");
            }

        }

        if(null != rg){
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.male){
                        sex = 1;
                    }else{
                        sex = 2;
                    }
                }
            });
        }

        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        initDatePicker();
    }

    @OnClick(R.id.hospital)
    void hospital(){
        if (null != hospital) {

            getHospitals();
        }
    }

    @OnClick(R.id.department)
    void department(){
        if (null != department){
            getDepartments();
        }
    }

    @OnClick(R.id.level)
    void level(){
        if (null != level){
            if(NetworkUtils.isNetworkAvaliable(this)){
                showDialog("提取级别中~");
                levelAPI.getLevels(new Callback<LevelBean>() {
                    @Override
                    public void success(LevelBean levelBean, Response response) {
                        closeDialog();
                        if(null != levelBean && true == levelBean.getSuccess()){
                            if(null == levelBean.getObj()) {
                                SystemTools.show_msg(UpateUserActivity.this, R.string.level_fail);
                                return;
                            }



                            List<Level> levelList = levelBean.getObj();
                            levelList.add(level1);
                            Collections.sort(levelList);
                            ActivityJumper.getInstance().buttoListJumpTo(UpateUserActivity.this,LevelActivity.class,levelList);
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
    }

    @OnClick(R.id.iv_image)
    void updateImage(){
        if(null != headImageFile){
            showChoosePicDialog();
        }
    }

    private int sex;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                date = currentDate.getText().toString();
                break;

        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        if(null != user && null != user.getCustomer()&& !TextUtils.isEmpty(user.getCustomer().getBirthdayStr())){
            currentDate.setText(user.getCustomer().getBirthdayStr());
        }else {
            currentDate.setText(now.split(" ")[0]);
        }
        date = now.split(" ")[0];

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        },"1400-01-01 00:00" , now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动


    }

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private String outputAvatarPath = "";
    private static final int CROP_SMALL_PICTURE = 2;
    /**
     * 显示修改图片的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpateUserActivity.this);
        builder.setTitle("修改头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, IMG_FROM_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        outputAvatarPath = UploadImageUtils.getAvatarPath(UpateUserActivity.this);
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(outputAvatarPath));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, IMG_FROM_CAMERA);
                        break;
                }
            }
        });
        builder.show();
    }

    public static final int IMG_FROM_PICTURE = 0x1001;
    public static final int IMG_FROM_CAMERA = 0x1002;
    private File file;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMG_FROM_CAMERA:
                    new WriteToSdcardTask(false,null).execute();
                    break;
                case IMG_FROM_PICTURE:
                    Uri uri = data.getData();
                    new WriteToSdcardTask(true,uri).execute();
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                default:
                    break;
            }
        }
    }


    private TypedFile typedFile ;

    public class WriteToSdcardTask extends AsyncTask {
        private boolean isUri;
        private Uri uri;

        public WriteToSdcardTask(boolean isUri,Uri uri) {
            this.isUri = isUri;
            this.uri = uri;
//            showDialog();
        }

        @Override
        protected String doInBackground(Object... params) {
            String path = "";
            try {
                if(isUri){
                    path = UploadImageUtils.writeUriToSDcard(UpateUserActivity.this, uri);
                }else{
                    path = UploadImageUtils.writeStringToSDcard(UpateUserActivity.this, outputAvatarPath);
                }

            } catch (IOException e) {
                SystemTools.show_msg(UpateUserActivity.this, "对不起,无法找到此图片!");
            }
            return path;
        }

        @Override
        protected void onPostExecute(Object result) {
            String path = (String) result;
            if(!TextUtils.isEmpty(path)){
                submitUserIcon(new File(path));
            }else{
                SystemTools.show_msg(UpateUserActivity.this, "对不起,无法找到此图片!");
            }
        }

        public void submitUserIcon(final File file){
            typedFile = new TypedFile("application/octet-stream",file);
            userAPI1.updateUserInfo( typedFile,
                    new Callback<UserConfigBean>() {
                        @Override
                        public void success(UserConfigBean userBean, Response response) {
                            if(null != userBean && true == userBean.getSuccess() && null != userBean.getObj()) {
                                Log.e("log1", "update success");
                                if (null != headImageFile && !TextUtils.isEmpty(userBean.getObj().getPics())) {
                                    headImageFile.setImageURI(Uri.parse(userBean.getObj().getPics()));
                                    if(null != user){
                                        if(null != headImageFile)headImageFile.setImageURI(Uri.parse(userBean.getObj().getPics()));//显示图片
                                        user.setHeadImage(userBean.getObj().getPics());
                                        user.setPicUrl(userBean.getObj().getPics());
                                        user.setStatus(2);
                                        try {
                                            sharedPreferencesUtil.saveObject("user",sharedPreferencesUtil.serialize(user));
                                        }catch (Exception e){
                                            Log.e("error",e.getMessage());
                                        }


                                    }
                                    SystemTools.show_msg(UpateUserActivity.this,"头像修改成功~");
                                }else {
                                    SystemTools.show_msg(UpateUserActivity.this, "图片上传失败，请重传~");
                                }
                            }else{
                                SystemTools.show_msg(UpateUserActivity.this,"图片上传失败，请重传~");
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("log1","update fail");
                            SystemTools.show_msg(UpateUserActivity.this,"图片上传失败，请重传~");

                            if(null != error && error.getMessage().contains("path $.obj")){

                                final UserService userService = UserService.getInstance(UpateUserActivity.this);
                                final User user = userService.getActiveAccountInfo();
                                userAPI.login(user.getMobile(),userService.getPwd(user.getId()),2,new Callback<UserBean>(){

                                    @Override
                                    public void success(UserBean userBean, Response response) {
                                        if(null != userBean && null != userBean.getObj() && !TextUtils.isEmpty(userBean.getObj().getTokenId())){
                                            userService.setTokenId(user.getId(),userBean.getObj().getTokenId());
                                            submitUserIcon(file);
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });

                            }
                        }
                    }

            );
        }
    }






    private Bitmap mBitmap;
    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            if(null != headImageFile)headImageFile.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码，后期将单独写一篇这方面的博客，敬请期待...

//            saveBitmapFile(mBitmap);
        }
    }

    public void saveBitmapFile(Bitmap bitmap){
        file=new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    private List<Hospital> hospitalList;

    private List<DepartMent> departMentList;

    private void getHospitals(){
        showDialog("提取中~");
        userAPIHospital.hospitalList(new Callback<HospitalBean>() {
            @Override
            public void success(HospitalBean levelBean, Response response) {
                closeDialog();

                if(null != levelBean && true == levelBean.getSuccess()){
                    if(null == levelBean.getObj()) {
                        SystemTools.show_msg(UpateUserActivity.this, R.string.level_fail);
                        return;
                    }

                    hospitalList = levelBean.getObj();
                    if(null != hospitalList && 0 < hospitalList.size()) {
                        ActivityJumper.getInstance().buttoListJumpTo(UpateUserActivity.this, HospitalActivity.class, hospitalList);
                    }else{
//                        showDialog("正在加载~");
//                        getHospitals();
                        SystemTools.show_msg(UpateUserActivity.this,"提取失败，请重试~");
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                closeDialog();
                SystemTools.show_msg(UpateUserActivity.this,"提取失败，请重试~");
            }
        });
    }

    private void getDepartments(){
        showDialog("提取中~");
        userAPIHospital.departmentList(new Callback<DepartMentBean>() {
            @Override
            public void success(DepartMentBean levelBean, Response response) {
                closeDialog();
                if(null != levelBean && true == levelBean.getSuccess()){
                    if(null == levelBean.getObj()) {
                        SystemTools.show_msg(UpateUserActivity.this, R.string.level_fail);
                        return;
                    }

//                        Hospital hospital = new Hospital(1480562081846l,0,"0",1482214042373l,"无职位");

                    departMentList = levelBean.getObj();
                    if(null != departMentList && 0 < departMentList.size()) {

                        ActivityJumper.getInstance().buttoListJumpTo(UpateUserActivity.this, DepartmentActivity.class, departMentList);
                    }else{
                        showDialog("正在加载~");
                        getDepartments();
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
    public void success(Message message, Response response) {
        if(null != message && true == message.getSuccess()){
            SystemTools.show_msg(this,"修改成功");
            ActivityJumper.getInstance().buttonJumpTo(this,AccountinfoActivity.class);
            finish();
        }else {
            SystemTools.show_msg(this,"修改失败");
        }
    }

    @Override
    public void failure(RetrofitError error) {
        SystemTools.show_msg(this,"修改失败");
    }



    @Override
    public void add() {
//        Log.e("date  --", currentDate.getText().toString());
            userAPI1.updateUserInfo(
                    (null != realName && null != realName.getText()) ? realName.getText().toString() : "",
                    (null != currentDate && null != currentDate.getText()) ? currentDate.getText().toString() : "",
                    sex,
                    (null != email && null != email.getText()) ? email.getText().toString() : "",
                    (null != hospital1) ? hospital1.getId() : (null != user.getMemberDoctor())?user.getMemberDoctor().getHospital():0,

                    (null != departMent) ? departMent.getId() : (null != user.getMemberDoctor())?user.getMemberDoctor().getDepartment():0,
                    (null != level2) ? level2.getId() : (null != user.getMemberDoctor())?user.getMemberDoctor().getLevel():0,
                    (null != speciality && null != speciality.getText()) ? speciality.getText().toString() : "",
                    (null != des && null != des.getText()) ? des.getText().toString() : "",
                    new Callback<UserConfigBean>() {
                        @Override
                        public void success(UserConfigBean user1, Response response) {

                            try {
                                if(null != user1 && true == user1.getSuccess() && null != user1.getObj()){
                                    if(null != user) {
                                        user.setStatus(2);
                                        if (null != user.getCustomer()){
                                            user.getCustomer().setRealName(user1.getObj().getRealName());
                                            user.getCustomer().setSex(sex);
                                            Log.e("eerrr",user1.getObj().getBirthdayStr());
                                            user.getCustomer().setBirthdayStr(user1.getObj().getBirthdayStr());
                                        }
                                        user.setEmail(user1.getObj().getEmail());
                                        if(null != user.getMemberDoctor()) {
                                            if(null != hospital1) {
                                                user.getMemberDoctor().setHospital(hospital1.getId());
                                                user.getMemberDoctor().setHospitalName(hospital1.getHospitalName());

                                            }
                                            if(null != departMent) {
                                                user.getMemberDoctor().setDepartment(departMent.getId());
                                                user.getMemberDoctor().setDepartmentName(departMent.getName());
                                            }
                                            if(null != level2) {
                                                user.getMemberDoctor().setLevel(level2.getId());
                                                user.getMemberDoctor().setLevelName(TextUtils.isEmpty(level2.getName())?"无职称":level2.getName());
                                                user.getMemberDoctor().setSpeciality(user1.getObj().getSpeciality());
                                            }
                                            if(!TextUtils.isEmpty(user1.getObj().getIntroduce())){
                                                user.getMemberDoctor().setIntroduce(user1.getObj().getIntroduce());
                                            }
                                        }
                                    }
                                    sharedPreferencesUtil.saveObject("user",sharedPreferencesUtil.serialize(user));
                                    SystemTools.show_msg(UpateUserActivity.this,"添加成功，等待审核~");
                                    finish();
                                }else{
                                    SystemTools.show_msg(UpateUserActivity.this,"添加失败，请重试");
                                }
                            }catch (Exception e){
                                Log.e("log",e.getMessage());
                                SystemTools.show_msg(UpateUserActivity.this,"添加失败，请重试");
                            }

                            Log.e("log1","update success");
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("log1","update fail");
                            SystemTools.show_msg(UpateUserActivity.this,"添加失败，请重试");

                        }
                    }
            );
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




    public void onEventMainThread(EventType event) {

        if(Config.LEVEL_EVENTTYPE.equals(event.getType())){
            if(null == level){
                return;
            }
            if(null == event.getObject())
                return;
            level2 =(Level) event.getObject();

            level.setText(level2.getName());
        }
        if(Config.HOSPITAL_EVENTTYPE.equals(event.getType())){
            if(null == hospital){
                return;
            }
            if(null == event.getObject())
                return;
            hospital1 =(Hospital) event.getObject();

            hospital.setText(hospital1.getHospitalName());
        }
        if(Config.DEPARTMENT_EVENTTYPE.equals(event.getType())){
            if(null == department){
                return;
            }
            if(null == event.getObject())
                return;
            departMent =(DepartMent) event.getObject();

            department.setText(departMent.getName());
        }
    }
}
