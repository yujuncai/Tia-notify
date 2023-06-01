package org.kikyou.tia.netty.notify.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;


public class MyFileUtils {

    public static File base64ToFile(String base64FileStr) throws Exception {
        String fileType = getImgType(base64FileStr);
        if (base64FileStr.contains("data:image")) {
            base64FileStr = base64FileStr.substring(base64FileStr.indexOf(",") + 1);
        }
        base64FileStr = base64FileStr.replace("\r\n", "");
        // 在用户temp目录下创建临时文件
        File file = File.createTempFile(UUID.randomUUID().toString(), fileType);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            // 用Base64进行解码后获取的字节数组可以直接转换为文件
            byte[] bytes = Base64.getDecoder().decode(base64FileStr);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private static String getImgType(String imageStr) {
        if (StrUtil.isBlank(imageStr)) {
            return ".jpg";
        }
        return "." + StrUtil.split(StrUtil.split(imageStr, "/").get(1), ";").get(0);
    }

    /**
     * 图片转化成base64字符串
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String getImageStr(File imgFile) {
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        return new String(Base64Utils.encode(data), StandardCharsets.UTF_8);
    }


}
