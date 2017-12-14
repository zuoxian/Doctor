package com.yjm.doctor.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.MemberDoctor;

/**
 * Created by zx on 2017/12/8.
 */
public class GreenDaoHelper {

    private static GreenDaoHelper instance;
    private DaoSession daoSession;

    public GreenDaoHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "yjm-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static synchronized GreenDaoHelper getInstance(Context ctx) {
        if (instance == null)
            instance = new GreenDaoHelper(ctx.getApplicationContext());
        return instance;
    }

    public UserDao getUserDao() {
        return daoSession.getUserDao();
    }

    public CustomerDao getCustomDao() {
        return daoSession.getCustomerDao();
    }

    public MemberDoctorDao getMemberDao() {
        return daoSession.getMemberDoctorDao();
    }


}
