package com.yjm.doctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zx on 2017/12/23.
 */

public class UserPatientInfos implements Serializable{

    private static final long serialVersionUID = -4038938045231302034L;

    private List<UserPatientInfo> appointments;

    public List<UserPatientInfo> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<UserPatientInfo> appointments) {
        this.appointments = appointments;
    }
}
