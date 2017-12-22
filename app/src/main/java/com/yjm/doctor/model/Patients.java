package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2017/12/22.
 */

public class Patients implements Serializable{
    private static final long serialVersionUID = 7512883259725413311L;

    private int total;
    private List<User> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<User> getRows() {
        return rows;
    }

    public void setRows(List<User> rows) {
        this.rows = rows;
    }
}
