package com.yudian.www.controller.upload;

import com.yudian.common.entity.R;
import com.yudian.common.utils.RestTemplateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Api(tags = "minio文件上传")
@RestController
@RequestMapping("/upload/minio")
public class MinioUploadController {

    @Resource
    private RestTemplateUtils restTemplateUtils;

    @ApiOperation(value = "文件上传", notes = "文件上传（photoType=[test]）")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "photoType", value = "图片类型（项目）", dataType = "String", required = true)
    )
    @PostMapping("/file")
    public R uploadFile(@RequestParam("file") MultipartFile file,
                        @RequestParam String photoType) {
        if (file == null || file.isEmpty()) {
            return R.error(null, "上传图片失败");
        }
        if (StringUtils.isBlank(photoType)) {
            return R.error(null, "图片类型错误");
        }
        //获取文件全名
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            return R.error(null, "上传附件失败");
        }
        if (originalFilename.lastIndexOf(".") == -1) {
            return R.error(null, "上传附件失败");
        }
        //获得文件后缀名
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileUrl;
        String fileName = UUID.randomUUID().toString().replace("-", "") + fileSuffix;
        try {
            if ("test".equals(photoType)) {
                R testR = restTemplateUtils.minioUpload("http://127.0.0.1:9002/minio-File-Service/minio/putObject", "test", fileName, file, R.class);
                if (testR.getStatusCode() != 20000) {
                    return R.error(null, "上传失败");
                }
                Map<String, Object> uriVariables = new HashMap<>();
                uriVariables.put("bucketName", "test");
                uriVariables.put("objectName", fileName);
                R<String> exchange = restTemplateUtils.exchange("http://127.0.0.1:9002/minio-File-Service/minio/getObjectOptionSecond", HttpMethod.POST, uriVariables, null, null, R.class);
                if (exchange.getStatusCode() != 20000) {
                    return R.error(null, "上传失败");
                }
                fileUrl = exchange.getData();
            } else {
                return R.error(null, "图片类型错误");
            }
            if (null == fileUrl) {
                return R.error(null, "上传文件失败");
            }
            return R.ok(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return R.error(null, "上传文件失败");
    }
}
