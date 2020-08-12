package com.yk.androidassistant.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.rh.nhcj.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 版本更新提示框
 * Created by jsgyrcb-yk on 2019/12/18 0018.
 */

public class VersionUpdateDialog extends AlertDialog.Builder {
    private static final String TAG = "VersionUpdateDialog";

    AlertDialog dialog;
    ProgressBar progressBar;

    Handler handler;

    public VersionUpdateDialog(@NonNull Context context) {
        super(context);
        setTitle("发现新版本");
        View view = LayoutInflater.from(context).inflate(R.layout.version_update_dialog, null);
        progressBar = view.findViewById(R.id.progressBar);
        setView(view);
        setIcon(android.R.drawable.ic_dialog_alert);
        setNegativeButton("退出", null);
        setPositiveButton("更新", null);

        dialog = create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setText(DialogInterface.BUTTON_POSITIVE, (String) msg.obj);
            }
        };
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    /**
     * 设置单击事件
     *
     * @param listener 事件
     */
    public void setOnClickListener(View.OnClickListener listener) {
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(listener);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(listener);
    }

    /**
     * 获取按钮的id
     *
     * @param button 按钮
     * @return
     */
    public int getButtonId(int button) {
        return dialog.getButton(button).getId();
    }

    /**
     * 设置按钮文字
     *
     * @param button 按钮
     * @param text   文字
     */
    public void setText(int button, String text) {
        dialog.getButton(button).setText(text);
    }

    /**
     * 获取按钮文字
     *
     * @param button 按钮
     * @return
     */
    public String getText(int button) {
        return dialog.getButton(button).getText().toString();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    //下载文件
    public void downloadFile(String url) {
//        final String url = "http://dlied5.myapp.com/myapp/1105999355/muses/10030965_com.tencent.tako.muses_h101_1.0.101_319e47.apk";
//        final long startTime = System.currentTimeMillis();
//        LogUtil.i(TAG,"startTime="+startTime);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "close")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //下载失败设置
                Message message = handler.obtainMessage();
                message.obj = "下载失败";
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
//                String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mapdata/download";
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(getContext().getExternalCacheDir(), "guanyunyunguan.apk");
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
//                        LogUtil.e(TAG,"download progress : " + progress);
                        setProgress(progress);
                    }
                    fos.flush();
                    //安装apk
                    installAPK();
                    //下载完成设置
                    Message message = handler.obtainMessage();
                    message.obj = "去安装";
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    //下载失败设置
                    Message message = handler.obtainMessage();
                    message.obj = "更新失败";
                    handler.sendMessage(message);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    //安装apk
//    private void installAPK() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.fromFile(new File(getContext().getExternalCacheDir(), "guanyunyunguan.apk")),
//                "application/vnd.android.package-archive");
//        getContext().startActivity(intent);
////        finish();
//        ActivityCollector.finishAll();
//        System.exit(0);
//    }

    //安装apk
    public void installAPK() {
        File apkFile = new File(getContext().getExternalCacheDir(), "guanyunyunguan.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    getContext()
                    , "jsgyrcb.com.dataanalysis"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        getContext().startActivity(intent);
    }

}
