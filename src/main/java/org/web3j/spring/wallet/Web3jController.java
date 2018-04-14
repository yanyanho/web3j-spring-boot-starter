package org.web3j.spring.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class Web3jController {
    @Autowired
    Web3jService web3jService;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    String getVersion() {
        return web3jService.getClientVersion();
    }

//    @RequestMapping(value = "/new-wallet/{password}", method = RequestMethod.GET)
//    MultipartFile getNewWallert1(@PathVariable String password) throws Exception {
//        return web3jService.newWallet(password);
//    }

    @RequestMapping(value = "/new-wallet/{password}", method = RequestMethod.GET)
    ResponseEntity<InputStreamResource> getNewWallert(@PathVariable String password) throws Exception {
        File file = web3jService.newWallet(password);
        return ResponseEntity.ok().headers(headers(file)).body(new InputStreamResource(new FileInputStream(file)));
    }


    @RequestMapping(value = "/{address}/balance", method = RequestMethod.GET)
    BigInteger getBalance(@PathVariable String address) {
        return web3jService.getBalance(address);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    TransactionReceipt transfer(@RequestParam String address, @RequestParam double value, @RequestParam Credentials credentials) throws Exception {
        return web3jService.transaction(address, value, credentials);
    }


//    @RequestMapping(value="/transfer/password", method = RequestMethod.GET)
//    void transfer(@RequestParam  String address , @RequestParam  String password , @RequestParam  String toAddress , @RequestParam double value) throws Exception {
//           web3jService.unlockWallet(address, password, toAddress,  value);
//    }


    public HttpHeaders headers(File file) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename*=UTF-8''"+encode(file.getName()));
        return httpHeaders;
    }

    private String encode(String name){
        try {
            return URLEncoder.encode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
