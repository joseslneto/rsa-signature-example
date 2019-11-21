package br.com.example.rsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;

@SpringBootApplication
public class RsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsaApplication.class, args);
    }
}
