package com.yk.androidassistant.Util;

import java.util.ArrayList;
import java.util.List;

//农户信息字典类
public class NhxxDictionaries {
    private static final String TAG = "NhxxDictionaries";
    public static final String SPLITE = "_";

    //性别
    private static List<String> sexList;

    public static final List<String> sexList() {
        if (sexList == null) {
            sexList = new ArrayList<>();
//            sexList.add("-1" + SPLITE + "请选择");
            sexList.add("0" + SPLITE + "未说明性别");
            sexList.add("1" + SPLITE + "男");
            sexList.add("2" + SPLITE + "女");
        }
        return sexList;
    }

    //婚姻
    private static List<String> hyList;

    public static final List<String> hyList() {
        if (hyList == null) {
            hyList = new ArrayList<>();
//            hyList.add("-1" + SPLITE + "请选择");
            hyList.add("0" + SPLITE + "未婚");
            hyList.add("1" + SPLITE + "已婚");
            hyList.add("2" + SPLITE + "离异/丧偶无子女");
            hyList.add("3" + SPLITE + "离异/丧偶有子女");
            hyList.add("4" + SPLITE + "未知");
        }
        return hyList;
    }

    //职业
    private static List<String> zyList;

    public static final List<String> zyList() {
        if (zyList == null) {
            zyList = new ArrayList<>();
//            zyList.add("-1" + SPLITE + "请选择");
            zyList.add("0" + SPLITE + "国家机关、党群组织、企事业单位负责人");
            zyList.add("1" + SPLITE + "专业技术人员");
            zyList.add("2" + SPLITE + "一般工作人员");
            zyList.add("3" + SPLITE + "商业服务人员");
            zyList.add("4" + SPLITE + "农业生产人员");
            zyList.add("5" + SPLITE + "军人");
            zyList.add("6" + SPLITE + "其他人员");
        }
        return zyList;
    }

    //文化程度
    private static List<String> whcdList;

    public static final List<String> whcdList() {
        if (whcdList == null) {
            whcdList = new ArrayList<>();
//            whcdList.add("-1" + SPLITE + "请选择");
            whcdList.add("0" + SPLITE + "研究生及以上");
            whcdList.add("1" + SPLITE + "大学本科");
            whcdList.add("2" + SPLITE + "大学专科和专科学校");
            whcdList.add("3" + SPLITE + "中等专业学校或中等技术学校");
            whcdList.add("4" + SPLITE + "高中");
            whcdList.add("5" + SPLITE + "初中");
            whcdList.add("6" + SPLITE + "小学");
            whcdList.add("7" + SPLITE + "其他");
        }
        return whcdList;
    }

    //健康状况
    private static List<String> jkzkList;

    public static final List<String> jkzkList() {
        if (jkzkList == null) {
            jkzkList = new ArrayList<>();
//            jkzkList.add("-1" + SPLITE + "请选择");
            jkzkList.add("0" + SPLITE + "健康");
            jkzkList.add("1" + SPLITE + "健康状况较差");
            jkzkList.add("2" + SPLITE + "有重大疾病");
        }
        return jkzkList;
    }

    //家庭关系
    private static List<String> jtgxList;

    public static final List<String> jtgxList() {
        if (jtgxList == null) {
            jtgxList = new ArrayList<>();
//            jtgxList.add("-1" + SPLITE + "请选择");
            jtgxList.add("1" + SPLITE + "本人");
            jtgxList.add("2" + SPLITE + "父母（岳父母）");
            jtgxList.add("3" + SPLITE + "儿子（女儿）");
            jtgxList.add("4" + SPLITE + "妻子（丈夫）");
            jtgxList.add("5" + SPLITE + "其他");
        }
        return jtgxList;
    }

    //房屋性质
    private static List<String> fwxzList;

    public static final List<String> fwxzList() {
        if (fwxzList == null) {
            fwxzList = new ArrayList<>();
//            fwxzList.add("-1" + SPLITE + "请选择");
            fwxzList.add("1" + SPLITE + "生产用房");
            fwxzList.add("2" + SPLITE + "农村生活用房");
            fwxzList.add("3" + SPLITE + "城镇商品房");
        }
        return fwxzList;
    }

    //房屋抵押状态
    private static List<String> dyztList;

    public static final List<String> dyztList() {
        if (dyztList == null) {
            dyztList = new ArrayList<>();
//            fwdyztList.add("-1" + SPLITE + "请选择");
            dyztList.add("0" + SPLITE + "没有抵押");
            dyztList.add("1" + SPLITE + "已抵押");
            dyztList.add("2" + SPLITE + "已注销");
        }
        return dyztList;
    }

    /**
     * id转str和str转id的转换器处理
     * str==null id转str
     * str!=null str转id
     *
     * @param id
     * @param str
     * @return
     */
    public static Object idStrHandler(List<String> list, int id, String str) {
        Object object;
        if (str == null)
            object = "请选择";
        else
            object = "-1";
        for (String string : list) {
            String[] strs = string.split(SPLITE);
            if (str == null) { //id转str
                if (Integer.parseInt(strs[0]) == id)
                    return strs[1];
            } else { //str转id
                if (strs[1].equals(str))
                    return strs[0];
            }
        }
        return object;
    }
}
