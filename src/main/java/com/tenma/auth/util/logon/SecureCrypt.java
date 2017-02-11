package com.tenma.auth.util.logon;

import com.sun.crypto.provider.SunJCE;
import org.apache.ibatis.io.Resources;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by IntelliJ IDEA.
 * User: ADITYA
 * Date: 9/19/11
 * Time: 3:30 AM
 * To change this template use File | Settings | File Templates.
 */
class SecureCrypt {
    private static SecretKey pbeKey = null;
    /* The salt used to make the encryption more secure */
    private static byte salt[] = {-57, 115, 33, -116, 126, -56, -18, -103};
    /* Number of iteration, how many time the text are salted and hashed */
    private static int count;
    private static PBEParameterSpec pbeParamSpec;
    /* The instance of this SecureCrypt class */
    private static SecureCrypt instance;

    static {
        count = 20;
        pbeParamSpec = new PBEParameterSpec(salt, count);
    }

    /* This keys is the key structure of the configuration file */
    final String CRYPT = "crypt";
    final String KS_FILE = "keystore-file";
    final String KS_PASS = "keystore-pass";
    final String KS_KEY_ALIAS = "keystore-key-alias";
    final String KS_KEY_PASS = "keystore-key-pass";
    /* The system dependent file separator */
    final String CONFIG = "xml/crypt.xml";
    /* The encryption algorithm used in this class */
    final String ALGORITHM = "PBEWithMD5AndDES";
    /* The encode type that is used to encode the plain text and cipher text */
    final String ENCODE_TYPE = "UTF8";
    /* The location of the keystore file to be generated */
    String ksFile = "";
    private String ksPass = null;
    private String keyAlias = null;
    private String keyPass = null;

    /**
     * Constructs a new instance of <code>SecureCrypt</code> class.
     * By default this constructor reads the xml configuration file from the
     */
    private SecureCrypt() throws Exception {
        init();
    }

    // ------------------------------------------------------- Private Methods

    /**
     * Gets a new instance of <code>SecureCrypt</code>.
     *
     * @return an instance of crypt.
     */
    public static SecureCrypt getInstance() throws Exception {
        if (instance == null) {
            instance = new SecureCrypt();
        }
        return instance;
    }

    // -------------------------------------------------------- Public Methods

    public static void main(String[] arg) {
        try {
            String s = SecureCrypt.getInstance().encrypt("pwd");
            System.out.println(s);
            s = SecureCrypt.getInstance().encrypt("02|12|2016");
            System.out.println("s = " + s);
            s = SecureCrypt.getInstance().decrypt("q8VL0hjjmqc=");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the <code>SecureCrypt</code> class. This method reads all the
     * configuration information from the configuration file.
     */
    private void init() throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();
            File f = Resources.getResourceAsFile(CONFIG);
            Document doc = builder.build(f);
            Element root = doc.getRootElement().getChild(CRYPT);
            ksFile += root.getChild(KS_FILE).getText();
            ksPass = root.getChild(KS_PASS).getText();
            keyAlias = root.getChild(KS_KEY_ALIAS).getText();
            keyPass = root.getChild(KS_KEY_PASS).getText();

            Security.addProvider(new SunJCE());
            char ac[] = new char[ksPass.length()];
            ksPass.getChars(0, ksPass.length(), ac, 0);

            char ac1[] = new char[keyPass.length()];
            keyPass.getChars(0, keyPass.length(), ac1, 0);

            KeyStore keystore = KeyStore.getInstance("JCEKS");

            File file = new File(ksFile);
            if (file.exists()) {
                keystore.load(new FileInputStream(ksFile), ac);
            } else {
                keystore.load(null, ac);
            }

            if (!keystore.containsAlias(keyAlias)) {
                PBEKeySpec pbekeyspec = new PBEKeySpec(ac1);
                SecretKeyFactory secretkeyfactory =
                        SecretKeyFactory.getInstance(ALGORITHM);
                pbeKey = secretkeyfactory.generateSecret(pbekeyspec);
                keystore.setKeyEntry(keyAlias, pbeKey, ac1, null);
                keystore.store(new FileOutputStream(ksFile), ac);
            } else {
                pbeKey = (SecretKey) keystore.getKey(keyAlias, ac1);
            }
        } catch (FileNotFoundException e) {
            SAXBuilder builder = new SAXBuilder();
            String path = System.getProperty("JBOSS_HOME");
            System.out.println("path = " + path);
            System.out.println("CONFIG = " + CONFIG);
            System.out.println(path + File.separator + CONFIG);
            File f = Resources.getResourceAsFile(path + File.separator + CONFIG);
            Document doc = builder.build(f);
            Element root = doc.getRootElement().getChild(CRYPT);
            ksFile += root.getChild(KS_FILE).getText();
            ksPass = root.getChild(KS_PASS).getText();
            keyAlias = root.getChild(KS_KEY_ALIAS).getText();
            keyPass = root.getChild(KS_KEY_PASS).getText();

            Security.addProvider(new SunJCE());
            char ac[] = new char[ksPass.length()];
            ksPass.getChars(0, ksPass.length(), ac, 0);

            char ac1[] = new char[keyPass.length()];
            keyPass.getChars(0, keyPass.length(), ac1, 0);

            KeyStore keystore = KeyStore.getInstance("JCEKS");

            File file = new File(ksFile);
            if (file.exists()) {
                System.out.println("SecureCrypt.init 1");
                keystore.load(new FileInputStream(ksFile), ac);
            } else {
                System.out.println("File not exist" + ksFile);
                keystore.load(null, ac);
            }

            if (!keystore.containsAlias(keyAlias)) {
                System.out.println("SecureCrypt.init 3");
                PBEKeySpec pbekeyspec = new PBEKeySpec(ac1);
                SecretKeyFactory secretkeyfactory =
                        SecretKeyFactory.getInstance(ALGORITHM);
                pbeKey = secretkeyfactory.generateSecret(pbekeyspec);
                keystore.setKeyEntry(keyAlias, pbeKey, ac1, null);
                keystore.store(new FileOutputStream(ksFile), ac);
            } else {
                System.out.println("SecureCrypt.init 4");
                pbeKey = (SecretKey) keystore.getKey(keyAlias, ac1);
            }

        } catch (JDOMException e) {
            throw new Exception(e.getMessage());
        } catch (KeyStoreException e) {
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (CertificateException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new Exception(e.getMessage());
        } catch (UnrecoverableKeyException e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Decrypts a cipher text message into a plain text message using
     * PBEWithMD5AndDES algorithm and the key specified in the configuration
     * file.
     *
     * @param cipherText a string to be decrypted.
     * @return a plain text string from the cipher text.
     */
    public String decrypt(String cipherText) throws Exception {
        String plainText = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
            final byte[] dec =
                    new sun.misc.BASE64Decoder().decodeBuffer(cipherText);
            final byte[] utf8 = cipher.doFinal(dec);
            plainText = new String(utf8, ENCODE_TYPE);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalStateException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
            throw new Exception(e.getMessage());
        }
        return plainText;
    }

    /**
     * Encrypts a plain text message into a cipher text message using
     * PBEWithMD5AndDES algorithm and the key specified in the configuration
     * file.
     *
     * @param plainText a string to be encrypted.
     * @return a cipher text of the plain text.
     */
    public String encrypt(String plainText) throws Exception {
        String cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
            cipherText = null;
            final byte[] utf8 = plainText.getBytes(ENCODE_TYPE);
            final byte[] enc = cipher.doFinal(utf8);
            cipherText = new sun.misc.BASE64Encoder().encode(enc);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalStateException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
            throw new Exception(e.getMessage());
        }
        return cipherText;
    }
}