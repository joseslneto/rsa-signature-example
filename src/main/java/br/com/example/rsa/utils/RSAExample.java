package br.com.example.rsa.utils;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class RSAExample {

    private static Integer MAX_SIZE_OF_CHUNK = 32;
    private static String KEY_STORE_PWD = "test12";
    private static String KEY_STORE_ALIAS = "testRsa";
    private static String KEY_STORE_INSTANCE = "JCEKS";

    public KeyPair getKeyPairFromKeyStore() throws Exception {
        final InputStream ins = RSAExample.class.getResourceAsStream("/keys/testRsa.jks");

        final KeyStore keyStore = KeyStore.getInstance(KEY_STORE_INSTANCE);
        keyStore.load(ins, KEY_STORE_PWD.toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword =       //Key password
                new KeyStore.PasswordProtection(KEY_STORE_PWD.toCharArray());

        final KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_STORE_ALIAS, keyPassword);

        final Certificate cert = keyStore.getCertificate(KEY_STORE_ALIAS);
        final PublicKey publicKey = cert.getPublicKey();
        final PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }

    public String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        System.out.println("plain text bytes");
        System.out.println(Arrays.toString(plainText.getBytes(UTF_8)));
        privateSignature.update(plainText.getBytes(UTF_8));
        System.out.println("Algorithm");
        System.out.println(privateSignature.getAlgorithm());

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }

    /*public void divideChunks(final String plainText) {
        List<Character> charList = plainText.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<String> stringList = charList.stream().map(String::valueOf).collect(Collectors.toList());
        List<Byte> byteList = new ArrayList<>();
        for (String s : stringList) {
            byte[] bytes = s.getBytes();
            for (byte aByte : bytes) {
                byteList.add(aByte);
            }
        }

        System.out.println("teste");
        System.out.println(Partition.ofSize(byteList, 2));
    }*/
}
