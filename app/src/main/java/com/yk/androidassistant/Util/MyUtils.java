package com.yk.androidassistant.Util;


import android.content.Context;
import android.content.res.Configuration;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * Created by jsgyrcb-yk on 2019/12/7 0007.
 */
public class MyUtils {

    /**
     * 匹配两个字符串是否相等
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean equalsStr(String string1, String string2) {
        String[] str1 = string1.split(",");
        String[] str2 = string2.split(",");
        Arrays.sort(str1);
        Arrays.sort(str2);
        String res1 = Arrays.toString(str1);
        String res2 = Arrays.toString(str2);
        return res1.equals(res2);
    }

    /**
     * 时间格式转换
     *
     * @return 返回时间格式  yyyy-MM-dd HH:mm:ss
     */
    public static String timeYMDHMinSFigure() {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        re_StrTime = sdf.format(new Date(System.currentTimeMillis()));
        return re_StrTime;
    }

    /**
     * 时间格式转换
     *
     * @param time 需要转换的时间戳
     * @return 返回时间格式  yyyy-MM-dd HH:mm:ss
     */
    public static String timeYMDHMinSFigure(long time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        re_StrTime = sdf.format(new Date(time));
        return re_StrTime;
    }

    /**
     * 判断字符串是否是空
     *
     * @param str
     * @return
     */
    public static boolean strIsNull(String str) {
        if (str == null)
            return true;
        if (str.equals("null"))
            return true;
        if (str.isEmpty())
            return true;
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param strFolder 文件路径
     * @return
     */
    public static boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdir()) {
                return true;
            } else
                return false;
        }
        return true;
    }

    /**
     * 设置单选框是否可以点击
     *
     * @param testRadioGroup
     * @param isClick
     */
    public static void radioGroupIsClick(RadioGroup testRadioGroup, boolean isClick) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 校验身份证是否合法
     *
     * @param sfz 身份证
     * @return
     */
    public static boolean isSfzTrue(String sfz) {
//        // 对身份证号进行长度等简单判断
//        if (sfz == null || sfz.length() != 18 || !sfz.matches("\\d{17}[0-9X]")) {
//            return false;
//        }
//        // 1-17位相乘因子数组
//        int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
//        // 18位随机码数组
//        char[] random = "10X98765432".toCharArray();
//        // 计算1-17位与相应因子乘积之和
//        int total = 0;
//        for (int i = 0; i < 17; i++) {
//            total += Character.getNumericValue(sfz.charAt(i)) * factor[i];
//        }
//        // 判断随机码是否相等
//        return random[total % 11] == sfz.charAt(17);


        if (sfz == null || sfz.length() != 18) {
            return false;
        }
        //身份证格式的正则校验
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!sfz.matches(reg)) {
            return false;
        }
        //身份证最后一位的校验算法
        char[] id = sfz.toCharArray();
        int sum = 0;
        int w[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] ch = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        for (int i = 0; i < id.length - 1; i++) {
            sum += (id[i] - '0') * w[i];
        }
        int c = sum % 11;
        char code = ch[c];
        char last = id[id.length - 1];
        last = last == 'x' ? 'X' : last;
        return last == code;
    }

    /**
     * 根据正则表达式判断字符是否为汉字
     * 字符串中包含汉字时返回true
     */
    public static boolean hasChinese(String value) {
        // 汉字的Unicode取值范围
        String regex = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(value);
        return match.find();
    }

    /**
     * 判断手机是否root，不弹出root请求框<br/>
     */
    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        if (new File(xBinPath).exists() && isExecutable(xBinPath))
            return true;
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            LogUtil.i("isExecutable", str);
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

//    //判断当前界面显示的是哪个Activity
//    public static String getTopActivity(Context context){
//        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
//        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
////        Log.d("测试", "pkg:"+cn.getPackageName());//包名
////        Log.d("测试", "cls:"+cn.getClassName());//包名加类名
//        return cn.getClassName();
//    }
}
