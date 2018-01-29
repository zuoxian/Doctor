package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowBigExpression;

/**
 * Created by zhangsong on 17-10-12.
 */

public class EaseChatBigExpressionPresenter extends EaseChatTextPresenter {

    private String headerUrl;
    private String myHeaderUrl;


    public EaseChatBigExpressionPresenter(String headerUrl, String myHeaderUrl) {
        super(headerUrl, myHeaderUrl);
        this.headerUrl = headerUrl;
        this.myHeaderUrl = myHeaderUrl;
    }

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowBigExpression(cxt, message, position, adapter,headerUrl,myHeaderUrl);
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
    }
}
