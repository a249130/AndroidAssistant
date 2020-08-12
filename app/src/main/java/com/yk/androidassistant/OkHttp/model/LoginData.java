package com.yk.androidassistant.OkHttp.model;


/**
 * 登录信息 data数据
 * Created by jsgyrcb-yk on 2019/12/10 0010.
 */
public class LoginData {
    private String jgm; //机构码
//    private List<MenuCode> menucode; //菜单
    private String jsbh; //角色编号
    private String ygxm; //姓名
    private String jgmc; //机构名称
    private String ygh; //员工号
    private String ismrpwd; //密码轻度是否符合   1：不符合（弱密码）
//    private String isrlsb; //是否需要人脸识别  0：不需要  1：需要

    public String getJgm() {
        return jgm;
    }

    public void setJgm(String jgm) {
        this.jgm = jgm;
    }

//    public List<MenuCode> getMenucode() {
//        return menucode;
//    }
//
//    public void setMenucode(List<MenuCode> menucode) {
//        this.menucode = menucode;
//    }

    public String getJsbh() {
        return jsbh;
    }

    public void setJsbh(String jsbh) {
        this.jsbh = jsbh;
    }

    public String getYgxm() {
        return ygxm;
    }

    public void setYgxm(String ygxm) {
        this.ygxm = ygxm;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getYgh() {
        return ygh;
    }

    public void setYgh(String ygh) {
        this.ygh = ygh;
    }

    public String getIsmrpwd() {
        return ismrpwd;
    }

    public void setIsmrpwd(String ismrpwd) {
        this.ismrpwd = ismrpwd;
    }

//    public String getIsrlsb() {
//        return isrlsb;
//    }
//
//    public void setIsrlsb(String isrlsb) {
//        this.isrlsb = isrlsb;
//    }
}
