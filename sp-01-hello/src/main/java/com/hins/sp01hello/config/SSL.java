package com.hins.sp01hello.config;

/**
 * Created by chenqixuan on 19/5/29.
 * 跳过证书验证封装
 */

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class SSL extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            prepareHttpsConnection((HttpsURLConnection) connection);
        }

        super.prepareConnection(connection, httpMethod);
    }

    private void prepareHttpsConnection(HttpsURLConnection connection) {
        connection.setHostnameVerifier(new SkipHostnameVerifier());
        try {
            connection.setSSLSocketFactory(createSslSocketFactory());
        } catch (Exception ex) { // Ignore

        }
    }

    private SSLSocketFactory createSslSocketFactory() throws Exception {

        SSLContext context = SSLContext.getInstance("TLS");
//        // 报java.net.SocketException:Connection reset
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        // 报java.net.SocketException:Connection reset
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        // 使用TLSv1.2版本协议，避免nginx禁用TLSv1协议导致异常
//        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        context.init(null, new TrustManager[]{new SkipX509TrustManager()}, new SecureRandom());
        return context.getSocketFactory();
    }

    private class SkipHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    private static class SkipX509TrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

}

