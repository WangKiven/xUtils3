package com.sxb.kiven.mytest;

import android.app.Application;
import android.content.Context;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by kiven on 16/3/30.
 */
public class MyApplication extends Application {

    static MyApplication mContext = null;

    private UserBo mUser;
    private DbManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        x.Ext.init(this);
        x.Ext.setDebug(true);
        dbManager = x.getDb(null);
    }

    public static MyApplication getInstance(){
        return mContext;
    }


    public boolean isLogin(){
        return mUser != null;
    }

    public UserBo getUser() {
        return mUser;
    }

    public void setUser(UserBo user) {
        mUser = user;
        if (mUser != null) {
            try {
                dbManager.delete(UserBo.class);
                dbManager.save(mUser);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
}
