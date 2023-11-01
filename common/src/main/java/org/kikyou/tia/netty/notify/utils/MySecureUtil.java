package org.kikyou.tia.netty.notify.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.json.JSONUtil;
import io.micrometer.common.util.StringUtils;
import org.kikyou.tia.netty.notify.vo.SignatureTime;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @desc: 借助hutool工具 整合加解密工具
 */
public class MySecureUtil {
    private static final String IV_KEY = "0000000000000000";
    /**
     * @return Base64格式的key
     * @desc: 返回AES算法使用的随机密钥key
     */

    public static void main(String[] args) {
       // String s = generateAesKey();
       // System.out.println(s);
        SignatureTime vo=new SignatureTime();
        vo.setSignature("/tia-java");
        vo.setTimes(DateUtil.current());

        for (int i = 0; i <10; i++) {

            String json = JSONUtil.toJsonStr(vo);
            String signature = MySecureUtil.aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", json);
            System.out.println(signature);
            String s2 = aesDecrypt("jvZJhHtp3vOVmpool6QlMw==", signature);
            System.out.println(s2);
        }

        String s2 = aesDecrypt("jvZJhHtp3vOVmpool6QlMw==", "ZR2awnVp6cYBddStG3w2v/5gTM96pgegXPfVZOxHPsEkuA+78esexdY4t3EeeGG5");
       // String s3 = aesDecrypt("jvZJhHtp3vOVmpool6QlMw==", "ZR2awnVp6cYBddStG3w2v%2F5gTM96pgegXPfVZOxHPsFBc9A%2027VbARnch5hI9ybp");

        System.out.println(s2);
       // System.out.println(s3);


    }

    public static String generateAesKey() {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * @param key     base64格式的key
     * @param content 待加密文本
     * @return 加密后文本
     */
    public static String aesEncrypt(String key, String content) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(content)) {
            return "";
        }
        String encryptData = encryptFromString(content, Mode.CBC, Padding.ZeroPadding,key);
        return encryptData;
    }

    /**
     * @param key
     * @param content
     * @return 解密后文本
     */
    public static String aesDecrypt(String key, String content) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(content)) {
            return "";
        }
        //get 传输时 + 号 被换成了 %20 后,成了空格,需要将空格替换回+
        content = content.replaceAll(" ", "\\+");
        String decryptData = decryptFromString(content, Mode.CBC, Padding.ZeroPadding,key);
        return decryptData;
    }



    public static String encryptFromString(String data, Mode mode, Padding padding,String key) {
        AES aes;
        if (Mode.CBC == mode) {
            aes = new AES(mode, padding,
                    new SecretKeySpec(key.getBytes(), "AES"),
                    new IvParameterSpec(IV_KEY.getBytes()));
        } else {
            aes = new AES(mode, padding,
                    new SecretKeySpec(key.getBytes(), "AES"));
        }
        return aes.encryptBase64(data, StandardCharsets.UTF_8);
    }

    public static String decryptFromString(String data, Mode mode, Padding padding,String key) {
        AES aes;
        if (Mode.CBC == mode) {
            aes = new AES(mode, padding,
                    new SecretKeySpec(key.getBytes(), "AES"),
                    new IvParameterSpec(IV_KEY.getBytes()));
        } else {
            aes = new AES(mode, padding,
                    new SecretKeySpec(key.getBytes(), "AES"));
        }
        byte[] decryptDataBase64 = aes.decrypt(data);
        return new String(decryptDataBase64, StandardCharsets.UTF_8);
    }







}