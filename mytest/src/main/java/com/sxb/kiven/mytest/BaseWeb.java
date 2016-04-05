package com.sxb.kiven.mytest;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BaseWeb {
    /*public static String API_IP = "test.cqsxbkj.cn:7088";//正式远程
    public static String API_ROOT_HOST = "http://" + API_IP;
    public static String API_HOST = API_ROOT_HOST + "/app/v2/";*/

    protected static void post(String methodName, TreeMap<String, Object> params, Callback.CommonCallback callback) {
        RequestParams requestParams = new RequestParams("http://test.cqsxbkj.cn:7088/app/v2/" + methodName);
        if (params.size() > 0) {
            for (String key : params.keySet()) {
                requestParams.addBodyParameter(key, params.get(key), null);
            }
        }

        Callback.Cancelable cancelable = x.http().post(requestParams, callback);
    }

    //----------------------加密---------------------------

    /**
     * 重组参数，添加指定参数
     * @param params
     * @param isPrivate
     * @return
     */
    protected static TreeMap<String, Object> jiaMi(TreeMap<String, Object> params, boolean isPrivate){
        TreeMap<String, Object> mParams = new TreeMap<String, Object>();
        if(params != null){
            Gson gson = new Gson();
            mParams.put("param", gson.toJson(params));
        }

        if(MyApplication.getInstance().isLogin()){
            mParams.put("userID", MyApplication.getInstance().getUser().getUserID() + "");
            mParams.put("token", MyApplication.getInstance().getUser().getToken());
        }
        else{
            mParams.put("userID", "0");
            mParams.put("token", "");
        }
        mParams.put("deviceType", "2");
        mParams.put("deviceOSVersion", "android " + android.os.Build.VERSION.RELEASE);
        mParams.put("appVersion", /*Util.getVersionCode()*/320 + "");

        try {
            String sha1 = sha1(params, isPrivate);
            if (sha1 == null) {
                return null;
            }
            mParams.put("key", sha1);
        } catch (Exception e) {
            e.printStackTrace();
            mParams.put("key", "");
        }

        return mParams;
    }
    protected static TreeMap<String, Object> jiaMi(TreeMap<String, Object> params){
        return jiaMi(params, false);
    }
    /**
     * 重组参数，添加指定参数.
     * @param params
     * @param filePaths 上传的文件数组
     * @return
     * @throws FileNotFoundException
     */
    protected static TreeMap<String, Object> jiaMi(TreeMap<String, Object> params,String key, List<String> filePaths) throws FileNotFoundException{
        if(filePaths == null || filePaths.size() < 1)
            return jiaMi(params, true);

        TreeMap<String, Object> mParams = jiaMi(params, true);

        List<File> files = new ArrayList<File>();
        for (String string : filePaths) {
            files.add(new File(string));
        }
        mParams.put(key, files);

        return mParams;
    }
    /**
     * 加密参数字符串
     * @param params
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1(TreeMap<String, Object> params, boolean isPrivate) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if (params == null) {
            params = new TreeMap<String, Object>();
        }

        if (isPrivate) {
            if (MyApplication.getInstance().isLogin()) {
                params.put("publicKey", MyApplication.getInstance().getUser().getPrivateKey());
            }else {
                return null;
            }
        } else {
            params.put("publicKey", "4cbce54e0b03d2017c70193");
        }

        if (params != null && params.size() > 0) {
            String conStr = "";
            int i = 0;

            for (String key : params.keySet()) {

                Object value = params.get(key);
                if (value != null) {
                    if (i > 0) {
                        conStr += "^_~";
                    }else {
                        i ++;
                    }
                    if (value instanceof Boolean) {
                        boolean b = (Boolean) value;
                        conStr += (key + "=" + (b ? 1: 0));
                    } else if (value instanceof String) {
                        conStr += (key + "=" + value);
                    } else if (value instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Object> list = (List<Object>) value;
                        String valueStr = "[";
                        if (list.size() > 0) {
                            valueStr += list.get(0);
                            if (list.size() > 1) {
                                for (int j = 1; j < list.size(); j++) {
                                    valueStr += ("," + list.get(j));
                                }
                            }
                        }
                        valueStr += "]";
                        conStr += (key + "=" + valueStr);
                    } else {
                        conStr += (key + "=" + formaterFloat(Double.parseDouble(value.toString())));
                    }
                }
            }
            return sha1(conStr);
        }
        return "";
    }
    public static String formaterFloat(double value){
        return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
    }

    /**
     * 加密哈希
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes("UTF-8"));
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    /**
     * 登录
     *
     * @param userAccount
     * @param userPassword
     * @param callBack
     */
    public static void login(String userAccount, String userPassword, WebCallBack callBack) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("userAccount", userAccount);
        map.put("userPassword", userPassword);

        post("wh/user/login.json", jiaMi(map), callBack);
    }

    /**
     * 上传文件
     * @param userAccount
     * @param paths
     * @param callBack
     */
    public static void uploadImage(String userAccount, List<String> paths, WebCallBack callBack) {
        try {
            RequestParams requestParams = new RequestParams("http://192.168.0.130:8086/app/wh/my/test3.json");
            requestParams.addQueryStringParameter("userAccount", userAccount);
            requestParams.setConnectTimeout(1000 * 5);

            for (String string : paths) {
                requestParams.addParameter("images", new File(string));
            }

            Callback.Cancelable cancelable = x.http().post(requestParams, callBack);
        } catch (Exception e) {
            ULog.e(e);
        }
    }
}
