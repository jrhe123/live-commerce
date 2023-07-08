package org.example.live.common.interfaces.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DESUtils {

    // ALGORITHM
    public static final String KEY_ALGORITHM = "DES";
    // 算法名称/加密模式/填充方式
    // DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    public static final String PUBLIC_KEY = "BAS9j2C3D4E5F60708";

    /**
     * 生成密钥key对象
     *
     * @param keyStr 密钥字符串
     * @return secure key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = new DESKeySpec(input);
        // create key factory
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /**
     * hex string to bytes[]
     * 
     * @param hexstr
     * @return
     */
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * encrypt
     *
     * @param raw data string
     * @return encrypted
     */
    public static String encrypt(String data) {
        Key deskey = null;
        try {
            deskey = keyGenerator(PUBLIC_KEY);
            // get instance of cipher
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecureRandom random = new SecureRandom();
            // init Cipher, set encrypt mode
            cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
            byte[] results = cipher.doFinal(data.getBytes());
            // return base64 string
            return Base64.encodeBase64String(results);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * decrypt
     *
     * @param encrypted data
     * @return raw data
     */
    public static String decrypt(String data) {
        Key deskey = null;
        try {
            deskey = keyGenerator(PUBLIC_KEY);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // init Cipher, set decrypt mode
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            // exec decrypt
            return new String(cipher.doFinal(Base64.decodeBase64(data)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        String phone = "17818723311";
        String encryptStr = DESUtils.encrypt(phone);
        String decryStr = DESUtils.decrypt(encryptStr);
        System.out.println(encryptStr);
        System.out.println(decryStr);
    }

}