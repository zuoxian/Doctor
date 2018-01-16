package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2018/1/11.
 */

public class SMessageRow implements Serializable {
    private static final long serialVersionUID = -5416144985769687510L;

    private List<SMessage> rows;

    private int total;

    public List<SMessage> getRows() {
        return rows;
    }

    public void setRows(List<SMessage> rows) {
        this.rows = rows;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
