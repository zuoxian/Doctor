package com.yjm.doctor.model;

//import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.DaoException;
import com.yjm.doctor.dao.DaoSession;
import com.yjm.doctor.dao.MemberDoctorDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.yjm.doctor.dao.CustomerDao;
import com.yjm.doctor.dao.UserDao;

/**
 * Created by zx on 2017/12/7.
 */
@Entity
public class User implements Serializable {


    private static final long serialVersionUID = -8951807283047736469L;

    private int amount;
    private String email;

    private int groupid;

    private String hxPassword;

    private boolean hxStatus;
    @Id(autoincrement = false)
    private int id;

    private int isAdmin;

    private int lastLoginIp;

    private int lastLoginTime;

    private int login;

    private String mobile;

    private int modelid;

    private String password;

    private String pic;

    private int regIp;

    private long regTime;

    private int score;

    private int status;

    private String tokenId;

    private int updateTime;

    private String username;

    @Transient
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @ToOne(joinProperty = "id")
    private Customer customer;

   @ToOne(joinProperty = "id")
    private MemberDoctor memberDoctor;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1507654846)
private transient UserDao myDao;

@Generated(hash = 43279384)
private transient Integer customer__resolvedKey;

@Generated(hash = 1960825245)
private transient Integer memberDoctor__resolvedKey;




    @Generated(hash = 1313920973)
    public User(int amount, String email, int groupid, String hxPassword,
            boolean hxStatus, int id, int isAdmin, int lastLoginIp,
            int lastLoginTime, int login, String mobile, int modelid,
            String password, String pic, int regIp, long regTime, int score,
            int status, String tokenId, int updateTime, String username) {
        this.amount = amount;
        this.email = email;
        this.groupid = groupid;
        this.hxPassword = hxPassword;
        this.hxStatus = hxStatus;
        this.id = id;
        this.isAdmin = isAdmin;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
        this.login = login;
        this.mobile = mobile;
        this.modelid = modelid;
        this.password = password;
        this.pic = pic;
        this.regIp = regIp;
        this.regTime = regTime;
        this.score = score;
        this.status = status;
        this.tokenId = tokenId;
        this.updateTime = updateTime;
        this.username = username;
    }



    @Generated(hash = 586692638)
    public User() {
    }


    
    @Override
    public String toString() {
        return "User{" +
                "amount=" + amount +
                ", email='" + email + '\'' +
                ", groupid=" + groupid +
                ", hxPassword='" + hxPassword + '\'' +
                ", hxStatus=" + hxStatus +
                ", id=" + id +
                ", isAdmin=" + isAdmin +
                ", lastLoginIp=" + lastLoginIp +
                ", lastLoginTime=" + lastLoginTime +
                ", login=" + login +
                ", mobile='" + mobile + '\'' +
                ", modelid=" + modelid +
                ", password='" + password + '\'' +
                ", pic='" + pic + '\'' +
                ", regIp=" + regIp +
                ", regTime=" + regTime +
                ", score=" + score +
                ", status=" + status +
                ", tokenId='" + tokenId + '\'' +
                ", updateTime=" + updateTime +
                ", username='" + username + '\'' +
                '}';
    }



    public int getAmount() {
        return this.amount;
    }



    public void setAmount(int amount) {
        this.amount = amount;
    }



    public String getEmail() {
        return this.email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public int getGroupid() {
        return this.groupid;
    }



    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }



    public String getHxPassword() {
        return this.hxPassword;
    }



    public void setHxPassword(String hxPassword) {
        this.hxPassword = hxPassword;
    }



    public boolean getHxStatus() {
        return this.hxStatus;
    }



    public void setHxStatus(boolean hxStatus) {
        this.hxStatus = hxStatus;
    }



    public int getId() {
        return this.id;
    }



    public void setId(int id) {
        this.id = id;
    }



    public int getIsAdmin() {
        return this.isAdmin;
    }



    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }



    public int getLastLoginIp() {
        return this.lastLoginIp;
    }



    public void setLastLoginIp(int lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }



    public int getLastLoginTime() {
        return this.lastLoginTime;
    }



    public void setLastLoginTime(int lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }



    public int getLogin() {
        return this.login;
    }



    public void setLogin(int login) {
        this.login = login;
    }



    public String getMobile() {
        return this.mobile;
    }



    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public int getModelid() {
        return this.modelid;
    }



    public void setModelid(int modelid) {
        this.modelid = modelid;
    }



    public String getPassword() {
        return this.password;
    }



    public void setPassword(String password) {
        this.password = password;
    }



    public String getPic() {
        return this.pic;
    }



    public void setPic(String pic) {
        this.pic = pic;
    }



    public int getRegIp() {
        return this.regIp;
    }



    public void setRegIp(int regIp) {
        this.regIp = regIp;
    }



    public long getRegTime() {
        return this.regTime;
    }



    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }



    public int getScore() {
        return this.score;
    }



    public void setScore(int score) {
        this.score = score;
    }



    public int getStatus() {
        return this.status;
    }



    public void setStatus(int status) {
        this.status = status;
    }



    public String getTokenId() {
        return this.tokenId;
    }



    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }



    public int getUpdateTime() {
        return this.updateTime;
    }



    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }



    public String getUsername() {
        return this.username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    /** To-one relationship, resolved on first access. */
    @Generated(hash = 837741669)
    public Customer getCustomer() {
        int __key = this.id;
        if (customer__resolvedKey == null || !customer__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CustomerDao targetDao = daoSession.getCustomerDao();
            Customer customerNew = targetDao.load(__key);
            synchronized (this) {
                customer = customerNew;
                customer__resolvedKey = __key;
            }
        }
        return customer;
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2053123759)
    public void setCustomer(@NotNull Customer customer) {
        if (customer == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.customer = customer;
            id = customer.getUserId();
            customer__resolvedKey = id;
        }
    }



    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2075703290)
    public MemberDoctor getMemberDoctor() {
        int __key = this.id;
        if (memberDoctor__resolvedKey == null
                || !memberDoctor__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MemberDoctorDao targetDao = daoSession.getMemberDoctorDao();
            MemberDoctor memberDoctorNew = targetDao.load(__key);
            synchronized (this) {
                memberDoctor = memberDoctorNew;
                memberDoctor__resolvedKey = __key;
            }
        }
        return memberDoctor;
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1814594776)
    public void setMemberDoctor(@NotNull MemberDoctor memberDoctor) {
        if (memberDoctor == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.memberDoctor = memberDoctor;
            id = memberDoctor.getId();
            memberDoctor__resolvedKey = id;
        }
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
