package com.yk.androidassistant.Util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理类
 * Created by jsgyrcb-yk on 2019/12/7 0007.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 向list添加数据
     *
     * @param activity 活动
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 从list移出活动
     *
     * @param activity 活动
     */
    public static void remnoveActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 将所有活动全部销毁
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing())
                activity.finish();
        }
    }
}
