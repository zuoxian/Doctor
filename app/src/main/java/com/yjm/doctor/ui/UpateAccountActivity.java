package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.MainAPI;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IAdd;
import com.yjm.doctor.model.Account;
import com.yjm.doctor.model.DataType;
import com.yjm.doctor.model.DataTypeBean;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.layout.ListLayoutModel;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Query;

/**
 * Created by zx on 2017/12/24.
 */

public class UpateAccountActivity extends BaseActivity implements Callback<Message>,IAdd{

    @BindView(R.id.bankAccount)
    EditText bankAccount;
    @BindView(R.id.bankIdNo)
    EditText bankIdNo;
    @BindView(R.id.bankPhone)
    EditText bankPhone;
    @BindView(R.id.bankCode)
    TextView bankCode;
    @BindView(R.id.bankName)
    EditText bankName;
    @BindView(R.id.bankCard)
    EditText bankCard;
    @BindView(R.id.alipay)
    EditText alipay;


    private UserAPI userAPI;

    private MainAPI mainAPI;
    private Account account;


    private List<DataType> dataTypes;

    private String dateTypesId = "";
    private String dateTypeName = "";

    private void getBCs(){
        mainAPI.baseData("BC",new Callback<DataTypeBean>() {
            @Override
            public void success(DataTypeBean levelBean, Response response) {
                closeDialog();

                if(null != levelBean && true == levelBean.getSuccess()){
                    if(null == levelBean.getObj()) {
                        SystemTools.show_msg(UpateAccountActivity.this, R.string.level_fail);
                        return;
                    }
                    dataTypes = levelBean.getObj();
                    if(null != dataTypes && dataTypes.size()>0){
                        dateTypesId = dataTypes.get(0).getId();
                        dateTypeName = dataTypes.get(0).getName();
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                closeDialog();
            }
        });

    }

    @OnClick(R.id.bankCode)
    void bankCodeOnClick(){
        ActivityJumper.getInstance().buttoListJumpTo(this, BCActivity.class, dataTypes);
    }

    @Override
    public int initView() {
        YjmApplication.update = false;
        YjmApplication.tooAdd = true;
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class,this);
        return R.layout.activity_update_account;
    }

//    @Query("bankAccount") String bankAccount,
//    @Query("bankPhone") String bankPhone,
//    @Query("bankIdNo") String bankIdNo,
//    @Query("bankCode") String bankCode,
//    @Query("bankName") String bankName,
//    @Query("bankCard") String bankCard,
//    @Query("alipay") String alipay,

    @Override
    public void finishButton() {
        userAPI.updateAccount(account.getBankAccount(), account.getBankPhone(),account.getBankIdNo() , dateTypesId,account.getBankName(),account.getBankCard(), account.getAlipay(), new Callback<Message>() {
            @Override
            public void success(Message message, Response response) {
                if(null != message && !TextUtils.isEmpty(message.getMsg())){
                    SystemTools.show_msg(UpateAccountActivity.this,message.getMsg());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                SystemTools.show_msg(UpateAccountActivity.this,"更新失败~");
            }
        });
    }


    private int bank_index = 1;
    public void onEventMainThread(EventType event) {
        if(Config.BC_EVENTTYPE.equals(event.getType()) && null != event && null != event.getObject()){

            if (null != bankCode) bankCode.setText(((DataType) event.getObject()).getName());
            dateTypesId = ((DataType) event.getObject()).getId();


        }

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = (Account) getIntent().getSerializableExtra("object");
        mainAPI = RestAdapterUtils.getRestAPI(Config.MAIN_BASEDATA, MainAPI.class,this);
        EventBus.getDefault().register(this);
        getBCs();
        if(null != account) {
            if (null != bankAccount) bankAccount.setText(account.getBankAccount());
            if (null != bankIdNo) bankIdNo.setText(account.getBankIdNo());

            if (null != bankCode) bankCode.setText(TextUtils.isEmpty(account.getBankCode())?dateTypeName:account.getBankCode());
            if (null != bankPhone) bankPhone.setText(account.getBankPhone());
            if (null != bankName) bankName.setText(account.getBankName());
            if (null != bankCard) bankCard.setText(account.getBankCard());
            if (null != alipay) alipay.setText(account.getAlipay());
        }



    }

    @Override
    public void success(Message message, Response response) {
        if(null != message && true == message.getSuccess()){
            SystemTools.show_msg(this,"修改成功");
//            ActivityJumper.getInstance().buttonJumpTo(this,AccountinfoActivity.class);
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
        userAPI.updateAccount(
                (null != bankAccount && null != bankAccount.getText())?bankAccount.getText().toString():null,
                (null != bankIdNo && null != bankIdNo.getText())?bankIdNo.getText().toString():null,
                (null != bankPhone && null != bankPhone.getText())?bankPhone.getText().toString():null,
                (null != bankCode && null != bankCode.getText())?dateTypesId:null,
                (null != bankName && null != bankName.getText())?bankName.getText().toString():null,
                (null != bankCard && null != bankCard.getText())?bankCard.getText().toString():null,
                (null != alipay && null != alipay.getText())?alipay.getText().toString():null,
                this
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
