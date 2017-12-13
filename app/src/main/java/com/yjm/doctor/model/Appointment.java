package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2017/12/10.
 */

public class Appointment implements Serializable {

    private static final long serialVersionUID = -1859496344792657783L;
    private List<AppointmentInfo> rows;

    private int total;

    public List<AppointmentInfo> getRows() {
        return rows;
    }

    public void setRows(List<AppointmentInfo> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
