package com.java.activiti.util;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/12/22.
 */
public class JsonUtil {
    public static <T> T formatJSON(Object obj, Class<T> classOfT) {
        Gson gson = new Gson();
        String res = gson.toJson(obj);
        return gson.fromJson(res, classOfT);
    }
}
