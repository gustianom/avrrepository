package com.tenma.auth.util.logon;

/**
 * Created by gustianom on 20/11/14.
 */
public class TenmaEncrypt {
    public static String doEncrypt(String key) throws Exception {
        return SecureCrypt.getInstance().encrypt(key);
    }

    public static String doDecrypt(String key) throws Exception {
        return SecureCrypt.getInstance().decrypt(key);
    }
}
