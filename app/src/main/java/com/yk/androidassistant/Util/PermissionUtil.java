package com.yk.androidassistant.Util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.rh.nhcj.BuildConfig;

/**
 * 跳转权限申请界面工具类
 * Created by jsgyrcb-yk on 2019/12/13 0013.
 */
public class PermissionUtil {
    private static final String TAG = "PermissionUtil";

    /**
     * 跳转到权限申请界面
     *
     * @param context 上下文
     */
    public static Intent gotoPermission(Context context) {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            LogUtil.d(TAG,"gotoPermission-->redmi");
            return PermissionUtil.gotoMiuiPermission(context);
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            LogUtil.d(TAG,"gotoPermission-->meizu");
            return PermissionUtil.gotoMeizuPermission(context);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            LogUtil.d(TAG,"gotoPermission-->huawei");
            return PermissionUtil.gotoHuaweiPermission(context);
        } else {
            LogUtil.d(TAG,"gotoPermission-->其他");
            return PermissionUtil.getAppDetailSettingIntent(context);
        }
    }


    /**
     * 跳转到miui的权限管理页面
     */
    private static Intent gotoMiuiPermission(Context context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
//            context.startActivity(localIntent);
            return localIntent;
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
//                context.startActivity(localIntent);
                return localIntent;
            } catch (Exception e1) { // 否则跳转到应用详情
//                context.startActivity(getAppDetailSettingIntent(context));
                return getAppDetailSettingIntent(context);
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static Intent gotoMeizuPermission(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
//            context.startActivity(intent);
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
//            context.startActivity(getAppDetailSettingIntent(context));
            return getAppDetailSettingIntent(context);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static Intent gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
//            context.startActivity(intent);
            LogUtil.d(TAG,"gotoHuaweiPermission-->进入华为的权限管理页面");
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
//            context.startActivity(getAppDetailSettingIntent(context));
            LogUtil.d(TAG,"gotoHuaweiPermission-->进入华为的权限管理页面失败");
            return getAppDetailSettingIntent(context);
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));

        LogUtil.d(TAG,"getAppDetailSettingIntent-->获取应用详情页面intent" +
                "（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）");
        return localIntent;
    }

    /**
     * 通知权限申请
     *
     * @param context
     */
    public static void requestNotify(Context context) {
        /**
         * 跳到通知栏设置界面
         * @param context
         */
        Intent localIntent = new Intent();
        ///< 直接跳转到应用通知设置的代码
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            ///< 4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }
}
