package com.yjm.doctor.model;

import java.io.Serializable;

/**
 * Created by zs on 2017/12/14.
 */

public class ObjBean  {
    private String appointmentFee;
    private boolean acceptAppointment;
    private boolean acceptConsultation;
    private String consultationFee;

    public ObjBean() {
    }


    @Override
    public String toString() {
        return "ObjBean{" +
                "appointmentFee=" + appointmentFee +
                ", acceptAppointment=" + acceptAppointment +
                ", acceptConsultation=" + acceptConsultation +
                ", consultationFee=" + consultationFee +
                '}';
    }

    public String getAppointmentFee() {
        return appointmentFee;
    }

    public void setAppointmentFee(String appointmentFee) {
        this.appointmentFee = appointmentFee;
    }

    public boolean isAcceptAppointment() {
        return acceptAppointment;
    }

    public void setAcceptAppointment(boolean acceptAppointment) {
        this.acceptAppointment = acceptAppointment;
    }

    public boolean isAcceptConsultation() {
        return acceptConsultation;
    }

    public void setAcceptConsultation(boolean acceptConsultation) {
        this.acceptConsultation = acceptConsultation;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }
}
