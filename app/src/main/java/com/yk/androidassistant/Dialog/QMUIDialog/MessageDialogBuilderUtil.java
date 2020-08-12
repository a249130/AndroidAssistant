package com.yk.androidassistant.Dialog.QMUIDialog;

import android.app.Activity;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class MessageDialogBuilderUtil {

    /**
     * 不允许使用本APP的提示,点按钮退出应用
     * 已获取超级权限提示
     *
     * @param activity
     * @param title
     * @param message
     * @param butName
     */
    public static void MessageDialogFinishActivity(final Activity activity, String title, String message, String butName) {
        new QMUIDialog.MessageDialogBuilder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addAction(0, butName, QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        activity.finish();
                    }
                })
                .show();
    }

    /**
     * 点按钮只是关闭dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param butName
     */
    public static void MessageDialogFinishActivity2(final Activity activity, String title, String message, String butName) {
        new QMUIDialog.MessageDialogBuilder(activity)
                .setTitle(title)
                .setMessage(message)
                .addAction(0, butName, QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 两个按钮的dialog
     * 此dialog点返回键，点其他地方不退出dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param butName1
     * @param actionListener1
     * @param butName2
     * @param actionListener2
     */
    public static void MessageDialogActivity(final Activity activity, String title,
                                             String message,
                                             String butName1,
                                             QMUIDialogAction.ActionListener actionListener1,
                                             String butName2,
                                             QMUIDialogAction.ActionListener actionListener2) {
        new QMUIDialog.MessageDialogBuilder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addAction(0, butName1,
                        QMUIDialogAction.ACTION_PROP_NEGATIVE, actionListener1)
                .addAction(0, butName2,
                        QMUIDialogAction.ACTION_PROP_NEUTRAL, actionListener2)
                .show();
    }
}
