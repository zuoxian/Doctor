package com.hyphenate.easeui.model;

/**
 * Created by zx on 2017/12/9.
 */

public class EventType {

    private String type;

    private Object object;

    public EventType(String type, Object object) {
        this.type = type;
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
