package com.yjm.doctor.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.MemberDoctor;

import com.yjm.doctor.dao.CustomerDao;
import com.yjm.doctor.dao.UserDao;
import com.yjm.doctor.dao.MemberDoctorDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig customerDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig memberDoctorDaoConfig;

    private final CustomerDao customerDao;
    private final UserDao userDao;
    private final MemberDoctorDao memberDoctorDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        customerDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        memberDoctorDaoConfig = daoConfigMap.get(MemberDoctorDao.class).clone();
        memberDoctorDaoConfig.initIdentityScope(type);

        customerDao = new CustomerDao(customerDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        memberDoctorDao = new MemberDoctorDao(memberDoctorDaoConfig, this);

        registerDao(Customer.class, customerDao);
        registerDao(User.class, userDao);
        registerDao(MemberDoctor.class, memberDoctorDao);
    }
    
    public void clear() {
        customerDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
        memberDoctorDaoConfig.clearIdentityScope();
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public MemberDoctorDao getMemberDoctorDao() {
        return memberDoctorDao;
    }

}
