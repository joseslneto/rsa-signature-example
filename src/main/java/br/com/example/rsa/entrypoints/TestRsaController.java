package br.com.example.rsa.entrypoints;

import br.com.example.rsa.utils.RSAExample;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.KeyPair;
import java.security.PrivateKey;

@Controller
@AllArgsConstructor
public class TestRsaController {

    private final RSAExample rsaExample;

    @GetMapping("/rsa/test")
    public ResponseEntity testRsa() throws Exception {
        final KeyPair keyPair = rsaExample.getKeyPairFromKeyStore();
        final String signature = rsaExample.sign("foobar", keyPair.getPrivate());
//        rsaExample.divideChunks("foobar");
        System.out.println("Signature:");
        System.out.println(signature);

        //Let's check the signature
        final Boolean isCorrect = rsaExample.verify("f00bar", signature, keyPair.getPublic());
        System.out.println("Signature correct: " + isCorrect);

        return ResponseEntity.ok(isCorrect);
    }

    @GetMapping("/rsa/private-key")
    public ResponseEntity getPrivateKey() throws Exception {
        final PrivateKey privateKey = rsaExample.getKeyPairFromKeyStore().getPrivate();
        System.out.println(privateKey);
        System.out.println(privateKey);
        System.out.println(privateKey.getEncoded());
        System.out.println(privateKey.getFormat());
        System.out.println(privateKey.getAlgorithm());
        return ResponseEntity.status(HttpStatus.OK).body(privateKey);
    }
}
