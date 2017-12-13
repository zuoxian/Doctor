package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2017/12/11.
 */

public class ConsultationObject implements Serializable {

    private static final long serialVersionUID = -5238270571811695110L;
    private List<ConsultationRows> rows;
    private int total;

    public List<ConsultationRows> getRows() {
        return rows;
    }

    public void setRows(List<ConsultationRows> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
