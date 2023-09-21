package com.ruoyi.lcp.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UniqueIdUtils {
    public static long generateUniqueId(String input) {
        try {
            // 创建 SHA-256 摘要
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将输入字符串转换为字节数组
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将字节数组转换为 BigInteger 对象
            BigInteger bigInt = new BigInteger(1, hashBytes);

            // 将 BigInteger 转换为 long 值
            return bigInt.longValue();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    public static String reverseUniqueId(long uniqueId) {
        // 将 long 值转换为 BigInteger 对象
        BigInteger bigInt = BigInteger.valueOf(uniqueId);

        try {
            // 创建 SHA-256 摘要
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将 BigInteger 对象转换为字节数组
            byte[] hashBytes = bigInt.toByteArray();

            // 计算哈希值
            byte[] hash = digest.digest(hashBytes);

            // 将字节数组转换为字符串
            return new String(hash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
