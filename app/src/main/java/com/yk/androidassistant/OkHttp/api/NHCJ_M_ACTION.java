//package com.yk.androidassistant.OkHttp.api;
//
//import com.yk.androidassistant.OkHttp.httpUtils;
//import com.yk.androidassistant.Util.LogUtil;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import okhttp3.Response;
//
///**
// * 农户信息
// * Created by yk on 2020/7/27 0027.
// */
//public class NHCJ_M_ACTION {
//    private static final String TAG = "NHCJ_M_ACTION";
//    private static final String URL_HEAD = "NHCJ_M_ACTION/";
//
//    /**
//     * 农户信息【查询】列表数据
//     *
//     * @param NH_CRED 身份证
//     * @param nowPage 下一页
//     * @return 返回数据
//     */
//    public static String getNhcjList(String NH_CRED, int nowPage) throws JSONException, IOException {
//        String res;
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("NH_CRED", NH_CRED);
//        jsonObject.put("nowPage", nowPage);
//        Response response = httpUtils.doPost(URL_HEAD + "/getNhcjList", jsonObject.toString());
//        if (response.isSuccessful()) { //数据获取成功
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "getNhcjList:res-->" + res);
//        return res;
//    }
//
//    /**
//     * 农户采集列表数据处理
//     *
//     * @param rows 数据
//     * @return
//     */
//    public static List<NhxxModel> nhcjListHandler(String rows) throws Exception {
//        List<NhxxModel> list = new ArrayList<>();
//        JSONArray array = new JSONArray(rows);
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            NhxxModel model = new NhxxModel();
//            model.setID(httpUtils.nullHandler(object.optString("ID", "")));
//            model.setNH_NAME(httpUtils.nullHandler(object.optString("NH_NAME", "")));
//            model.setNH_CRED(httpUtils.nullHandler(object.optString("NH_CRED", "")));
//            model.setNH_AREA_1(httpUtils.nullHandler(object.optString("NH_AREA_1", "")));
//            model.setNH_AREANAME_1(httpUtils.nullHandler(object.optString("NH_AREANAME_1", "")));
//            model.setNH_AREA_2(httpUtils.nullHandler(object.optString("NH_AREA_2", "")));
//            model.setNH_AREANAME_2(httpUtils.nullHandler(object.optString("NH_AREANAME_2", "")));
//            model.setNH_AREA_3(httpUtils.nullHandler(object.optString("NH_AREA_3", "")));
//            model.setNH_AREANAME_3(httpUtils.nullHandler(object.optString("NH_AREANAME_3", "")));
//            model.setNH_AREA_4(httpUtils.nullHandler(object.optString("NH_AREA_4", "")));
//            model.setNH_AREANAME_4(httpUtils.nullHandler(object.optString("NH_AREANAME_4", "")));
//            model.setNH_AREA_5(httpUtils.nullHandler(object.optString("NH_AREA_5", "")));
//            model.setNH_AREANAME_5(httpUtils.nullHandler(object.optString("NH_AREANAME_5", "")));
//            model.setNH_AREA_6(httpUtils.nullHandler(object.optString("NH_AREA_6", "")));
//            model.setNH_AREANAME_6(httpUtils.nullHandler(object.optString("NH_AREANAME_6", "")));
//            model.setNH_RES_ADDR(httpUtils.nullHandler(object.optString("NH_RES_ADDR", "")));
//            model.setNH_CJR_CODE(httpUtils.nullHandler(object.optString("NH_CJR_CODE", "")));
//            model.setNH_CJR_NAME(httpUtils.nullHandler(object.optString("NH_CJR_NAME", "")));
//            list.add(model);
//        }
//        return list;
//    }
//
//    /**
//     * 插入数据
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String insertNhcjData(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/insertNhcjData", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "insertNhcjData: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 查看详情根据身份证查询数据
//     *
//     * @return sfz 身份证
//     * @throws IOException
//     */
//    public static String getNhcjDetail(String sfz) throws Exception {
//        String res;
//        JSONObject object = new JSONObject();
//        object.put("NH_CRED", sfz);
//        Response response = httpUtils.doPost(
//                URL_HEAD + "/getNhcjDetail", object.toString());
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "getNhcjDetail: " + res + "\r\n" + object.toString());
//        return res;
//    }
//
//    /**
//     * 详情数据处理
//     *
//     * @param rows 数据
//     * @return
//     */
//    public static NhxxModel nhcjDetailHandler(String rows) throws Exception {
//        NhxxModel model = new NhxxModel();
//        JSONObject object = new JSONObject(rows);
//        JSONObject baseObject = new JSONObject(object.getString("BASE_INFO"));
//        //基本信息（基础资料、资产负债、收入支出）
//        model.setNH_NAME(httpUtils.nullHandler(baseObject.optString("NH_NAME", ""))); //海州区",
//        model.setNH_AREA_5(httpUtils.nullHandler(baseObject.optString("NH_AREA_5", ""))); //下车村",
//        model.setNH_AREANAME_5(httpUtils.nullHandler(baseObject.optString("NH_AREANAME_5", ""))); //下车村",
//        model.setNH_AREA_6(httpUtils.nullHandler(baseObject.optString("NH_AREA_6", ""))); //下车村",
//        model.setNH_AREANAME_6(httpUtils.nullHandler(baseObject.optString("NH_AREANAME_6", ""))); //下车村",
//        model.setNH_CJR_ORGNAME(httpUtils.nullHandler(baseObject.optString("NH_CJR_ORGNAME", ""))); //下车支行",
//        model.setNH_AREA_2(httpUtils.nullHandler(baseObject.optString("NH_AREA_2", ""))); //连云港市",
//        model.setNH_AREANAME_2(httpUtils.nullHandler(baseObject.optString("NH_AREANAME_2", ""))); //连云港市",
//        model.setNH_AREA_1(httpUtils.nullHandler(baseObject.optString("NH_AREA_1", ""))); //江苏省",
//        model.setNH_AREANAME_1(httpUtils.nullHandler(baseObject.optString("NH_AREANAME_1", ""))); //江苏省",
//        model.setNH_OTHER_ASSETS(baseObject.optInt("NH_OTHER_ASSETS", -1)); //
//        model.setNH_AREA_4(httpUtils.nullHandler(baseObject.optString("NH_AREA_4", ""))); //同兴镇",
//        model.setNH_AREANAME_4(httpUtils.nullHandler(baseObject.optString("NH_AREANAME_4", ""))); //同兴镇",
//        model.setNH_AREA_3(httpUtils.nullHandler(baseObject.optString("NH_AREA_3", ""))); //灌云县",
//        model.setNH_AREANAME_3(httpUtils.nullHandler(baseObject.optString("NH_AREANAME_3", ""))); //灌云县",
//        model.setNH_NF_PAY(baseObject.optInt("NH_NF_PAY", -1)); //
//        model.setNH_CJR_ORGCODE(httpUtils.nullHandler(baseObject.optString("NH_CJR_ORGCODE", ""))); //32-1723-111",
//        model.setNH_CJ_TIME(httpUtils.nullHandler(baseObject.optString("NH_CJ_TIME", ""))); //",
//        model.setNH_HEALTH_COND(baseObject.optInt("NH_HEALTH_COND", -1)); //-1",
//        model.setNH_FAMILY_MOV(baseObject.optInt("NH_FAMILY_MOV", -1)); //
//        model.setNH_FAMILY_GET(baseObject.optInt("NH_FAMILY_GET", -1)); //
//        model.setNH_CJ_STATUS(baseObject.optInt("NH_CJ_STATUS", -1)); //2",
//        model.setNH_CJR_CODE(httpUtils.nullHandler(baseObject.optString("NH_CJR_CODE", ""))); //-1-12",
//        model.setID(httpUtils.nullHandler(baseObject.optString("ID", ""))); //-11-1-11-1-1-1-15",
//        model.setNH_CRED(httpUtils.nullHandler(baseObject.optString("NH_CRED", ""))); //32-17231998-1215-1231",
//        model.setNH_REAL(baseObject.optInt("NH_REAL", -1)); //2",
//        model.setNH_AGE(baseObject.optInt("NH_AGE", -1)); //457",
//        model.setNH_FAMILY_ASSETS(baseObject.optInt("NH_FAMILY_ASSETS", -1)); //2",
//        model.setNH_CJR_NAME(httpUtils.nullHandler(baseObject.optString("NH_CJR_NAME", ""))); //测试2",
//        model.setNH_SEX(baseObject.optInt("NH_SEX", -1)); //1",
//        model.setNH_FAMILY_NUM(baseObject.optInt("NH_FAMILY_NUM", -1)); //3",
//        model.setNH_XG_TIME(httpUtils.nullHandler(baseObject.optString("NH_XG_TIME", ""))); //",
//        model.setNH_NF_GET(baseObject.optInt("NH_NF_GET", -1)); //2",
//        model.setNH_RES_ADDR(httpUtils.nullHandler(baseObject.optString("NH_RES_ADDR", ""))); //6组",
//        model.setNH_FAMILY_LIABILITIES(baseObject.optInt("NH_FAMILY_LIABILITIES", -1)); //2",
//        model.setNH_F_PAY(baseObject.optInt("NH_F_PAY", -1)); //2",
//        model.setNH_MARITAL_STATUS(baseObject.optInt("NH_MARITAL_STATUS", -1)); //-1",
//        model.setNH_OCCUPATION(baseObject.optInt("NH_OCCUPATION", -1)); //2",
//        model.setNH_CONTACT_NUM(httpUtils.nullHandler(baseObject.optString("NH_CONTACT_NUM", ""))); //11-111-111-1",
//        model.setNH_XGR_CODE(httpUtils.nullHandler(baseObject.optString("NH_XGR_CODE", ""))); //",
//        model.setNH_XGR_NAME(httpUtils.nullHandler(baseObject.optString("NH_XGR_NAME", ""))); //",
//        model.setNH_EDU_LEVEL(baseObject.optInt("NH_EDU_LEVEL", -1)); //6",
//        model.setNH_FAMILY_PAY(baseObject.optInt("NH_FAMILY_PAY", -1)); //2",
//        model.setNH_F_GET(baseObject.optInt("NH_F_GET", -1)); //2",
//        //家庭关系
//        List<JtgxModel> jtgxModelList = new ArrayList<>();
//        JSONArray jtgxArray = new JSONArray(object.getString("RELA_INFO"));
//        for (int i = 0; i < jtgxArray.length(); i++) {
//            JSONObject jtgxObject = jtgxArray.getJSONObject(i);
//            JtgxModel jtgxModel = new JtgxModel();
//            jtgxModel.setID(jtgxObject.getString("ID"));
//            jtgxModel.setNH_NAME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_NAME", "")));
//            jtgxModel.setNH_XGR_CODE(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_XGR_CODE", "")));
//            jtgxModel.setNH_CRED(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_CRED", "")));
//            jtgxModel.setNH_XGR_NAME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_XGR_NAME", "")));
//            jtgxModel.setNH_HZ_CY_REL(jtgxObject.optInt("NH_HZ_CY_REL", -1));
//            jtgxModel.setNH_EDU_LEVEL(jtgxObject.optInt("NH_EDU_LEVEL", -1));
//            jtgxModel.setNH_CYCRED(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_CYCRED", "")));
//            jtgxModel.setNH_CYNAME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_CYNAME", "")));
//            jtgxModel.setNH_XG_TIME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_XG_TIME", "")));
//            jtgxModel.setNH_OCCUPATION(jtgxObject.optInt("NH_OCCUPATION", -1));
//            jtgxModelList.add(jtgxModel);
//        }
//        model.setJtgxList(jtgxModelList);
//        //房屋信息
//        List<FwxxModel> fwxxModelList = new ArrayList<>();
//        JSONArray fwxxArray = new JSONArray(object.getString("HOUSE_INFO"));
//        for (int i = 0; i < fwxxArray.length(); i++) {
//            JSONObject jtgxObject = fwxxArray.getJSONObject(i);
//            FwxxModel fwxxModel = new FwxxModel();
//            fwxxModel.setID(jtgxObject.getString("ID"));
//            fwxxModel.setNH_NAME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_NAME", "")));
//            fwxxModel.setNH_CRED(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_CRED", "")));
//            fwxxModel.setNH_SYQ_NAME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_SYQ_NAME", "")));
//            fwxxModel.setNH_SYQ_CRED(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_SYQ_CRED", "")));
//            fwxxModel.setNH_HOUSES_CERT(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_HOUSES_CERT", "")));
//            fwxxModel.setNH_HOUSES_PROPERTY(jtgxObject.optInt("NH_HOUSES_PROPERTY", -1));
//            fwxxModel.setNH_HOUSES_AREA(jtgxObject.optInt("NH_HOUSES_AREA", -1));
//            fwxxModel.setNH_HOUSES_ADDR(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_HOUSES_ADDR", "")));
//            fwxxModel.setNH_HOUSES_MORTAGE(jtgxObject.optInt("NH_HOUSES_MORTAGE", -1));
//            fwxxModel.setNH_XGR_CODE(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_XGR_CODE", "")));
//            fwxxModel.setNH_XGR_NAME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_XGR_NAME", "")));
//            fwxxModel.setNH_XG_TIME(httpUtils.nullHandler(
//                    jtgxObject.optString("NH_XG_TIME", "")));
//            fwxxModelList.add(fwxxModel);
//        }
//        model.setFwxxList(fwxxModelList);
//        return model;
//    }
//
//    /**
//     * 根据身份证修改农户信息（基本信息、资产负债、收入支出）
//     *
//     * @return json （基本信息、资产负债、收入支出）
//     * @throws IOException
//     */
//    public static String updateNhcjByCred(String json) throws Exception {
//        String res;
//        Response response = httpUtils.doPost(
//                URL_HEAD + "/updateNhcjByCred", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "updateNhcjByCred: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 删除农户数据
//     *
//     * @return sfz 身份证
//     * @throws IOException
//     */
//    public static String deletByCred(String sfz) throws Exception {
//        String res;
//        JSONObject object = new JSONObject();
//        object.put("NH_CRED", sfz);
//        Response response = httpUtils.doPost(
//                URL_HEAD + "/deletByCred", object.toString());
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "deletByCred: " + res + "\r\n" + object.toString());
//        return res;
//    }
//
//    /**
//     * 家庭关系 数据插入
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String insertOneJTGX(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/insertOneJTGX", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "insertOneJTGX: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 根据户主身份证【修改】农户信息：家庭关系
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String getJtgxList(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/getJtgxList", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "getJtgxList: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 家庭关系列表数据处理
//     *
//     * @param rows 数据
//     * @return
//     */
//    public static List<JtgxModel> jtgxListHandler(String rows) throws Exception {
//        List<JtgxModel> list = new ArrayList<>();
//        JSONArray array = new JSONArray(rows);
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            JtgxModel model = new JtgxModel();
//            model.setID(httpUtils.nullHandler(object.optString("ID", "")));
//            model.setNH_NAME(httpUtils.nullHandler(object.optString("NH_NAME", "")));
//            model.setNH_CRED(httpUtils.nullHandler(object.optString("NH_CRED", "")));
//            model.setNH_CYNAME(httpUtils.nullHandler(object.optString("NH_CYNAME", "")));
//            model.setNH_CYCRED(httpUtils.nullHandler(object.optString("NH_CYCRED", "")));
//            model.setNH_HZ_CY_REL(object.optInt("NH_HZ_CY_REL", -1));
//            model.setNH_EDU_LEVEL(object.optInt("NH_EDU_LEVEL", -1));
//            model.setNH_OCCUPATION(object.optInt("NH_OCCUPATION", -1));
//            model.setNH_XGR_CODE(httpUtils.nullHandler(object.optString("NH_XGR_CODE", "")));
//            model.setNH_XGR_NAME(httpUtils.nullHandler(object.optString("NH_XGR_NAME", "")));
//            model.setNH_XG_TIME(httpUtils.nullHandler(object.optString("NH_XG_TIME", "")));
//            list.add(model);
//        }
//        return list;
//    }
//
//    /**
//     * 根据户主身份证【修改】农户信息：家庭关系
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String updateJtgxByCred(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/updateJtgxByCred", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "updateJtgxByCred: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 根据身份证号【删除】农户信息：家庭关系
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String deleteJtgxByCred(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/deleteJtgxByCred", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "deleteJtgxByCred: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 房屋信息 数据插入
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String insertOneFWXX(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/insertOneFWXX", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "insertOneFWXX: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 根据户主身份证【修改】农户信息：房屋信息
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String getFcxxList(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/getFcxxList", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "getFcxxList: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 房屋信息列表数据处理
//     *
//     * @param rows 数据
//     * @return
//     */
//    public static List<FwxxModel> fwxxListHandler(String rows) throws Exception {
//        List<FwxxModel> list = new ArrayList<>();
//        JSONArray array = new JSONArray(rows);
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            FwxxModel model = new FwxxModel();
//            model.setID(httpUtils.nullHandler(object.optString("ID", "")));
//            model.setNH_NAME(httpUtils.nullHandler(object.optString("NH_NAME", "")));
//            model.setNH_CRED(httpUtils.nullHandler(object.optString("NH_CRED", "")));
//            model.setNH_SYQ_NAME(httpUtils.nullHandler(object.optString("NH_SYQ_NAME", "")));
//            model.setNH_SYQ_CRED(httpUtils.nullHandler(object.optString("NH_SYQ_CRED", "")));
//            model.setNH_HOUSES_CERT(httpUtils.nullHandler(object.optString("NH_HOUSES_CERT", "")));
//            model.setNH_HOUSES_PROPERTY(object.optInt("NH_HOUSES_PROPERTY", -1));
//            model.setNH_HOUSES_AREA(object.optInt("NH_HOUSES_AREA", -1));
//            model.setNH_HOUSES_ADDR(httpUtils.nullHandler(object.optString("NH_HOUSES_ADDR", "")));
//            model.setNH_HOUSES_MORTAGE(object.optInt("NH_HOUSES_MORTAGE", -1));
//            model.setNH_XGR_CODE(httpUtils.nullHandler(object.optString("NH_XGR_CODE", "")));
//            model.setNH_XGR_NAME(httpUtils.nullHandler(object.optString("NH_XGR_NAME", "")));
//            model.setNH_XG_TIME(httpUtils.nullHandler(object.optString("NH_XG_TIME", "")));
//            list.add(model);
//        }
//        return list;
//    }
//
//    /**
//     * 根据户主身份证【修改】农户信息：房产信息
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String updateFcxxByCred(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/updateFcxxByCred", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "updateFcxxByCred: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 根据身份证号【删除】农户信息：家庭关系
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String deleteFcxxByCred(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/deleteFcxxByCred", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "deleteFcxxByCred: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 根据身份证号【删除】农户信息：家庭关系
//     *
//     * @return json 数据
//     * @throws IOException
//     */
//    public static String getAreaList(String json) throws IOException {
//        String res;
//        Response response = httpUtils.doPost(URL_HEAD + "/getAreaList", json);
//        if (response.isSuccessful()) {
//            res = response.body().string();
//        } else {
//            res = "无法连接到服务器，请检查网络连接，code=" + response.code();
//        }
//        LogUtil.d(TAG, "getAreaList: " + res + "\r\n" + json);
//        return res;
//    }
//
//    /**
//     * 房屋信息列表数据处理
//     *
//     * @param rows 数据
//     * @return
//     */
//    public static String[] areaListHandler(String rows) throws Exception {
//        JSONArray array = new JSONArray(rows);
//        String[] items = new String[array.length()];
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            items[i] = object.getString("NH_AREA_CODE") +
//                    NhxxDictionaries.SPLITE +
//                    object.getString("NH_AREA_NAME");
//        }
//        return items;
//    }
//
//}