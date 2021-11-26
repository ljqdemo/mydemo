package com.ljq.mydemo.hutool.sm;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.util.Base64;

/**
 * SM2国密加密
 *
 * https://hutool.cn/docs/#/crypto/%E5%9B%BD%E5%AF%86%E7%AE%97%E6%B3%95%E5%B7%A5%E5%85%B7-SmUtil
 * @author gino
 * 2021-11-26
 */
public class Test {
    public static void main(String[] args) {
        String text = "aaaaa-----aaaa";

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);

        System.out.println(encryptStr);
        System.out.println(Base64.getDecoder().decode(encryptStr.getBytes()));
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));

        System.out.println(decryptStr);

    }
}
