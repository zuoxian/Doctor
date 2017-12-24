package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.application.baseInterface.IAdd;
import com.yjm.doctor.model.Account;
import com.yjm.doctor.model.Message;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.ActivityJumper;
import com.yjm.doctor.util.RestAdapterUtils;
import com.yjm.doctor.util.SystemTools;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    private Account account;



    @OnClick(R.id.bankCode)
    void bankCodeOnClick(){

    }

    @Override
    public int initView() {
        YjmApplication.update = false;
        YjmApplication.tooAdd = true;
        userAPI = RestAdapterUtils.getRestAPI(Config.USER_BUSINESSSETTING,UserAPI.class);
        return R.layout.activity_update_account;
    }

    @Override
    public void finishButton() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = (Account) getIntent().getSerializableExtra("object");

        if(null != account) {
            if (null != bankAccount) bankAccount.setText(account.getBankAccount());
            if (null != bankIdNo) bankIdNo.setText(account.getBankIdNo());
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
        userAPI.updateAccount(
                (null != bankAccount && null != bankAccount.getText())?bankAccount.getText().toString():null,
                (null != bankIdNo && null != bankIdNo.getText())?bankIdNo.getText().toString():null,
                (null != bankPhone && null != bankPhone.getText())?bankPhone.getText().toString():null,
                (null != bankCode && null != bankCode.getText())?bankCode.getText().toString():null,
                (null != bankName && null != bankName.getText())?bankName.getText().toString():null,
                (null != bankCard && null != bankCard.getText())?bankCard.getText().toString():null,
                (null != alipay && null != alipay.getText())?alipay.getText().toString():null,
                this
        );
    }
}
