package com.yk.androidassistant.OkHttp.api;

import com.yk.androidassistant.OkHttp.httpUtils;
import com.yk.androidassistant.Util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * 登录
 * Created by yk on 2019/12/9 0009.
 */
public class mobileLogin {
    private static final String TAG = "mobileLogin";
    private static final String URL_HEAD = "mobileLogin/";

    /**
     * 登录
     *
     * @param loginId 用户名
     * @param pwd     密码
     * @return 返回数据
     */
    public static String doLogin(String loginId, String pwd) throws JSONException, IOException {
        String res;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginId", loginId);
        jsonObject.put("pwd", pwd);
//        Response response = httpUtils.doPost("gynshWeb/mobileLogin/doLogin", jsonObject.toString());
        Response response = httpUtils.doPost(URL_HEAD + "/doMobileLogin2", jsonObject.toString());
        if (response.isSuccessful()) { //数据获取成功
            res = response.body().string();
            //获取头信息
            Headers headers = response.headers();
            LogUtil.d(TAG, "headers: " + headers.toString());
//            Log.d(TAG, "headers: Set-Cookie-->" + headers.get("Set-Cookie"));
            String Cookie = headers.get("Set-Cookie");
            if (Cookie != null)
                httpUtils.COOKIE = headers.get("Set-Cookie").split(";")[0];
        } else {
            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
        }
        LogUtil.d(TAG, "doLogin:res-->" + res);
        return res;
    }

    /**
     * 获取版本信息
     *
     * @return
     * @throws IOException
     */
    public static String doMobileVersion() throws IOException {
        String res;
        Response response = httpUtils.doGet(URL_HEAD + "/doMobileVersion");
        if (response.isSuccessful()) {
            res = response.body().string();
        } else {
            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
        }
        LogUtil.d(TAG, "doMobileVersion: " + res);
        return res;
    }

    /**
     * 修改密码
     *
     * @return
     * @throws IOException
     */
    public static String doModifyPwd(String json) throws IOException {
        String res;
        Response response = httpUtils.doPost(URL_HEAD + "/doModifyPwd", json);
        if (response.isSuccessful()) {
            res = response.body().string();
        } else {
            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
        }
        LogUtil.d(TAG, "doModifyPwd: " + res);
        return res;
    }

    /**
     * 退出登录
     *
     * @return
     * @throws IOException
     */
    public static String doMobileLogout() throws IOException {
        String res;
        Response response = httpUtils.doGet(URL_HEAD + "/doMobileLogout");
        if (response.isSuccessful()) {
            res = response.body().string();
            //设置cookie为空
            httpUtils.COOKIE = "";
        } else {
            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
        }
        LogUtil.d(TAG, "doMobileLogout: " + res);
        return res;
    }

}