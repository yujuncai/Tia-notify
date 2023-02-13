package org.kikyou.tia.netty.chatroom.service;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSSClient;
import lombok.AllArgsConstructor;
import org.kikyou.tia.netty.chatroom.config.AliOssProperties;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author yujuncai
 */
@Service
@AllArgsConstructor
public class FileUploadService {

    private final AliOssProperties aliOssProperties;

    private final OSSClient ossClient;

    public String upload(File file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = aliOssProperties.getEndpoint();
        String bucketName = aliOssProperties.getBucket();
        // 上传文件流。
        String fileName = file.getName();
        //按照当前日期，创建文件夹，上传到创建文件夹里面
        //  2021/02/02/01.jpg
        String timeUrl = new DateTime().toString("yyyy/MM/dd");
        fileName = timeUrl + "/" + fileName;
        //调用方法实现上传
        ossClient.putObject(bucketName, fileName, file);
        //上传之后文件路径
        // https://xxxxxx.oss-cn-beijing.aliyuncs.com/01.jpg
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }
}
