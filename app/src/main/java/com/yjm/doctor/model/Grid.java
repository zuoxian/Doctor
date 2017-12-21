package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2017/12/20.
 */

public class Grid implements Serializable{

    private static final long serialVersionUID = 6265044640399279461L;
    private List<GridInfo> rows ;
    private int total;

    public List<GridInfo> getRows() {
        return rows;
    }

    public void setRows(List<GridInfo> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
