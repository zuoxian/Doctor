package com.yjm.doctor.model;

import com.hyphenate.chat.EMConversation;

/**
 * Created by zx on 2018/1/30.
 */

public class UserEaseInfo{

    private EMConversation emInof;
    private String headerUrl;
    private String realName;

    public EMConversation getEmInof() {
        return emInof;
    }

    public void setEmInof(EMConversation emInof) {
        this.emInof = emInof;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
