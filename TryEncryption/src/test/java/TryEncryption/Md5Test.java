package TryEncryption;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Test {
    @Test
    public void testSomething() throws NoSuchAlgorithmException {
        String str = "银河系的极光";
        // 定义编码
        String algorithm = "MD5";
        // 获取消息摘要算法对象
        MessageDigest md = MessageDigest.getInstance(algorithm);

        // 获取原始内容的字节数组
        byte[] originalBytes = str.getBytes(StandardCharsets.UTF_8);

        // 获取摘要结果
        byte[] digestBytes = md.digest(originalBytes);

        // 把每一个字节转换为16进制字节，最后再将它们拼接起来
        String hexStr = convertBytes2HexStr(originalBytes);
        System.out.println(hexStr);

        String md5code = new BigInteger(1, digestBytes).toString(16);

        System.out.println(md5code);
    }

    private String convertBytes2HexStr(byte[] originalBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte bt : originalBytes) {
            // 获取b补码后的八位
            String hex = Integer.toHexString(((int)bt)&0xff);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            stringBuffer.append(hex);
        }
        return stringBuffer.toString();
    }
}
