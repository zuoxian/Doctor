package com.yjm.doctor.util.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.MemberDoctor;
import com.yjm.doctor.model.User;
import com.yjm.doctor.util.SharedPreferencesHelper;

public class UserService {

    private static UserService mInstance;
    private final String KEY_USER_NAME = "username";
    private final String KEY_USER_UPDATETIME = "updateTime";
    private final String KEY_USER_STATUS = "status";
    private final String KEY_USER_SCORE = "score";
    private final String KEY_USER_REGTIME = "regTime";
    private final String KEY_USER_REGIP = "regIp";
    private final String KEY_USER_PIC = "pic";
    private final String KEY_USER_CHINA_COIN = "china_coin";
    private final String KEY_USER_PASSWORD = "password";
    private final String KEY_USER_MODELID = "modelid";
    private final String KEY_USER_MOBILE = "mobile";
    private final String KEY_USER_LOGIN = "login";
    private final String KEY_USER_LASTLOGINTIME = "lastLoginTime";
    private final String KEY_USER_LASTLOGINIP = "lastLoginIp";
    private final String KEY_USER_ISADMIN = "isAdmin";
    private final String KEY_USER_ID = "id";
    private final String KEY_USER_HXSTATUS = "hxStatus";
    private final String KEY_USER_HXPASSWORD = "hxPassword";
    private final String KEY_USER_GROUPID = "groupid";
    private final String KEY_USER_EMAIL = "email";
    private final String KEY_USER_AMOUNT = "amount";
    private final String KEY_DEFAULT_ACCOUNT = "default_count";
    private final String KEY_USER_TOKEN = "tokenId";

    private final String AGE = "age";
    private final String BALANCE = "balance";
    private final String BIRTHDAY = "birthday";
    private final String C_GROUPID = "groupId";
    private final String PHONE = "phone";
    private final String POINT = "point";
    private final String REALNAME = "realName";
    private final String SEX = "sex";
    private final String USERID = "userId";


    private final String CREATETIME = "createTime";
    private final String DEPARTMENT = "department";
    private final String DEPARTMENTNAME = "departmentName";
    private final String EDUCATION = "education";
    private final String EDUCATIONNAME = "educationName";
    private final String GROUPID = "groupId";
    private final String HOSPITAL = "hospital";
    private final String HOSPITALNAME = "hospitalName";
    private final String ID = "id";
    private final String INTRODUCE = "introduce";
    private final String  LEADER= "leader";
    private final String  LEVEL= "level";
    private final String  LEVELNAME= "levelName";
    private final String  SORT= "sort";
    private final String  SPECIALITY= "speciality";
    private final String  UPDATETIME= "updateTime";
    private final String CUSTOMER = "customer";
    private final String MEMBERDOCTOR = "memberDoctor";


    private Account activeAccount;
    private SharedPreferencesHelper sharedPrefs;
    private AccountManager mAccountManager;
    private Context mContext;

    public UserService() {
    }

    public UserService(Context c) {
        mContext = c;
        sharedPrefs = new SharedPreferencesHelper(c);
        mAccountManager = AccountManager.get(c);
        if (hasDefaultAccount()) {
            activeAccount = getDefaultAccount();
        }
        if (getActiveAccountInfo().getId() == 0) {
            Config.userId = 0;
        } else {
            Config.userId = getActiveAccountInfo().getId();
        }
    }

    public static synchronized UserService getInstance(Context c) {
        if (mInstance == null)
            mInstance = new UserService(c.getApplicationContext());
        return mInstance;
    }

    public static synchronized UserService getInstance() {
        if (mInstance == null)
            mInstance = new UserService();
        return mInstance;
    }

    private boolean hasDefaultAccount() {
        return getDefaultAccount() != null;
    }

    private Account getDefaultAccount() {
        String accountName = sharedPrefs.getValue(KEY_DEFAULT_ACCOUNT);

        return findAccountByUsername(accountName);
    }

    public void setTokenId(int id,String tokenId){

        sharedPrefs.setValue(id+"", tokenId);
    }

    public void setPwd(int id,String pwd){

        sharedPrefs.setValue(id+"pwd", pwd);
    }
    public String getPwd(int id){
        return sharedPrefs.getValue(id+"pwd");
    }

    public String getTokenId(int id){
        return sharedPrefs.getValue(id+"");
    }


    private void setDefaultAccount(Account defaultAccount) {
        sharedPrefs.setValue(KEY_DEFAULT_ACCOUNT, defaultAccount.name);

        boolean foundAccount = false;
        Account[] accounts = mAccountManager.getAccounts();
        for (int i = 0; i < accounts.length && !foundAccount; i++) {
            if (accounts[i].name.equals(defaultAccount.name)) {
                foundAccount = true;
            }
        }

        if (!foundAccount) {
            // 默认账户被注销了?
        }
    }



    public boolean hasActiveAccount() {
        return this.activeAccount != null;
    }

    public User getActiveAccountInfo() {
        if (null != activeAccount)
            return getAccountInfo(activeAccount);
        else
            return new User();
    }

    public Account getActiveAccount() {
        return this.activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account setActiveAccount(String username, String password) {
        Account account = findAccountByUsername(username);
        if (account == null) {
            account = new Account(username, mContext.getString(R.string.ACCOUNT_TYPE));
            mAccountManager.addAccountExplicitly(account, password, null);
        }
        this.activeAccount = account;

        return account;
    }

    public String getPassword(Account account) {
        return mAccountManager.getPassword(account);
    }

    public boolean signIn(String username, String password, User user) {

        if (user == null) {
            return false;
        }
        Account account = setActiveAccount(username, password);
        setDefaultAccount(account);
        updateAccountInfo(account, user);
        return true;
    }

    private User getAccountInfo(Account account) {
        User user = new User();
        user.setUsername(mAccountManager.getUserData(account, KEY_USER_NAME));
        user.setUpdateTime(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_UPDATETIME)));
        user.setStatus(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_STATUS)));
        user.setScore(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_SCORE)));
        user.setRegTime(Long.parseLong(mAccountManager.getUserData(account, KEY_USER_REGTIME)));
        user.setRegIp(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_REGIP)));
        user.setPic(mAccountManager.getUserData(account, KEY_USER_PIC));
        user.setPassword(mAccountManager.getUserData(account, KEY_USER_PASSWORD));
        user.setModelid(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_MODELID)));
        user.setMobile(mAccountManager.getUserData(account, KEY_USER_MOBILE));
        user.setLogin(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_LOGIN)));
        user.setLastLoginTime(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_LASTLOGINTIME)));
        user.setLastLoginIp(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_LASTLOGINIP)));
        user.setIsAdmin(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_ISADMIN)));
        user.setId(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_ID)));
        user.setHxStatus(Boolean.valueOf(mAccountManager.getUserData(account, KEY_USER_HXSTATUS)));
        user.setHxPassword(mAccountManager.getUserData(account, KEY_USER_HXPASSWORD));
        user.setGroupid(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_GROUPID)));
        user.setEmail(mAccountManager.getUserData(account, KEY_USER_EMAIL));
        user.setAmount(Integer.parseInt(mAccountManager.getUserData(account, KEY_USER_AMOUNT)));
        user.setTokenId(mAccountManager.getUserData(account, KEY_USER_TOKEN));

        if(!TextUtils.isEmpty(mAccountManager.getUserData(account,USERID))){
            Customer customer = new Customer(Integer.parseInt(mAccountManager.getUserData(account,AGE)==null ?mAccountManager.getUserData(account,AGE):"0"),
                    Float.parseFloat(mAccountManager.getUserData(account,BALANCE)),
                    Long.parseLong(mAccountManager.getUserData(account,BALANCE)),
                    Integer.parseInt(mAccountManager.getUserData(account,GROUPID)==null?"0":mAccountManager.getUserData(account,GROUPID)),
                    String.valueOf(mAccountManager.getUserData(account,PHONE)),
                    Integer.parseInt(mAccountManager.getUserData(account,POINT)==null?"0":mAccountManager.getUserData(account,POINT)),
                    String.valueOf(mAccountManager.getUserData(account,REALNAME)),
                    Integer.parseInt(mAccountManager.getUserData(account,SEX)==null?"0":mAccountManager.getUserData(account,SEX)),
                    Integer.parseInt(mAccountManager.getUserData(account,USERID)==null?"0":mAccountManager.getUserData(account,USERID))
            );
            user.setCustomer(customer);
        }



        if(!TextUtils.isEmpty(mAccountManager.getUserData(account,ID))) {
            MemberDoctor memberDoctor = new MemberDoctor(
                    String.valueOf(mAccountManager.getUserData(account, CREATETIME)),
                    Integer.parseInt(mAccountManager.getUserData(account, DEPARTMENT)==null?"0":mAccountManager.getUserData(account, DEPARTMENT)),
                    String.valueOf(mAccountManager.getUserData(account, DEPARTMENTNAME)),
                    String.valueOf(mAccountManager.getUserData(account, EDUCATION)),
                    String.valueOf(mAccountManager.getUserData(account, EDUCATIONNAME)),
                    Integer.parseInt(mAccountManager.getUserData(account, GROUPID)==null?"0":mAccountManager.getUserData(account, GROUPID)),
                    Integer.parseInt(mAccountManager.getUserData(account, HOSPITAL)==null?"0":mAccountManager.getUserData(account, HOSPITAL)),
                    String.valueOf(mAccountManager.getUserData(account, HOSPITALNAME)),
                    Integer.parseInt(mAccountManager.getUserData(account, ID)==null?"0":mAccountManager.getUserData(account, ID)),
                    String.valueOf(mAccountManager.getUserData(account, INTRODUCE)),
                    Boolean.valueOf(mAccountManager.getUserData(account, LEADER)),
                    Integer.parseInt(mAccountManager.getUserData(account, LEVEL)==null?"0":mAccountManager.getUserData(account, LEVEL)),
                    String.valueOf(mAccountManager.getUserData(account, LEVELNAME)),
                    Integer.parseInt(mAccountManager.getUserData(account, SORT)==null?"0":mAccountManager.getUserData(account, SORT)),
                    String.valueOf(mAccountManager.getUserData(account, SPECIALITY)),
                    String.valueOf(mAccountManager.getUserData(account, UPDATETIME))
            );
            user.setMemberDoctor(memberDoctor);
        }

        return user;
    }

    public void updateActiveAccountInfo(User userProfile) {
        updateAccountInfo(activeAccount, userProfile);
    }

    public void updateAccountInfo(Account account, User userProfile) {
        Log.i("tokenId",userProfile.toString());
        mAccountManager.setUserData(account, KEY_USER_NAME, String.valueOf(userProfile.getUsername()));
        mAccountManager.setUserData(account, KEY_USER_UPDATETIME, String.valueOf(userProfile.getUpdateTime()));
        mAccountManager.setUserData(account, KEY_USER_STATUS, String.valueOf(userProfile.getStatus()));
        mAccountManager.setUserData(account, KEY_USER_SCORE, String.valueOf(userProfile.getScore()));
        mAccountManager.setUserData(account, KEY_USER_REGTIME, String.valueOf(userProfile.getRegTime()));
        mAccountManager.setUserData(account, KEY_USER_REGIP, String.valueOf(userProfile.getRegIp()));
        mAccountManager.setUserData(account, KEY_USER_CHINA_COIN, userProfile.getPassword());
        mAccountManager.setUserData(account, KEY_USER_MODELID, String.valueOf(userProfile.getModelid()));
        mAccountManager.setUserData(account, KEY_USER_MOBILE, userProfile.getMobile());
        mAccountManager.setUserData(account, KEY_USER_LOGIN, String.valueOf(userProfile.getLogin()));
        mAccountManager.setUserData(account, KEY_USER_LASTLOGINTIME, String.valueOf(userProfile.getLastLoginTime()));
        mAccountManager.setUserData(account, KEY_USER_LASTLOGINIP, String.valueOf(userProfile.getLastLoginIp()));
        mAccountManager.setUserData(account, KEY_USER_ISADMIN, String.valueOf(userProfile.getIsAdmin()));
        mAccountManager.setUserData(account, KEY_USER_ID, String.valueOf(userProfile.getId()));
        mAccountManager.setUserData(account, KEY_USER_HXSTATUS, String.valueOf(userProfile.getHxStatus()));
        mAccountManager.setUserData(account, KEY_USER_HXPASSWORD, userProfile.getHxPassword());
        mAccountManager.setUserData(account, KEY_USER_GROUPID, String.valueOf(userProfile.getGroupid()));
        mAccountManager.setUserData(account, KEY_USER_EMAIL, userProfile.getEmail());
        mAccountManager.setUserData(account, KEY_USER_AMOUNT, String.valueOf(userProfile.getAmount()));
        mAccountManager.setUserData(account, KEY_USER_PASSWORD, String.valueOf(userProfile.getTokenId()));
        mAccountManager.setAuthToken(account,KEY_USER_TOKEN,userProfile.getTokenId());
        Log.i("tokenId",userProfile.getTokenId());

        mAccountManager.setUserData(account,AGE,String.valueOf(userProfile.getCustomer().getAge()));
        mAccountManager.setUserData(account,BIRTHDAY,String.valueOf(userProfile.getCustomer().getBirthday()));
        mAccountManager.setUserData(account,C_GROUPID,String.valueOf(userProfile.getCustomer().getGroupId()));
        mAccountManager.setUserData(account,PHONE,String.valueOf(userProfile.getCustomer().getPhone()));
        mAccountManager.setUserData(account,POINT,String.valueOf(userProfile.getCustomer().getPoint()));
        mAccountManager.setUserData(account,REALNAME,String.valueOf(userProfile.getCustomer().getRealName()));
        mAccountManager.setUserData(account,SEX,String.valueOf(userProfile.getCustomer().getSex()));
        mAccountManager.setUserData(account,USERID,String.valueOf(userProfile.getCustomer().getUserId()));

        mAccountManager.setUserData(account,CREATETIME,String.valueOf(userProfile.getMemberDoctor().getCreateTime()));
        mAccountManager.setUserData(account,DEPARTMENT,String.valueOf(userProfile.getMemberDoctor().getDepartment()));
        mAccountManager.setUserData(account,DEPARTMENTNAME,String.valueOf(userProfile.getMemberDoctor().getDepartmentName()));
        mAccountManager.setUserData(account,EDUCATION,String.valueOf(userProfile.getMemberDoctor().getEducation()));
        mAccountManager.setUserData(account,EDUCATIONNAME,String.valueOf(userProfile.getMemberDoctor().getEducationName()));
        mAccountManager.setUserData(account,GROUPID,String.valueOf(userProfile.getMemberDoctor().getGroupId()));
        mAccountManager.setUserData(account,HOSPITAL,String.valueOf(userProfile.getMemberDoctor().getHospital()));
        mAccountManager.setUserData(account,HOSPITALNAME,String.valueOf(userProfile.getMemberDoctor().getHospitalName()));
        mAccountManager.setUserData(account, ID,String.valueOf(userProfile.getMemberDoctor().getId()));
        mAccountManager.setUserData(account,INTRODUCE,String.valueOf(userProfile.getMemberDoctor().getIntroduce()));
        mAccountManager.setUserData(account,LEADER,String.valueOf(userProfile.getMemberDoctor().getLeader()));
        mAccountManager.setUserData(account,LEVEL,String.valueOf(userProfile.getMemberDoctor().getLevel()));
        mAccountManager.setUserData(account,LEVELNAME,String.valueOf(userProfile.getMemberDoctor().getLevelName()));
        mAccountManager.setUserData(account,SORT,String.valueOf(userProfile.getMemberDoctor().getSort()));
        mAccountManager.setUserData(account,SPECIALITY,String.valueOf(userProfile.getMemberDoctor().getSpeciality()));
        mAccountManager.setUserData(account,UPDATETIME,String.valueOf(userProfile.getMemberDoctor().getUpdateTime()));

    }

    private Account findAccountByUsername(String username) {
        Log.d("tab", "findAccountByUsername, username: " + username);
        if (username.length() > 0) {
            for (Account account : mAccountManager.getAccountsByType(mContext.getString(R.string.ACCOUNT_TYPE))) {
                Log.d("tab", "findAccountByUsername, a.name: " + account.name);
                if (account.name.equals(username)) {
                    return account;
                }
            }
        }
        Config.userId = 0;
        return null;
    }

    public void logout(String username) {
        Account account = findAccountByUsername(username);
        if (account != null) {
            mAccountManager.removeAccount(account, null, null);
            sharedPrefs.remove(KEY_DEFAULT_ACCOUNT);
        }
    }

    public void logout() {
        Account account = getDefaultAccount();
        if (account != null) {
            mAccountManager.removeAccount(account, null, null);
            sharedPrefs.remove(KEY_DEFAULT_ACCOUNT);
            activeAccount = null;
        }
        Config.userId = 0;
    }

    public void updateAccountAvatar(String avatar) {
        mAccountManager.setUserData(activeAccount, KEY_USER_PIC, avatar);
    }

//    public void updateAccountGender(String gender) {
//        mAccountManager.setUserData(activeAccount, KEY_USER_DATA_GENDER, gender);
//    }


}
