package com.yk.androidassistant.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by Administrator on 2020/6/30 0030.
 */

public class DeEnCode {
    private static final String TAG = "DeEnCode";

    /**
     * @param enc
     * @return 加密
     */
    public static String encrypt(String enc) throws UnsupportedEncodingException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String date = base64Encoder.encode(enc.getBytes("UTF-8"));
        LogUtil.d(TAG, "encrypt-->" + date);
        String startstr = date.substring(3, date.length()) + date.substring(0, 3);
        return startstr;
//        return enc; //未加密
    }

    /**
     * @param dec
     * @return 解密
     * @throws IOException
     */
    public static String decrypt(String dec) throws Exception {
        String startstr = dec.substring(dec.length() - 3, dec.length()) + dec.substring(0, dec.length() - 3);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(startstr);
        return new String(bytes, "UTF-8");
//        return dec; //未加密
    }
}
