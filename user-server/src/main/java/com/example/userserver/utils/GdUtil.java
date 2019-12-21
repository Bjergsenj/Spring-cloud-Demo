package com.example.userserver.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class GdUtil {


    private static final String KEY = "302139c42cb3c69fd10f6ade6706fbde";       //web服务的key

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return JSONObject.parseObject(jsonText);
        }
    }


    public static JSONObject coordinateConvert(String rectangle) {
        JSONObject json = null;
        try {
            String replace = rectangle.replace(";", "|");
            json = readJsonFromUrl("https://restapi.amap.com/v3/geocode/regeo?location=" + replace + "&key=" + KEY);
            if ("0".equals(json.getString("status"))) {
                log.error("调用高德返回异常: " + json.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("高德获取城市编码：", e);
        }
        return json;
    }

    public static JSONObject getAll(String ipAddress) {
        JSONObject json = null;
        try {
            json = readJsonFromUrl("http://restapi.amap.com/v3/ip?ip=" + ipAddress + "&key=" + KEY + "");
            if ("0".equals(json.getString("status"))) {
                log.error("调用高德返回异常: " + json.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("高德获取城市编码：", e);
        }
        return json;
    }
}
