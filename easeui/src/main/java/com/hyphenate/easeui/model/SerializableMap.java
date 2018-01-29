package com.hyphenate.easeui.model;

import com.hyphenate.chat.EMConversation;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zx on 2018/1/29.
 */

public class SerializableMap implements Serializable{
    private static final long serialVersionUID = 8178028470078938059L;

    Map<String, EMConversation> conversations;
//    String headerUrl;

    public Map<String, EMConversation> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, EMConversation> conversations) {
        this.conversations = conversations;
    }
//
//    public String getHeaderUrl() {
//        return headerUrl;
//    }
//
//    public void setHeaderUrl(String headerUrl) {
//        this.headerUrl = headerUrl;
//    }
}
