package com.yk.androidassistant.Util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件处理工具类
 * Created by yk on 2020/4/29 0029.
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    //根目录路径
    public static final String rootDirectory = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    /**
     * 创建文件
     *
     * @param path 文件路径
     */
    public static boolean createPath(String path) {
        File file = new File(path);
        if (!file.exists()) { //文件夹不存在
            try {
                // 获取父文件
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();  //创建所有父文件夹
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

//    /**
//     * 获取文件大小
//     *
//     * @param path
//     * @return
//     * @throws Exception
//     */
//    public static long getFileSize(String path) throws Exception {
//        File file = new File(path);
//        return getFileSize(file);
//    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static long getAutoFileOrFilesSize(String filePath) throws Exception {
        File file = new File(filePath);
        long blockSize = 0;
        if (file.isDirectory()) {
            blockSize = getFileSizes(file);
        } else {
            blockSize = getFileSize(file);
        }
        return blockSize / 1024;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
//            file.createNewFile();
//            LogUtil.e(TAG, "文件不存在!");
            throw new Exception("文件不存在！");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }
}
