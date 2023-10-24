package org.kikyou.tia.netty.notify.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yujuncai
 * oss配置模板
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOssProperties {

    private String endpoint;

    private String bucket;

    private String accessKeyId;

    private String accessKeySecret;

    @Bean(name = "ossClient", destroyMethod = "shutdown")
    public OSSClient ossClient() {
        return (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
