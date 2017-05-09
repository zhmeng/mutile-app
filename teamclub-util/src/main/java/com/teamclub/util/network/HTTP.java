package com.teamclub.util.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.teamclub.util.libs.Json;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhangmeng on 17-2-8.
 */
public class HTTP {

    private static final Logger logger = LoggerFactory.getLogger(HTTP.class);

    private static CloseableHttpClient httpClient = null;
    static {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(2 * 1000)
                .setConnectTimeout(2 * 1000)
                .setSocketTimeout(2 * 1000)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(100);
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(config)
                .build();
    }


    public static HttpRespEntity post(String url, Map<String, String> map ) {
        JsonNode jn = Json.toJson(map);
        return post(url, jn);
    }

    public static HttpRespEntity post(String url, JsonNode json) {
        return post(url, Json.stringify(json));
    }

    public static HttpRespEntity post(String url, String str) {
        HttpUriRequest req = null ;
        req = RequestBuilder.post(url).setHeader("Content-Type", "application/json;UTF-8").setEntity(new StringEntity(str, "UTF-8")).build();
        return unify(req);
    }
    public static HttpRespEntity get(String url) {
        HttpUriRequest req = RequestBuilder.get(url).build();
        return unify(req);
    }

    public static HttpRespEntity unify(HttpUriRequest req) {
        try (CloseableHttpResponse response = httpClient.execute(req)) {
            byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
            HttpRespEntity respEntity = new HttpRespEntity(bytes);
            return respEntity;
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }
}
