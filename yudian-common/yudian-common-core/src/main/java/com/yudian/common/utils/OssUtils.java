//package com.yudian.common.utils;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.ObjectMetadata;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.net.ssl.HttpsURLConnection;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Component
//public class OssUtils {
//
//    public static final Logger logger = LoggerFactory.getLogger(OssUtils.class);
//    //
//    private final static String accessKeyId = "LTAI5t9MifTM78EhG8gasrXF";
//    private final static String accessKeySecret = "YhCusQjT0CcAYYxjccPKraHtSNHLzT";
//    private final static String ordinaryBucketName = "qingbaowang";
//    //        private final static String endpoint = "oss-cn-shenzhen.aliyuncs.com";= "oss-cn-shenzhen-internal.aliyuncs.com"
//    @Value("${oss.endpoint}")
//    private String endpoint;
//
//    public static String getContentType(String FilenameExtension) {
//        if (".bmp".equalsIgnoreCase(FilenameExtension)) {
//            return "image/bmp";
//        }
//        if (".gif".equalsIgnoreCase(FilenameExtension)) {
//            return "image/gif";
//        }
//        if (".jpeg".equalsIgnoreCase(FilenameExtension) || ".jpg".equalsIgnoreCase(FilenameExtension) || ".png".equalsIgnoreCase(FilenameExtension)) {
//            return "image/jpg";
//        }
//        if (".html".equalsIgnoreCase(FilenameExtension)) {
//            return "text/html";
//        }
//        if (".txt".equalsIgnoreCase(FilenameExtension)) {
//            return "text/plain";
//        }
//        if (".vsd".equalsIgnoreCase(FilenameExtension)) {
//            return "application/vnd.visio";
//        }
//        if (".pptx".equalsIgnoreCase(FilenameExtension) || ".ppt".equalsIgnoreCase(FilenameExtension)) {
//            return "application/vnd.ms-powerpoint";
//        }
//        if (".docx".equalsIgnoreCase(FilenameExtension) || ".doc".equalsIgnoreCase(FilenameExtension) || ".rtf".equalsIgnoreCase(FilenameExtension)) {
//            return "application/msword";
//        }
//        if (".xml".equalsIgnoreCase(FilenameExtension)) {
//            return "text/xml";
//        }
//        if (".pdf".equalsIgnoreCase(FilenameExtension)) {
//            return "application/pdf";
//        }
//        if (".pic".equalsIgnoreCase(FilenameExtension)) {
//            return "application/x-pic";
//        }
//        if (".xls".equalsIgnoreCase(FilenameExtension) || ".xlsx".equalsIgnoreCase(FilenameExtension)) {
//            return "application/vnd.ms-excel";
//        }
//        return null;
//    }
//
//    /**
//     * 上传图片
//     */
//    public String upload(InputStream input, String fileName, DirPathEnum dirPathEnum) {
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        try {
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setHeader("Access-Control-Allow-Origin", "*");
//            objectMetadata.setHeader("Access-Control-Allow-Credentials", "true");
//            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
//            ossClient.putObject(ordinaryBucketName, dirPathEnum.dir + fileName, input, objectMetadata);
//            StringBuffer sb = new StringBuffer("https://");
//            sb.append(ordinaryBucketName).append(".").append(endpoint.replaceAll("-internal", "")).append("/").append(dirPathEnum.dir).append(fileName);
//            return sb.toString();
//        } catch (OSSException e) {
//            logger.error(e.getMessage());
//        } catch (ClientException e) {
//            logger.error(e.getMessage());
//        } finally {
//            ossClient.shutdown();
//        }
//        return null;
//    }
//
//    /**
//     * @return void    返回类型
//     * @Title: deleteFile 需要带上目录
//     * @Description: 删除文件
//     */
//    public void deleteFile(DirPathEnum dirPathEnum, String fileName) {
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        ossClient.deleteObject(ordinaryBucketName, dirPathEnum.dir + fileName);
//        ossClient.shutdown();
//    }
//
//    public void compared(List<String> oldPhotos, List<String> newPhotos) {
//        // 新的过滤旧的，得到要删除的照片
//        List<String> remove = oldPhotos.stream().filter(old -> !newPhotos.contains(old))
//                .collect(Collectors.toList());
//
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        for (String photos : remove) {
//            // 先替换掉域名
//            String replace = photos.replaceAll("https://" + ordinaryBucketName + "." + endpoint + "/", "");
////            String dir = replace.substring(0, replace.indexOf("/"));
////            String fileName = replace.substring(replace.indexOf("/"));
//            ossClient.deleteObject(ordinaryBucketName, replace);
//            System.out.println("删除文件：" + replace);
//        }
//        ossClient.shutdown();
//    }
//
//    public String avatarUrl(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//            // 得到URL的输入流
//            InputStream input = con.getInputStream();
//            String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
//            return upload(input, fileName, DirPathEnum.TEST);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageUrl;
//    }
//
//    public enum DirPathEnum {
//        /**
//         * 测试
//         */
//        TEST("test/"),
//        THREEJS("threejs/"),
//        ;
//        private final String dir;
//
//        DirPathEnum(String dir) {
//            this.dir = dir;
//        }
//    }
//}
