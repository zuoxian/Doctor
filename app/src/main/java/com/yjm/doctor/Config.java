package com.yjm.doctor;

/**
 * Created by zx on 2017/12/7.
 */

public class Config {

    private static final String DOMAIN_URL = "https://www.mobiang.com";

    public static final String USER_API = DOMAIN_URL + "/ethealth/api/member";

    public static final String USER_LEVELS_API = DOMAIN_URL + "/ethealth/api/apiCommon";

    public static final String HOME_BANNERS = DOMAIN_URL + "/ethealth/api/home";

    public static final String HOME_CONSULATION =  DOMAIN_URL + "/ethealth/api/consultation/doctor";

    public static final String EASE_MESSAGE =  DOMAIN_URL + "/ethealth/api/consultation";

    public static final String HOME_APPOINTMENT =  DOMAIN_URL + "/ethealth/api/appointment/doctor";

    public static final String USER_BUSINESSSETTING = DOMAIN_URL + "/ethealth/api/doctor";

    public static final String USER_BALANCE = DOMAIN_URL + "/ethealth/api/balance";

    public static final String MAIN_BASEDATA = DOMAIN_URL + "/ethealth/api/baseData";

    public static final String HOME_APPOINTMENT_INFO = DOMAIN_URL +"/ethealth/api/appointment";

    public static final String SERVICE_GRID = DOMAIN_URL +"/ethealth/api/doctorCloseTime";

    public static final String DOCTORSERVICE = DOMAIN_URL +"/ethealth/api/doctorService";

    public static final String MESSAGE = DOMAIN_URL +"/ethealth/api/message";

    public static final String USER = "user_info";

    public static String DEFAULT_TOKENID = "1D96DACB84F21890ED9F4928FA8B352B";

    public static final int DOCTOR = 2;

    public static final int AUTH_STATUS_SUCCESS = 1;//登录成功

    public static final int AUTH_STATUS_AUTH = 2;//认证中

    public static final int AUTH_STATUS_FAIL = 3;//认证失败

    public static String LEVEL_EVENTTYPE = "level";

    public static String HOSPITAL_EVENTTYPE = "hospital";

    public static String DEPARTMENT_EVENTTYPE = "department";

    public static String BC_EVENTTYPE = "bc";

    public static String PUSH_TYPE = "stauts_push";

    public static String MESSAGE_NUM = "message_num";



    public static int userId = 0;

    public static String mobile = "";

    public static final String SOURCE = "6";

    public static final int APPOINTMENT_REPLY =1;//已回复

    public static final int APPOINTMENT_MAKE = 0;//未回复

    public static final String APPOINTMENT_TYPE =  "appointment_type";

    public static final int SEX_FEMALE = 0;//女

    public static final int SEX_MALE = 1;//男

    public static final String MAIN_SERVICE_TYPE = "main_service_type";//信息服务标识
    public static final int MAIN_SERVICE_TYPE_VALUE = 1;//信息服务标识

    public static final int MAIN_TAB = 0;

    public static final int MAIN_SERVICE = 1;

    public static final int MAIN_USER = 2;

    public static final String TAB = "tab";

    public static final String REGISTER_STATUS = "register_status";

    public static final String UPDATE_USER_STATUS = "update_user_status";


}
