package com.sxb.kiven.mytest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by kiven on 16/3/31.
 */
public class JsonUtil {

    private static Gson gson;

    public static void init(){
        if(gson == null){
//			gson = new Gson();
            GsonBuilder builder = new GsonBuilder();

            JsonDeserializer<Boolean> deserializer = new JsonDeserializer<Boolean>() {
                @Override
                public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {

                    if (json.isJsonPrimitive()) {
                        JsonPrimitive primitive = (JsonPrimitive) json;
                        if (primitive.isNumber()) {
                            return primitive.getAsInt() != 0;
                        }
                    }
                    return json.getAsBoolean();
                }
            };

            builder.registerTypeAdapter(Boolean.class, deserializer);
            builder.registerTypeAdapter(boolean.class, deserializer);
            gson = builder.create();
        }
    }

    private String jsonStr = null;
    private boolean isSuccess = false;
    private String errorMsg = null;
    private int code;
    private JSONObject dataObj = null;
    private String requestTime = "";//下拉刷新时，返回一个空字符串；上拉查看历史时，返回下一页记录中最大的时间值

    public JsonUtil(String jsonStr) {
        init();

        this.jsonStr = jsonStr;

        try {
            JSONObject jo = new JSONObject(jsonStr);

            if((isSuccess =jo.optBoolean("success", false))){
                dataObj = jo.optJSONObject("datas");
                if(dataObj != null){
                    requestTime = dataObj.optString("requestTime", "");
                }
            }
            errorMsg = jo.optString("errMsg", "");
            code = jo.optInt("code");

        } catch (JSONException e) {
            errorMsg = "not network";
            ULog.e(e);
        }
    }

    public <T> T toObject(Class<T> aClass){
        if(dataObj == null)
            return null;
        try {
            return gson.fromJson(dataObj.toString(), aClass);
        } catch (Exception e) {
            ULog.e(e);
            return null;
        }
    }

    public <T> ArrayList<T> toObjects(Class<T> aClass){
        ArrayList<T> datas = new ArrayList<T>();
        if(dataObj == null)
            return datas;
        try {

            JSONArray jsonArray = dataObj.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsObj = jsonArray.getJSONObject(i);
                datas.add(gson.fromJson(jsObj.toString(), aClass));
            }
            //			return gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<T>>() {}.getType());
        } catch (Exception e) {
            ULog.e(e);
        }

        return datas;
    }

    public <T> ArrayList<T> toObjects2(Class<T> aClass){
        ArrayList<T> datas = new ArrayList<T>();

        try {
            JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("datas");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsObj = jsonArray.getJSONObject(i);
                    datas.add(gson.fromJson(jsObj.toString(), aClass));
                }
            }

        } catch (JSONException e) {
            ULog.e(e);
        }

        return datas;
    }

    //-------------------------getter----------------------------
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public JSONObject getData(){
        return dataObj;
    }

    public String getStr(){
        return jsonStr;
    }

    public Gson getGson() {
        init();
        return gson;
    }

    public int getCode() {
        return code;
    }

    //------------------------静态解析---------------------

    public static boolean isSuccess(String jsonStr){
        try {
            JSONObject jo = new JSONObject(jsonStr);

            return jo.optBoolean("success", false);
        } catch (JSONException e) {
            //			e.printStackTrace();
            ULog.e(e);
        }

        return false;
    }

    public static String errorMsg(String jsonStr){

        try {
            JSONObject jo = new JSONObject(jsonStr);

            return jo.optString("errMsg", "");
        } catch (JSONException e) {
            //			e.printStackTrace();
            ULog.e(e);
        }

        return "network error";
    }

    public static <T> ArrayList<T> toObjects(String jsonStr, Class<T> aClass){
        init();
        try {
            JSONObject jo = new JSONObject(jsonStr);
            JSONArray jo2 = jo.optJSONArray("data");
            //			String jsonObjStr = jo2.toString();

            ArrayList<T> datas = new ArrayList<T>();

            for (int i = 0; i < jo2.length(); i++) {
                JSONObject jsObj = jo2.getJSONObject(i);
                datas.add(gson.fromJson(jsObj.toString(), aClass));
            }


            //			return gson.fromJson(jsonObjStr, new TypeToken<ArrayList<T>>() {}.getType());

            return datas;
        } catch (Exception e) {
            //			e.printStackTrace();
            ULog.e(e);
        }

        return null;
    }

    public static <T> T getObject(String jsonStr,String key, Class<T> aClass){
        init();
        try {
            JSONObject jo = new JSONObject(jsonStr);

            Object value = jo.opt(key);

            if(value != null)
                return gson.fromJson(value.toString(), aClass);
        } catch (Exception e) {
            ULog.e(e);
        }
        return null;
    }

    public static <T> T toObject(Class<T> aClass, String jsonStr){
        init();

        try {
            JSONObject jo = new JSONObject(jsonStr);
            JSONObject jo2 = jo.optJSONObject("data");
            String jsonObjStr = jo2.toString();

            //			Object jo2 = jo.opt("datas");
            //			String jsonObjStr = jo2.toString();

            T obj = gson.fromJson(jsonObjStr, aClass);

            return obj;
        } catch (Exception e) {
            //			e.printStackTrace();
            ULog.e(e);
        }

        return null;
    }

    //	public static <T> T toObject(Class<T> aClass, JSONObject jsonObj){
    //
    //	}
}
