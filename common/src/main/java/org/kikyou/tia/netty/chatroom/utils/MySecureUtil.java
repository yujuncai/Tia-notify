package org.kikyou.tia.netty.chatroom.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import io.micrometer.common.util.StringUtils;

import java.util.Base64;

/**
 * @desc: 借助hutool工具 整合加解密工具
 */
public class MySecureUtil {

    /**
     * @desc: 返回AES算法使用的随机密钥key
     * @return Base64格式的key
     */

    public static void main(String[] args) {
        String s = generateAesKey();
        System.out.println(s);

        String s1 = aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", "1111111111");
        String s2 = aesDecrypt("jvZJhHtp3vOVmpool6QlMw==", s1);
        System.out.println(s2);
    }

    public static String generateAesKey(){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * @param key base64格式的key
     * @param content 待加密文本
     * @return 加密后文本
     */
    public static String aesEncrypt(String key,String content){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(content)){
            return "";
        }
        byte[] decode = Base64.getDecoder().decode(key);
        return SecureUtil.aes(decode).encryptBase64(content);
    }

    /**
     * @param key
     * @param content
     * @return 解密后文本
     */
    public static String aesDecrypt(String key,String content){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(content)){
            return "";
        }
        byte[] decode = Base64.getDecoder().decode(key);
        return SecureUtil.aes(decode).decryptStr(content);
    }

}