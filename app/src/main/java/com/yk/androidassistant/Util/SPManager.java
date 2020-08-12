package com.yk.androidassistant.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

//SharedPreferences处理类
public class SPManager {
    private static final String TAG = "SPManager";

    //定义存储用户名字段
    public static final String SP_USER_NAME = "0";
    public static final String SP_USER_PWD = "1";
    public static final String SP_NINE_UNLOCK = "2";

    private static SPManager instance;
    private SharedPreferences sp;
    private Context mContext;

    private SPManager(Context context) {
        this.mContext = context;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new SPManager(context);
        }
    }

    public static synchronized SPManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    SPManager.class.getSimpleName()
                            + " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    //存储数据
    public void putValue(String key, String value) {
        try {
            sp.edit().putString(DeEnCode.encrypt(key),
                    DeEnCode.encrypt(value)).apply();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //提取数据
    public String getValue(String key) {
        try {
            String value = sp.getString(DeEnCode.encrypt(key), "");
            if(value.isEmpty())
                return value;
            return DeEnCode.decrypt(value);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //删除
    public void removeKey(String key) {
        try {
            sp.edit().remove(DeEnCode.encrypt(key)).apply();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
