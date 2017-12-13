//package com.yjm.doctor.util.auth;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.yjm.doctor.Config;
//import com.yjm.doctor.api.UserAPI;
//import com.yjm.doctor.model.User;
//import com.yjm.doctor.model.UserBean;
//import com.yjm.doctor.util.NetworkUtils;
//import com.yjm.doctor.util.RestAdapterUtils;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okio.Buffer;
//import okio.BufferedSource;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//
//
///**
// * Created by zx on 2017/12/12.
// */
//
//public class TokenInterceptor implements Interceptor, Callback<UserBean> {
//    private static final Charset UTF8 = Charset.forName("UTF-8");
//
//    private UserService userService;
//
//    private String pwd;
//
//    private UserAPI userAPI;
//
//    private Context mContext;
//
//    private Chain chain;
//
//    public TokenInterceptor() {
//    }
//
//    public TokenInterceptor(Context context) {
//        this.mContext = context;
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        chain = chain;
//
//        Request request = chain.request();
//
//        Response response = chain.proceed(request);
//        Log.i("main","response.code=" + response.code());
//
//        if(isTokenExpired(response)){
//            getNewToken();
//        }
//
//        return null;
//    }
//
//    private boolean isTokenExpired(Response response){
//        try {
//            ResponseBody responseBody = response.body();
//            BufferedSource source =responseBody.source();
//            source.request(Long.MAX_VALUE);
//            Buffer buffer = source.buffer();
//            Charset charset = UTF8;
//            MediaType contentType = responseBody.contentType();
//            if(contentType != null){
//                charset = contentType.charset(UTF8);
//            }
//            String bodyString = buffer.clone().readString(charset);
//
//            Log.i("main", "response.body = "+bodyString);
//        }catch (Exception e){
//            Log.i("main",e.getMessage());
//        }
//
//        return false;
//    }
//
//    private void getNewToken(){
//        userService = UserService.getInstance();
//        pwd = userService.getPwd(Config.userId);
//        userAPI = RestAdapterUtils.getRestAPI(Config.USER_API, UserAPI.class);
//        userAPI.login(Config.mobile, pwd, 2,this);
//    }
//
//    @Override
//    public void success(UserBean userBean, retrofit.client.Response response) {
//        if(null != userBean && true == userBean.getSuccess()) {
//            if (null == userBean.getObj()) {
//                return;
//            }
//            User user = userBean.getObj();
//            if (Config.AUTH_STATUS_SUCCESS == user.getStatus() || Config.AUTH_STATUS_AUTH == user.getStatus()) {
//                Log.i("main", "Splash tonken  tokenid=" + user.getTokenId());
//                String cache = "public, max-age=" + 600;
//                if (NetworkUtils.isNetworkAvaliable(mContext)){
//                    cache = "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 28;
//                }
//                Request newRequest = chain.request().newBuilder().header("tokenId",user.getTokenId())
//                        .header("Cache-Control",cache)
//                        .header("Transfer-Encoding","chunked")
//                        .build();//重新发起请求
//                Log.i("main", "Splash  user=" + user.toString());
//                finishLogin(user);
//            }
//        }
//    }
//
//
//    private void finishLogin(User user) {
//
//        UserService.getInstance(mContext).signIn(Config.mobile, pwd, user);
//
//        if(!TextUtils.isEmpty(user.getTokenId())) {
//            if(!TextUtils.isEmpty(user.getTokenId()))
//                UserService.getInstance(mContext).setTokenId(user.getId(),user.getTokenId());
//
//        }
//        if (user.getId() == 0) {
//            Config.userId = 0;
//        } else {
//            Config.userId = user.getId();
//        }
//    }
//
//    @Override
//    public void failure(RetrofitError error) {
//
//    }
//}
