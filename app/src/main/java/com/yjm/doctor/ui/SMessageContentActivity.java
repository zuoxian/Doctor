package com.yjm.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.api.UserAPI;
import com.yjm.doctor.model.MessageInfo;
import com.yjm.doctor.model.SMessage;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.util.RestAdapterUtils;

import butterknife.BindView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zx on 2018/1/22.
 */

public class SMessageContentActivity extends BaseActivity {

    @BindView(R.id.webkit)
    WebView mWebView;

    private UserAPI userAPI;
    private int id;

    SMessage sMessage;
    @Override
    public int initView() {

        return R.layout.activity_smessage_content;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getIntExtra("id",2);
        userAPI = RestAdapterUtils.getRestAPI(Config.MESSAGE,UserAPI.class,this);
        userAPI.getMessageInfo(id, new Callback<MessageInfo>() {
            @Override
            public void success(MessageInfo sMessageBean, Response response) {
                if(null != sMessageBean && true == sMessageBean.getSuccess() && null != sMessageBean.getObj()){

                    SMessage message = sMessageBean.getObj();

                    StringBuilder sb = new StringBuilder();
                    sb.append("<html>");
                    sb.append("<head>");
                    sb.append("<title> 欢迎您 </title>");
                    sb.append("</head>");
                    sb.append("<body>");
                    if(null != message && !TextUtils.isEmpty(message.getContent())){

                        sb.append(message.getContent());

                    }
                    sb.append("</body>");
                    sb.append("</html>");
                    Log.i("erere",sb.toString());
                    //  加载、并显示HTML代码
                    if(null != mWebView)
                        mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html" , "utf-8", null);

                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Override
    public void finishButton() {

    }
}
