package com.lyamada.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHelper {
    public static JSONObject getFirstResult(String httpResponse) {
        return new JSONObject(httpResponse).getJSONObject("data").getJSONArray("results").getJSONObject(0);
    }

    public static JSONArray getResultArray(String httpResponse) {
        return new JSONObject(httpResponse).getJSONObject("data").getJSONArray("results");
    }
}
