package com.egin.auth.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@RequiredArgsConstructor
public enum ConfigurationParameters {

    ISSUER("ISSUER"),

    AUTH_ACCESS_TOKEN_EXPIRE_MINUTE("30"),
    AUTH_REFRESH_TOKEN_EXPIRE_DAY("1"),
    AUTH_PUBLIC_KEY("""
            -----BEGIN PUBLIC KEY-----
            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyLGJvtHol9dum2mpLF4l
            jUw1EH/jnX3DLg1kU/kpyPWHPqywnxFPOAlYT1dPATT0f+r+jvMQ9zaqi4Autrnn
            0TsjW12mQuc5Uz+lXtd29NNmSwhMyvjDpyvb6JA1j/NS/8iokhvveHc27RYwLKBM
            I29LPm3FPRYbSdp6LQmwMAXFHf/00s4bMsGZkg1k3VQAq9YObZ6preVTp8yLbb2t
            fKYW9HAKZE6N4SayITeAJ2561E8ErFQF7rQsts/4EeQkEi9xtad55EzY2emvcBIT
            YpQqLgO1uZ3k3i6EhhEFVIFiFECnE1Pq+1tmCOjl/J/W7+Dj/8JxszkHC13x+oSl
            4wIDAQAB
            -----END PUBLIC KEY-----
                        """),
    AUTH_PRIVATE_KEY("""
            -----BEGIN PRIVATE KEY-----
            MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIsYm+0eiX126b
            aaksXiWNTDUQf+OdfcMuDWRT+SnI9Yc+rLCfEU84CVhPV08BNPR/6v6O8xD3NqqL
            gC62uefROyNbXaZC5zlTP6Ve13b002ZLCEzK+MOnK9vokDWP81L/yKiSG+94dzbt
            FjAsoEwjb0s+bcU9FhtJ2notCbAwBcUd//TSzhsywZmSDWTdVACr1g5tnqmt5VOn
            zIttva18phb0cApkTo3hJrIhN4AnbnrUTwSsVAXutCy2z/gR5CQSL3G1p3nkTNjZ
            6a9wEhNilCouA7W5neTeLoSGEQVUgWIUQKcTU+r7W2YI6OX8n9bv4OP/wnGzOQcL
            XfH6hKXjAgMBAAECggEAOG1mf87wGUU6tZqrNKzR3R0IrHdwzXavOpMMWunq/DRA
            jvgp/sd0oRpLiJ9RONMlTZQKd7HS2tc1c1yahcjWa4c3pTg7B4OQpB34hqaJLUQE
            9mLCiK0SAb48qfJmBO/SrdVCxDXzikjmmhmLePim/62Kl+ZwNwEwsf6qLGCVd6Kg
            Cdkd8awKc0zWrGx9rwvCxSyPPY7Ph014xt+ADbaymNtsGJ4D43BzyiBxAmTcwHbT
            7XrhXq/12JO0mSqIVLf+YAIAFyLpj+k/7qsuuvC0Bz9rlQGhLFFIlLHpEbssKzH6
            568Gq4FfWG7aj+EbgkZvKrGsb7ZuyvbroAvvjGvVFQKBgQDvCCQep6980G3iGP+3
            6p1+SfY2CdvWbRkiseHKlAKilZKlC1CHOWU4CJ570rR9wXZWXDBCpM6qKto8Oxrn
            7PqRWDK4dOo5zFuK7rGQo4E7ljWPVnK5Bs/lK/k3e4rFj8u5wz3M4UFpyzUgM8vK
            Sr7yIDeDN3SBunbZq8OMs9siXwKBgQDW8K+OMQnKlH2h4SNDjE7iOzSbnOxX5TGj
            GBN9HK9DkIr8/H7RifLwbrn5n8tz27KhUlc4p3P0+94JD9GwkEX0Hd2va/JRqemR
            cKrLeVHU3+BHIW7EHOR6qeZ5osLL3FNgc9P6sOMm/YGyKshiEI9wksVLVxIyf/u/
            rKyCxBcS/QKBgGcOu4P8CE1oD/hLjvVc0kqA2QazeXUZ1JyA1TjiV8CBfJhsKGwk
            8t8V2D7Kucb+gmb5mFUjeXckwrL1SMR2CBJiVHvy7Im+8jkUcLpLxIxMVwtV2mCr
            7zZ/nxOs0Egdl1igNSUAeZODaYRY0KSHVYxPF6AbaMAGBPgUdltTsEdBAoGAGcIy
            Z8n8sGhnPfUrMrcn5PjPby65LDdtYWxLOZ0sa95wqazgzd/IW4QJiZ7a1QGpQKgL
            h+CZhuyASXm52X0QxRanMJSxL5Q7R5DHByd85RQHXr7k9V+5CfI+iqk0yxWWcGKx
            LsPwFQYgeuehqA7wAb+1IThIGAE6fFgHPIyfb0kCgYEAhF0AnDke3cyhRkBCnlrR
            djXfVNBY3NFpHvCHOCwNGOoASCoXnDO8qkUYyHe4DXjOBm3jBSeXQK6cwOgABpVE
            LgpywQfDjM5ue4ZlrYz3Fap93ZhMpKJUnrZXmwD4isXz3bEwcEdMQYus6KeFHg3S
            E3Nh7bI52byZ+zps7zCucSA=
            -----END PRIVATE KEY-----
            """);


    private final String value;



}
