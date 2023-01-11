package com.yudian.common.utils;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class HttpConfig {

    private static final Logger logger = LoggerFactory.getLogger(HttpConfig.class);
    
    private static final int CONNECT_TIMEOUT = 5000;
    
    private static final int REQUEST_TIMEOUT = 5000;
    
    private static final int SOCKET_TIMEOUT = 5000;
    
    private static final int MAX_TOTAL_CONNECTIONS = 8000;
    
    private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000;
    
    private static final int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 30;



    @Resource
    CloseableHttpClient httpClient;

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            logger.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
        }
        SSLConnectionSocketFactory sslsf = null;
        try {

            sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            logger.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
        }
        assert sslsf != null : "SSLConnectionSocketFactory must not = null!";
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create().register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        return poolingConnectionManager;
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator
                    (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
        };
    }




    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .build();
    }


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        logger.info("HttpClientConfig|restTemplate|restTemplate = " + restTemplate);
        return restTemplate;
    }








    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }

}

