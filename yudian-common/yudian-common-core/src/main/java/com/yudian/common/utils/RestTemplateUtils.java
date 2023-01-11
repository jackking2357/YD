package com.yudian.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Component
public class RestTemplateUtils {

    private final static Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

    @Resource
    private RestTemplate restTemplate;

    public <T> T exchange(String url, HttpMethod method, Map<String, Object> uriVariables, Object jsonObject, HttpHeaders requestHeader, Class<T> responseType) {
        String uriVariablesParam = "";
        if (null != uriVariables && 0 != uriVariables.size()) {
            StringBuffer uriSb = new StringBuffer("?");
            uriVariables.forEach((k, v) -> {
                uriSb.append(k).append("=").append("{").append(k).append("}").append("&");
            });
            uriVariablesParam = uriSb.substring(0, uriSb.length() - 1);
        }
        HttpHeaders requestHeaders = requestHeader;
        if (null == requestHeaders) {
            requestHeaders = new HttpHeaders();
        }

        HttpEntity<Object> requestEntity = new HttpEntity<>(null, requestHeaders);
        if (null != jsonObject) {
            requestEntity = new HttpEntity<>(jsonObject, requestHeaders);
        }
        url += uriVariablesParam;
        long startTime = System.currentTimeMillis();
        if (null == uriVariables || 0 == uriVariables.size()) {
            T body = restTemplate.exchange(url, method, requestEntity, responseType).getBody();
            logger.info("【接口请求】【{}】【处理时间：{}】【普通参数：{}】【JSON参数：{}】【返回结果：{}】", url, System.currentTimeMillis() - startTime, uriVariables, jsonObject, body);
            return body;
        }
        T body = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
        logger.info("【接口请求】【{}】【处理时间：{}】【普通参数：{}】【JSON参数：{}】【返回结果：{}】", url, System.currentTimeMillis() - startTime, uriVariables, jsonObject, body);
        return body;
    }

    public <T> T minioUpload(String url, String bucketName, String objectName, MultipartFile file, Class<T> responseType) {
        File file1 = null;
        try {
            //转换为file
            file1 = multipartFileToFile(file);

            FileSystemResource resource = new FileSystemResource(file1);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            //参数
            param.add("file", resource);
            param.add("bucketName", bucketName);
            param.add("objectName", objectName);
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param);

            long startTime = System.currentTimeMillis();
            T body = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType).getBody();
            logger.info("【接口请求】【{}】【处理时间：{}】【普通参数：{}】【JSON参数：{}】【返回结果：{}】", url, System.currentTimeMillis() - startTime, null, null, body);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != file) {
                //最后要删除
                if (file1 != null) {
                    file1.delete();
                }
            }
        }
        return null;
    }

    private static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
