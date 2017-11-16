package com.worstEzreal.rabbitProducer.common.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.*;

/**
 * httpclient 4.3.x
 */
public class HttpClientFactory {

    private final static Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

    private static final String[] supportedProtocols = new String[]{"TLSv1"};

    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, RequestConfig config) {
        try {
            SSLContext sslContext = SSLContexts.custom().build();
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if (null != config) {
                httpClientBuilder.setDefaultRequestConfig(config);
            }
            return httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager).setSSLSocketFactory(sf).build();
        } catch (KeyManagementException e) {
            logger.error("", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * Key store 类型HttpClient
     *
     * @param keystore
     * @param keyPassword
     * @return
     */
    public static CloseableHttpClient createKeyMaterialHttpClient(KeyStore keystore, String keyPassword) {
        return createKeyMaterialHttpClient(keystore, keyPassword, supportedProtocols);
    }

    /**
     * Key store 类型HttpClient
     *
     * @param keystore
     * @param keyPassword
     * @param supportedProtocols
     * @return
     */
    public static CloseableHttpClient createKeyMaterialHttpClient(KeyStore keystore, String keyPassword, String[] supportedProtocols) {
        try {
            SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword.toCharArray()).build();
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            return HttpClientBuilder.create().setSSLSocketFactory(sf).build();
        } catch (KeyManagementException e) {
            logger.error("", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (UnrecoverableKeyException e) {
            logger.error("", e);
        } catch (KeyStoreException e) {
            logger.error("", e);
        }
        return null;
    }

}
