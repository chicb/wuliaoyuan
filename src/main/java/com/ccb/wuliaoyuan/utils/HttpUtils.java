package com.ccb.wuliaoyuan.utils;

import com.squareup.okhttp.*;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/17.
 */
public class HttpUtils {
    private OkHttpClient client;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static HttpUtils instance;

    private HttpUtils() {
        client = new OkHttpClient();
    }

    /**
     * singleTon 考虑线程安全
     *
     * @return
     */
    public static HttpUtils instance() {
        if (null == instance) {
            synchronized (HttpUtils.class) {
                if (null == instance) instance = new HttpUtils();
            }
        }
        return instance;
    }


    public String get(String url, Map<String, String> params) throws IOException {
        url = URLUtils.makeURL(url, params);
        final Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute().body().string();
    }

    public String post(String url, Map<String, String> params, String body) throws IOException {
        url = URLUtils.makeURL(url, params);
        RequestBody requestBody = RequestBody.create(JSON, StringUtils.isEmpty(body) ? "{}" : body);
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        return client.newCall(request).execute().body().string();
    }

    /**
     * 微信接口的post方法-适用于临时素材的上传
     *
     * @param url
     * @param params
     * @param filePath
     * @return
     * @throws Exception
     */
    public String upload(String url, Map<String, String> params, String filePath) throws Exception {
        String[] parts = filePath.split("/");
        String fileName = parts[parts.length - 1];
        byte[] bytes = read(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), bytes);
        Map<String, String> map = new HashMap<String, String>() {{
            put("filename", fileName);
            put("filelength", bytes.length + "");
        }};
        map.putAll(params);
        String expandParams = String.join("; ", map.entrySet().stream().map(entry -> entry.getKey() + "=\"" + entry.getValue() + "\"").collect(Collectors.toList()));

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; " + expandParams
                ), fileBody)
                .build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    /**
     * 上传电子坐席的图片，不确定其它情况是否适用
     *
     * @param url
     * @param params
     * @param filePath
     * @param authorization
     * @return
     * @throws Exception
     */
    public String upload(String url, Map<String, String> params, String filePath, String authorization) throws Exception {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), read(filePath));
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        params.entrySet().forEach((entry -> multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue())));
        RequestBody requestBody = multipartBuilder.addFormDataPart("file", Math.random() + ".jpg", fileBody).build();
        Request request = new Request.Builder().header("Authorization", authorization).url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    private byte[] read(String filePath) throws Exception {
        InputStream ins = new URL(filePath).openStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = ins.read(buffer)) != -1)
            outStream.write(buffer, 0, len);
        ins.close();
        return outStream.toByteArray();
    }
}
