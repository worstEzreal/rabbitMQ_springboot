package com.worstEzreal.rabbitProducer.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class LocalHttpClient {

    private final static Logger log = LoggerFactory.getLogger(LocalHttpClient.class);

    private final static String POST = HttpPost.METHOD_NAME;
    private final static String GET = HttpGet.METHOD_NAME;

    //Increase max total connection
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 200;

    //Increase default max connection per route
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 20;

    protected static CloseableHttpClient httpClient;
    protected static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    protected static Header textHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_PLAIN.toString());

    private static Map<String, CloseableHttpClient> httpClient_mchKeyStore = new HashMap<String, CloseableHttpClient>();

    private static String charsetEncoding = "utf-8";

    static {
        RequestConfig config = RequestConfig.custom().setSocketTimeout(20 * 1000)//超时 20秒
                .setConnectionRequestTimeout(20 * 1000)//
                .setConnectTimeout(20 * 1000)//超时 5秒
                .build();
        httpClient = HttpClientFactory.createHttpClient(DEFAULT_MAX_TOTAL_CONNECTIONS, DEFAULT_MAX_CONNECTIONS_PER_ROUTE, config);
    }

    public static <T> T execute(HttpUriRequest request, ResponseHandler<T> responseHandler) throws IOException {
        return httpClient.execute(request, responseHandler, HttpClientContext.create());
    }


    public static String post(String url, Object param) throws IOException {
        HttpUriRequest httpUriRequest = RequestBuilder.post().setHeader(jsonHeader).setUri(url).setEntity(
                new StringEntity(param == null ? "{}" : JSON.toJSONString(param), Charset.forName("utf-8"))).build();
        return httpClient.execute(httpUriRequest, new BasicResponseHandler(), HttpClientContext.create());
    }

    public static <T> T post(String url, Object param, Class<T> clazz) throws IOException {
        HttpUriRequest httpUriRequest = RequestBuilder.post().setHeader(jsonHeader).setUri(url).setEntity(
                new StringEntity(param == null ? "{}" : JSON.toJSONString(param), Charset.forName("utf-8"))).build();
        return httpClient.execute(httpUriRequest, createResponseHandler(clazz), HttpClientContext.create());
    }

    public static String post(String url, Map<String, String> param) throws IOException {
        List<NameValuePair> list = null;
        if (!param.isEmpty()) {
            list = map2Pair(param);
        }
        HttpUriRequest httpUriRequest = RequestBuilder.post().setUri(url).setEntity(new UrlEncodedFormEntity(list, charsetEncoding)).build();
        return httpClient.execute(httpUriRequest, new BasicResponseHandler(), HttpClientContext.create());
    }

    public static String get(String url, Map<String, String> map, Header header) throws IOException {
        HttpUriRequest httpUriRequest = RequestBuilder.get().setHeader(header).setUri(url + "?" + map2Url(map)).build();
        return httpClient.execute(httpUriRequest, new BasicResponseHandler(), HttpClientContext.create());
    }

    public static byte[] get(String url, Map<String,String> headers) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(),entry.getValue());
        }
        HttpUriRequest httpUriRequest = requestBuilder.setUri(url).build();
        return httpClient.execute(httpUriRequest, new AbstractResponseHandler<byte[]>() {
            @Override
            public byte[] handleEntity(HttpEntity entity) throws IOException {
                return EntityUtils.toByteArray(entity);
            }
        }, HttpClientContext.create());
    }

    public static <T> T get(String url, Map<String, String> map, Class<T> clazz) throws IOException {
        HttpUriRequest httpUriRequest = RequestBuilder.get().setHeader(jsonHeader).setUri(url + "?" + map2Url(map)).build();
        return httpClient.execute(httpUriRequest, createResponseHandler(clazz), HttpClientContext.create());
    }


    private static String map2Url(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    /**
     * post请求文件
     *
     * @param url            url路径
     * @param data           参数数据
     * @param fileParamName  文件名称
     * @param httpFileBodies 文件
     * @return 返回数据
     */
    public static JSONObject postFile(String url, Map<String, String> data, String fileParamName, HttpFileBody... httpFileBodies) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.post().setUri(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(Charset.forName(charsetEncoding));
        for (HttpFileBody httpFileBody : httpFileBodies) {
            multipartEntityBuilder.addPart(fileParamName, httpFileBody);
        }
        for (Map.Entry<String, String> entry : data.entrySet()) {
            multipartEntityBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
        }
        HttpUriRequest httpUriRequest = requestBuilder.setEntity(multipartEntityBuilder.build()).build();
        return execute(httpUriRequest, createResponseHandler(JSONObject.class));
    }

    public static JSONObject postFile(String url, Map<String, String> data, String fileParamName, byte[] binaryData, String fileName) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.post().setUri(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(Charset.forName(charsetEncoding));
        multipartEntityBuilder.addBinaryBody(fileParamName, binaryData, ContentType.APPLICATION_OCTET_STREAM, fileName);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            multipartEntityBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
        }
        HttpUriRequest httpUriRequest = requestBuilder.setEntity(multipartEntityBuilder.build()).build();
        return execute(httpUriRequest, createResponseHandler(JSONObject.class));
    }

    /**
     * @param map
     * @return
     */
    private static List<NameValuePair> map2Pair(Map<String, String> map) {
        Set<String> strings = map.keySet();
        Iterator<String> iterator = strings.iterator();
        List<NameValuePair> valuePairs = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            valuePairs.add(new BasicNameValuePair(key, value));
        }
        return valuePairs;
    }

    /**
     * HTTP文件流封装类.
     */
    public static class HttpFileBody extends AbstractContentBody {
        private String fileName;
        private long length;
        private InputStream fileInputStream;

        public HttpFileBody(String fileName, long length, InputStream fileInputStream) {
            super(ContentType.DEFAULT_BINARY);
            this.fileName = fileName;
            this.length = length;
            this.fileInputStream = fileInputStream;
        }

        @Override
        public String getFilename() {
            return this.fileName;
        }

        @Override
        public void writeTo(final OutputStream out) throws IOException {
            Args.notNull(out, "Output stream");
            try {
                final byte[] tmp = new byte[4096];
                int l;
                while ((l = this.fileInputStream.read(tmp)) != -1) {
                    out.write(tmp, 0, l);
                }
                out.flush();
            } finally {
                this.fileInputStream.close();
            }
        }

        @Override
        public String getTransferEncoding() {
            return MIME.ENC_BINARY;
        }

        @Override
        public long getContentLength() {
            return this.length;
        }
    }


    private static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz) {
        return new AbstractResponseHandler<T>() {
            @Override
            public T handleEntity(HttpEntity entity) throws IOException {
                T result = null;
                String rs = EntityUtils.toString(entity);
                log.info("【请求返回】result={}", rs);
                if (StringUtils.isNotBlank(rs)) {
                    result = JSON.parseObject(rs, clazz);
                }
                return result;
            }
        };
    }


}