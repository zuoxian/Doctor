package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EaseChatRowText extends EaseChatRow{

	private TextView contentView;

	private EaseImageView imageView;

	private String headerUrl;
	private String myHeaderUrl;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter,String headerUrl,String myHeaderUrl) {
		super(context, message, position, adapter,headerUrl,myHeaderUrl);
		this.headerUrl = headerUrl;
		this.myHeaderUrl = myHeaderUrl;
	}



    @Override
	protected void onInflateView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
				R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
	}

	@Override
	protected void onFindViewById() {
		contentView = (TextView) findViewById(R.id.tv_chatcontent);
		imageView = (EaseImageView)findViewById(R.id.iv_userhead);
	}



    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);

        if(message.direct() == EMMessage.Direct.RECEIVE ) {
            if (!TextUtils.isEmpty(myHeaderUrl)) {
                if(null != imageView){
                    imageView.setImageURI(Uri.parse(myHeaderUrl));
                }

            }
        }else{
            if (!TextUtils.isEmpty(headerUrl)) {
                if(null != imageView){
                    imageView.setImageURI(Uri.parse(myHeaderUrl));
                }
            }
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }
}
