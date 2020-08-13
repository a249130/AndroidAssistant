package com.yk.androidassistant.OkHttp;

import com.yk.androidassistant.OkHttp.model.Agreement;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jsgyrcb-yk on 2019/12/9 0009.
 */

public class httpUtils {
    private static final String TAG = "httpUtils";

    private static final String URL_HEADER = "http://192.168.3.181:8080/rhnhcjWeb/";
//    private static final String URL_HEADER = "http://192.168.43.101:8080/rhnhcjWeb/";

    public static String COOKIE = ""; //登录成功后的cookie

    static OkHttpClient client;

    static OkHttpClient getClient() {
        if (client == null)
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS) //设置连接超时时间
                    .readTimeout(20, TimeUnit.SECONDS) //设置读取数据超时时间
//                    .retryOnConnectionFailure(true) //当连接失败，尝试重连   暂时不需要
//                    .addNetworkInterceptor(new TestInterceptor()) //添加拦截器
                    .build();
        return client;
    }

    /**
     * http POST请求
     *
     * @param url  端口后面的部分  前面不要加/
     * @param json 请求数据
     * @return 返回数据
     * @throws IOException
     */
    public static Response doPost(String url, String json) throws IOException {
        //加密
//        json = MyDes.encode(json);

        OkHttpClient client = getClient();
        RequestBody body = RequestBody.create(
                MediaType.parse("text/html; charset=utf-8"), json);
        Request request;
        String urls = URL_HEADER + url;
//        LogUtil.d(TAG, "COOKIE: " + COOKIE + "   urls-->" + urls);
        if (COOKIE.isEmpty())
            request = new Request.Builder()
                    .url(urls)
                    .post(body)
//                    .addHeader("Connection", "close")
                    .build();
        else
            request = new Request.Builder()
                    .url(urls)
                    .post(body)
                    .addHeader("cookie", COOKIE)
//                    .addHeader("Connection", "close")
                    .build();
        Call call = client.newCall(request);
        return call.execute();
    }

    /**
     * http post请求
     *
     * @param httpUrl url地址
     * @param param   参数
     * @return
     */
//    public static String post(String httpUrl, String param) {
//        HttpURLConnection connection = null;
//        InputStream in = null;
//        OutputStream os = null;
//        BufferedReader br = null;
//        String res = "";
//        try {
//            URL url = new URL(URL_HEADER + httpUrl);
//            // 通过远程url连接对象打开连接
//            connection = (HttpURLConnection) url.openConnection();
//            // 设置连接请求方式
//            connection.setRequestMethod("POST");
//            // 设置连接服务器超时时间
//            connection.setConnectTimeout(10 * 1000);
//            // 设置返回数据超时时间
//            connection.setReadTimeout(20 * 1000);
//            // 默认为false 当向远程服务器传送数据/写数据时，需要设置为true
//            connection.setDoOutput(true);
//            // 默认为truw 当向远程服务器读取数据时，设置为true，该设置可有可无
//            connection.setDoInput(true);
//            connection.setRequestProperty("Content-Type",
//                    "text/html; charset=utf-8");
//            connection.setRequestProperty("cookie", COOKIE);
//            connection.setRequestProperty("Connection", "close");
//            // 通过连接对象获取输入流
//            os = connection.getOutputStream();
//            // 通过输出流对象将参数写出/传输出去，它是通过字节流数组写出
//            os.write(param.getBytes());
//            // 获取返回码
//            int code = connection.getResponseCode();
//            if (code == 200) { // 返回正常
//                // 通过连接对象获取输入流
//                in = connection.getInputStream();
//                // 对输入流对象进行包装
//                br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//                // 存放数据
//                StringBuffer sb = new StringBuffer();
//                String s = null;
//                while ((s = br.readLine()) != null) {
//                    sb.append(s);
//                }
//                res = sb.toString();
//            }
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            // 关闭释放资源
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            // 断开与远程url的连接
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//
//        return res;
//    }

//    /**
//     * http POST请求
//     * 批量上传文件并携带参数
//     *
//     * @param url      端口后面的部分  前面不要加/
//     * @param jsonKey  请求数据参数
//     * @param json     请求数据 传null 只上传文件
//     * @param fileKey  文件参数名称
//     * @param fileList 文件路径列表（批量）
//     * @return
//     * @throws IOException
//     */
//    public static Response doPostFiles(String url, String jsonKey, String json, String fileKey, List<String> fileList) throws Exception {
//        //加密
////        json = MyDes.encode(json);
//
//        OkHttpClient client = getClient();
//
//        MultipartBody.Builder reqBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//        if (json != null) {
//            //添加数据
//            reqBuilder.addFormDataPart(jsonKey, json);
//        }
//
////        //压缩图片
////        List<String> pathList = new ArrayList<String>();
////        for (String path : list) {
////            String newPath = PictureUtil.compressSavePath(MyApplication.getContext()
////                    .getExternalCacheDir() + "/" +
////                    System.currentTimeMillis() + ".jpg", path);
////            pathList.add(newPath);
////        }
////        list.clear();
//
//
//        //循环添加文件
//        for (String filePath : fileList) {
//            //图片压缩
//            String newPath = BitmapUtil.compressImage(MyApplication.getContext()
//                    .getExternalCacheDir() + "/" +
//                    System.currentTimeMillis() + ".jpg", filePath);
//            File file = new File(newPath);
//            reqBuilder.addFormDataPart(fileKey, file.getName(),
//                    RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), //设置文件传输的编码与类型
//                            file));
////            reqBuilder.addPart(RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), //设置文件传输的编码与类型
////                    file));
//        }
//        RequestBody requestBody = reqBuilder.build();
//
//        Request request;
////        Log.d(TAG, "doPost: " + COOKIE);
//        if (COOKIE.isEmpty())
//            request = new Request.Builder()
//                    .url(URL_HEADER + url)
//                    .post(requestBody)
//                    .build();
//        else
//            request = new Request.Builder()
//                    .url(URL_HEADER + url)
//                    .post(requestBody)
//                    .addHeader("cookie", COOKIE)
//                    .build();
//        Call call = client.newCall(request);
//        return call.execute();
//    }

//    public static Response doPostF(List<String> pathList){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS) //设置连接超时时间
//                .readTimeout(20, TimeUnit.SECONDS) //设置读取数据超时时间
//                .build();
//        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//        //循环添加文件
//        for (int i = 0; i < pathList.size(); i++) {
//            File file = new File(pathList.get(i));
//            requestBodyBuilder.addFormDataPart("files", file.getName(),
//                    RequestBody.create(MediaType.parse("text/html; charset=utf-8"),
//                            new File(pathList.get(i))));
//        }
//
//        Request request;
////        Log.d(TAG, "doPost: " + COOKIE);
//        if (COOKIE.isEmpty())
//            request = new Request.Builder()
//                    .url(URL_HEADER + url)
//                    .post(body)
//                    .build();
//        else
//            request = new Request.Builder()
//                    .url(URL_HEADER + url)
//                    .post(body)
//                    .addHeader("cookie", COOKIE)
//                    .build();
//        Call call = client.newCall(request);
//        return call.execute();
//    }

    /**
     * http GET请求
     *
     * @param url 端口后面的部分  前面不要加/
     * @return 返回数据
     * @throws IOException
     */
    public static Response doGet(String url) throws IOException {
        OkHttpClient client = getClient();
        Request request;
        if (COOKIE.isEmpty())
            request = new Request.Builder()
                    .url(URL_HEADER + url)
                    .get()
                    .addHeader("Content-Type", "text/html; charset=utf-8")
                    .build();
        else
            request = new Request.Builder()
                    .url(URL_HEADER + url)
                    .get()
                    .addHeader("cookie", COOKIE)
                    .addHeader("Content-Type", "text/html; charset=utf-8")
                    .build();
        Call call = client.newCall(request);
        return call.execute();
    }

    /**
     * 保存文件
     *
     * @param url 文件url
     * @return
     * @throws IOException
     */
    public static Call save(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS) //设置连接超时时间
//                .readTimeout(20, TimeUnit.SECONDS) //设置读取数据超时时间
//                    .retryOnConnectionFailure(true) //当连接失败，尝试重连   暂时不需要
//                    .addNetworkInterceptor(new TestInterceptor()) //添加拦截器
                .build();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
//        Call call = client.newCall(request);
        return client.newCall(request);
    }

    /**
     * json转实体类
     *
     * @param json json数据
     * @return
     */
    public static Agreement jsonToagreement(String json) {
        Agreement agreement = new Agreement();
        try {
            JSONObject jsonObject = new JSONObject(json);
            agreement.setCode(jsonObject.optString("code", ""));
            String data = jsonObject.optString("data", "");
            //解密
//            data = MyDes.decode(data);
            agreement.setData(data);
            agreement.setFooter(jsonObject.optString("footer", ""));
            agreement.setMsg(jsonObject.optString("msg", ""));
            agreement.setPage(jsonObject.optInt("page", 0));
            agreement.setPageTotal(jsonObject.optInt("pageTotal", 0));
            String rows = jsonObject.optString("rows", "");
            //解密
//            rows = MyDes.decode(rows);
            agreement.setRows(rows);
            agreement.setSuccess(jsonObject.optBoolean("success", false));
            agreement.setTotal(jsonObject.optString("total", ""));
        } catch (Exception e) {
            e.printStackTrace();
            agreement.setMsg(json);
            agreement.setSuccess(false);
        }
        return agreement;
    }

    /**
     * 报错信息转字符串
     *
     * @param e
     * @return
     */
    public static String ExceptionToString(String e) {
        if (e.contains("SocketTimeout") || e.contains("SocketTimeoutException"))
            e = "请求超时！";
        else if (e.contains("ConnectException"))
            e = "接口请求失败，请检查网络！";
        return e;
    }

    /**
     * 将一些字符串置空
     *
     * @param strNull
     * @return
     */
    public static String nullHandler(String strNull) {
        String res;
        switch (strNull) {
            case "null":
            case "/":
            case "//":
                res = "";
                break;
            default:
                res = strNull;
                break;
        }
        return res;
    }

    /**
     * 将数字转换成字符串，如果是-1转为空
     *
     * @param num 数字
     * @return
     */
    public static String numToStr(int num) {
        String res;
        if (num == -1)
            res = "";
        else
            res = String.valueOf(num);
        return res;
    }

//    static class TestInterceptor implements Interceptor {
//        private static final String TAG = "TestInterceptor";
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            LogUtil.d(TAG, "拦截器开始");
//            // 获取请求对象
//            Request request = chain.request();
//
//            long t1 = System.nanoTime();
//            LogUtil.d(TAG, String.format("Sending request %s on %s%n%s",
//
//            // 发起HTTP请求，并获取响应对象
//            Response response = chain.proceed(request);
//
//            long t2 = System.nanoTime();
//            LogUtil.d(TAG, String.format("Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//
//            return response;
//        }
//    }
}