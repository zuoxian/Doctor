package com.yjm.doctor.util.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class UserAccountService extends Service {
    private UserAccountAuthenticator _saa;

    @Override
    public IBinder onBind(Intent intent) {
        IBinder ret = null;
        if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
            ret = getMovieAuthenticator().getIBinder();
        return ret;
    }

    private UserAccountAuthenticator getMovieAuthenticator() {
        if (_saa == null)
            _saa = new UserAccountAuthenticator(this);
        return _saa;
    }
}
