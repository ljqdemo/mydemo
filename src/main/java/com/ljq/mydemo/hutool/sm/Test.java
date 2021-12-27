package com.ljq.mydemo.hutool.sm;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.security.KeyPair;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * SM2国密加密
 *
 * https://hutool.cn/docs/#/crypto/%E5%9B%BD%E5%AF%86%E7%AE%97%E6%B3%95%E5%B7%A5%E5%85%B7-SmUtil
 * @author gino
 * 2021-11-26
 */
public class Test {
    public static void main(String[] args) {


        Map<String,String> map=new HashMap<>();
        Map<String, String> finalMap = map;
        System.out.println(finalMap.get("11"));
        Integer a=1;
        Long b=1L;
        if ((a==b.intValue())) {

        }

String text="assdasdada---11111";
            LocalDateTime now = LocalDateTime.now();
            System.out.println(now.toString());

            System.out.println( now.plusDays(7));



        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        System.out.println(encryptStr);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));

        System.out.println(decryptStr);


        System.out.println(decryptStr);

    }
}
