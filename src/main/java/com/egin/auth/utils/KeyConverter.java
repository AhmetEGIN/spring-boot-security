package com.egin.auth.utils;

import lombok.experimental.UtilityClass;
//import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

@UtilityClass
public class KeyConverter {

    public static PublicKey convertPublicKey(String publicKey) {
        final StringReader reader = new StringReader(publicKey);
        try {
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo
                    .getInstance(new PEMParser(reader).readObject());
            return new JcaPEMKeyConverter().getPublicKey(publicKeyInfo);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert public key", e);
        }
    }

    public static PrivateKey convertPrivateKey(String privateKey) {
        StringReader reader = new StringReader(privateKey);
        try {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo
                    .getInstance(new PEMParser(reader).readObject());
            return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
