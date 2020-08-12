package com.yk.androidassistant.Base;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.yk.androidassistant.OkHttp.httpUtils;
import com.yk.androidassistant.Util.AppFrontBackHelper;
import com.yk.androidassistant.Util.LogUtil;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    //版本信息
    public String version = "1.9.2";
    public String versionInfo = "初始版本";
    //是否已经提示过网络问题
    public boolean isToastNetState = false;
    //应用进入后台的时间
    private long backstageTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        QMUISwipeBackActivityManager.init(this);
        //设置log显示级别
        LogUtil.changeLogLevel(LogUtil.LogLevel.WARN);
//        LogUtil.changeLogLevel(LogUtil.LogLevel.NOLOG);

//        //app切换到后台监听
//        AppFrontBackHelper helper = new AppFrontBackHelper();
//        backstageTime = System.currentTimeMillis();
//        helper.register(this, new AppFrontBackHelper.OnAppStatusListener() {
//            @Override
//            public void onFront() {
//                //应用切到前台处理
//                LogUtil.d(TAG, "应用切到前台处理");
//                //计算耗时秒数
//                long time = System.currentTimeMillis() - backstageTime;
////                    LogUtil.d(TAG, "耗时-->" + time);
//                if (!httpUtils.COOKIE.isEmpty())
//                    if (time > 60 * 60 * 1000) { //超过60分钟重新登录
////                    if (time > 3 * 1000) { //超过60分钟重新登录
//                        finish();
//
//                        Intent intent = new Intent(getApplicationContext(),
//                                NineUnlockActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra(NineUnlockActivity.FUNCTION,
//                                NineUnlockActivity.FUNCTION_UNLOCK_AGAIN);
//                        startActivity(intent);
//                        setToast("超时重新登录！");
//                    }
//            }
//
//            @Override
//            public void onBack() {
//                //应用切到后台处理
//                LogUtil.d(TAG, "应用切到后台处理");
//                backstageTime = System.currentTimeMillis();
//                if (!httpUtils.COOKIE.isEmpty())
//                    setToast("1小时不回来将退出登录！");
//            }
//        });
    }

    //Toast
    Toast toast;

    /**
     * toast信息提示
     *
     * @param message 提示信息
     */
    public void setToast(String message) {
        if (toast == null)
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        else
            toast.setText(message);
        toast.show();
    }

    //退出登录初始化数据
    public void finish() {
        //退出登录接口
    }

}
