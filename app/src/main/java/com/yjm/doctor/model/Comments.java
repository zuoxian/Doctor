package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2017/12/25.
 */

public class Comments implements Serializable{
    private static final long serialVersionUID = -3688537261646671047L;

    private List<Comment> rows;

    public List<Comment> getRows() {
        return rows;
    }

    public void setRows(List<Comment> rows) {
        this.rows = rows;
    }

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
