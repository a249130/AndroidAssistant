package com.yk.androidassistant.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.yk.androidassistant.Dialog.QMUIDialog.MessageDialogBuilderUtil;
import com.yk.androidassistant.R;
import com.yk.androidassistant.Util.ActivityCollector;
import com.yk.androidassistant.Util.LogUtil;
import com.yk.androidassistant.Util.MyUtils;
import com.yk.androidassistant.Util.PermissionUtil;
import com.yk.androidassistant.Util.SPManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class BaseQMUIActivity extends QMUIActivity {
    public final String TAG = this.getClass().getSimpleName();
    //全局变量
    public MyApplication application;
    //网络状态广播
    NetworkState networkState;
    //是否显示标题栏
//    private boolean isShowTitle = true;
    //是否显示状态栏
//    private boolean isShowStatusBar = true;

    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止截屏和录屏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        //activity管理
        ActivityCollector.addActivity(this);
//        if (!isShowTitle) {
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (getSupportActionBar() != null)
//                getSupportActionBar().hide();
//        } else { //显示title
//            if (getSupportActionBar() != null)
//                //左上角添加返回按钮
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        if (!isShowStatusBar)
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }

        //初始化布局
        setContentView(initLayout());
        //初始化mTopBar
        mTopBar = findViewById(R.id.topbar);
        //初始化全局变量
        application = (MyApplication) getApplication();
        //初始化SharedPreferences
        SPManager.initializeInstance(this);
        //初始化控件
        initView();
        //初始化网络广播
        networkState = new NetworkState();
        //注册无序广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkState, filter);
        //权限申请
        permissionApplication();

        if (MyUtils.isRoot()) {
            MessageDialogBuilderUtil.MessageDialogFinishActivity(this,
                    "提示框",
                    "系统检测到您当前使用的设备已经获取超级权限，为了确保数据安全请更换设备！",
                    "退出");
        }

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTopBar.setTitle(title);
    }

    List<String> permissionList; //需要申请权限的列表
    List<String> falseList; //未授权的权限列表
    List<String> showFalseList; //不再提醒的权限列表

    private static final int NOT_SHOW_PERMISSION = 1000; //不再提醒权限

    //权限申请
    private void permissionApplication() {
        LogUtil.d(TAG, "permissionApplication: " + Build.VERSION.SDK_INT);
        //判断系统版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionList == null)
                permissionList = new ArrayList<>();
            else
                permissionList.clear();
//            permissionList.add(Manifest.permission.CAMERA); //相机
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION); //位置
//            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION); //位置
//            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE); //存储
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE); //存储
            if (falseList == null)
                falseList = new ArrayList<>(); //未授权的权限列表
            else
                falseList.clear();
            if (showFalseList == null)
                showFalseList = new ArrayList<>(); //不再提醒的权限列表
            else
                showFalseList.clear();
            //批量申请权限
            for (String permission : permissionList) {
                //判断是否已经赋予权限
                boolean isTrue = ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
                LogUtil.d(TAG, "permission: " + permission + "-->" + isTrue);
                if (!isTrue) {
                    falseList.add(permission);
                }
            }
            if (falseList.size() == 0) { //没有需要授权的权限
                //所有权限已全部授权
                permission(true);
            } else { //有需要授权的权限
                if (showFalseList.size() != 0) { //有不再提醒的权限
                    showApplyAlertDialog();
                    return;
                }
                //申请权限
                ActivityCompat.requestPermissions(this, falseList.toArray(new String[falseList.size()]), 1);
            }
        } else {
            permission(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "onActivityResult-->requestCode:" +
                requestCode + ",resultCode:" + resultCode);
        if (requestCode == NOT_SHOW_PERMISSION) {
            //由于不知道是否选择了允许  所以需要再次判断
            permissionApplication();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            falseList.clear();
            showFalseList.clear();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) { //不给授权
                    String permission = permissions[i];
                    //判断该权限用户是否点击了不再提醒
                    //如果用户勾选了不再提醒，则返回false
                    //给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限打开
                    boolean isShowTrue = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                    LogUtil.d(TAG, "isShowTrue: " + permission + "-->" + isShowTrue);
                    if (!isShowTrue) { //有不再提醒的权限
                        showFalseList.add(permission);
                    }
                    falseList.add(permission);
                }
            }

            if (showFalseList.size() == 0) { //没有不再提醒的权限
                if (falseList.size() == 0) { //全部授权成功
                    //所有权限已全部授权
                    permission(true);
                } else {
                    //重新授权
                    permissionApplication();
                }
            } else { //有不再提醒的权限
                showApplyAlertDialog();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 权限是否全部ok
     */
    protected void permission(boolean isOk) {
    }

    /**
     * 权限申请提示框
     */
    public void showApplyAlertDialog() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("权限申请")
                .setMessage("您拒绝了某些权限，这将不能继续使用本软件！")
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        ActivityCollector.finishAll();
                        System.exit(0);
                    }
                })
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .addAction(0, "去允许", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        //跳转到权限申请界面
                        startActivityForResult(PermissionUtil.gotoPermission(BaseQMUIActivity.this),
                                NOT_SHOW_PERMISSION);
                    }
                })
                .show();
    }

    QMUITipDialog tipDialog;

    //加载提示框
    public void loading(String message) {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(message)
                    .create();
            tipDialog.setCanceledOnTouchOutside(false);
            tipDialog.setCancelable(false);
        }
//        tipDialog.setTipWord(message);
        tipDialog.show();
    }

    //关闭加载框
    public void loadDismiss() {
        if (tipDialog != null && tipDialog.isShowing())
            tipDialog.dismiss();
    }

//    //加载失败提示框
//    public void loadError(String message) {
//        QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
//                .setTipWord(message)
//                .create();
//        tipDialog.show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播
        if (networkState != null)
            unregisterReceiver(networkState);
        //activity管理
        ActivityCollector.remnoveActivity(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //第一个int类型的group ID参数：代表的是组概念，你可以将几个菜单项归为一组，以便更好的以组的方式管理你的菜单按钮。
//        //第二个int类型的item ID参数：代表的是项目编号。这个参数非常重要，一个item ID对应一个menu中的选项。在后面使用菜单的时候，就靠这个item ID来判断你使用的是哪个选项。
//        //第三个int类型的order ID参数：代表的是菜单项的显示顺序。默认是0，表示菜单的显示顺序就是按照add的显示顺序来显示。
//        //第四个String类型的title参数：表示选项中显示的文字。
//        menu.add(Menu.NONE, 0, 0, "关于");
//        menu.add(Menu.NONE, 1, 1, "修改密码");
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case 0: //关于
//                about();
//                break;
//            case 1: //修改密码
//                updatePwd();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 关于 提示版本信息
     */
    public void about() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("版本信息")
                .setMessage("版本号：" + application.version + "\n" +
                        "更新内容：" + application.versionInfo.replaceAll("br", "\n"))
                .addAction(0, "关闭", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 回调网络状态
     *
     * @param state
     * @param name
     */
    protected void netState(boolean state, String name) {
        if (!name.equals("WIFI") && !name.equals("当前没有网络")) {
            if (!application.isToastNetState)
                setToast("当前非WIFI环境，请注意流量消耗");
            application.isToastNetState = true;
        } else {
            application.isToastNetState = false;
        }
    }

    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract int initLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

//    /**
//     * 设置是否显示标题
//     *
//     * @param showTitle
//     */
//    public void setShowTitle(boolean showTitle) {
//        isShowTitle = showTitle;
//    }
//
//    /**
//     * 设置是否显示状态栏
//     *
//     * @param showStatusBar
//     */
//    public void setShowStatusBar(boolean showStatusBar) {
//        isShowStatusBar = showStatusBar;
//    }

    /**
     * toast提示信息
     *
     * @param message 显示的信息
     */
    public void setToast(String message) {
        LogUtil.d(TAG, "setToast: " + message);
        if (!message.isEmpty()) //message信息不为空
            application.setToast(message);
    }

    /**
     * 保证同一按钮在1秒内只响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        //最后点击时间
        private long lastClickTime = 0;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = nowTime;
                onSingleClick(view);
            } else {
                setToast("您已经点击过了");
            }
        }
    }

    /**
     * 根据输入法的状态显示和隐藏输入法
     */
    public static void autoInputmethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        //得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isSoftShowing()) { //如果开启
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }


//        View view = getCurrentFocus();
//        if (view != null) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
    }

    /**
     * 判断软键盘是否显示方法
     *
     * @return
     */

    public boolean isSoftShowing() {
        //获取当屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom + getSoftButtonsBarHeight();
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    //实时获取网络状态的广播
    class NetworkState extends BroadcastReceiver {
        //监视网络状态发生改变  并发出广播通知
        ConnectivityManager connectivityManager;
        //描述当前网络状态
        NetworkInfo info;

        @Override
        public void onReceive(Context context, Intent intent) {
            //当前网络发出的广播是有网络的
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //实例化connectivityManager
                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //实例化网络状态
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    String name = info.getTypeName();
//                    application.setToast("当前网络名称：" + name);
                    netState(true, name);
                } else {
//                    application.setToast("当前没有网络");
                    netState(false, "当前没有网络");
                }
            }
        }

    }

    //获取crc32
    public Long crc32() {
        String apkPath = getPackageCodePath();
        Long dexCrc = Long.parseLong("000");
        try {
            ZipFile zipfile = new ZipFile(apkPath);
            ZipEntry dexentry = zipfile.getEntry("classes.dex");
            LogUtil.d("verification", "classes.dexcrc=" + dexentry.getCrc());
            dexCrc = dexentry.getCrc();
//            if (dexentry.getCrc() != dexCrc) {
//                LogUtil.d("verification", "Dexhas been modified!");
//            } else {
//                LogUtil.d("verification", "Dex hasn't been modified!");
//            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dexCrc;
    }

    //获取sha1
    public String sha1() {
        String apkPath = getPackageCodePath();
        MessageDigest msgDigest = null;
        String sha = "";
        try {
            msgDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = new byte[1024];
            int byteCount;
            FileInputStream fis = new FileInputStream(new File(apkPath));
            while ((byteCount = fis.read(bytes)) > 0) {
                msgDigest.update(bytes, 0, byteCount);
            }
            BigInteger bi = new BigInteger(1, msgDigest.digest());
            sha = bi.toString(16).toUpperCase();
            fis.close();
            //这里添加从服务器中获取哈希值然后进行对比校验
            //f6f5170ef5fed89d922587860ce46ecaab2a2523
            LogUtil.d("verification", "sha-->" + sha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha;
    }

    /**
     * 遮罩层提示
     */
    QMUIPopup mNormalPopup;

    public void showTip(View v, String textStr) {
        TextView textView = new TextView(this);
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(this, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(this, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(textStr);
//        textView.setTextColor(
//                QMUIResHelper.getAttrColor(this, R.attr.app_skin_common_title_text_color));
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        builder.textColor(R.attr.app_skin_common_title_text_color);
        QMUISkinHelper.setSkinValue(textView, builder);
        builder.release();
        mNormalPopup = QMUIPopups.popup(this, QMUIDisplayHelper.dp2px(this, 250))
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .view(textView)
                .skinManager(QMUISkinManager.defaultInstance(this))
                .edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 5))
                .shadow(true)
                .arrow(true)
                .dimAmount(0.5f)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        Toast.makeText(this, "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .show(v);
    }

    /**
     * 输入框禁止录入  禁止粘贴
     *
     * @param editText
     */
    public void editTextNoEntry(EditText editText) {
        editText.setKeyListener(null); //禁止长按粘贴
//        editText.setEnabled(false);
        editText.setBackground(null);
        editText.setFocusable(false);
    }

}
