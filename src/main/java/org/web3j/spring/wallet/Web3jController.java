package org.web3j.spring.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;


@RestController
public class Web3jController {
    @Autowired
    Web3jService web3jService;
    public static final Logger logger = LoggerFactory.getLogger(Web3jController.class);

    BigInteger eth = new BigInteger("1000000000000");

    //创建钱包 输入密码，返回json文件
    @RequestMapping(value = "/new-wallet", method = RequestMethod.GET)
    ResponseEntity<InputStreamResource> createNewWallet(@RequestParam String password) throws Exception {
        FileContent fileContent = web3jService.newWallet(password);
        return ResponseEntity.ok().headers(headers(fileContent.getFileName())).body(new InputStreamResource(fileContent.getInputStream()));
    }

    // 导入钱包 key.json文件导入
    @RequestMapping(value = "/load-wallet", method = RequestMethod.POST)
    Credentials loadCredentialsByFJsonFileAndPassword(@RequestParam String password, MultipartFile jsonFile ) throws Exception {
      return  web3jService.loadCredentialsByJsonFile(password, jsonFile);
    }

    // 导入钱包 私钥导入 区分十六进制还是十进制
    @RequestMapping(value = "/load-wallet", method = RequestMethod.GET)
    Credentials loadCredentialsByPrivateKey(@RequestParam String privateKey ) throws Exception {
        String pkHex= privateKey;
        if(privateKey.length()!=64) {
            pkHex = new BigInteger(privateKey).toString(16);
        }
        return Credentials.create(pkHex);
    }

   //查看余额
    @RequestMapping(value = "/{address}/balance", method = RequestMethod.GET)
    double getBalance(@PathVariable String address) {
        return web3jService.getBalance(address).divide(eth).doubleValue()/1000000;
    }

    //转账
    @RequestMapping(value = "/transfer", method = RequestMethod.PUT)
    TransactionReceipt transfer(@RequestParam String address, @RequestParam double value, @RequestParam String privateKey ) throws Exception {
        String pkHex = new BigInteger(privateKey).toString(16);
        return web3jService.transaction(address, value,  Credentials.create(pkHex));
    }

    //转账ERC20
    @RequestMapping(value="/transfer/erc20", method = RequestMethod.PUT)
    String transferErc20(@RequestParam  String contractAddress ,@RequestParam  String privateKey , @RequestParam String toAddress, @RequestParam double amount) throws Exception {
        String pkHex= privateKey;
        if(privateKey.length()!=64) {
             pkHex = new BigInteger(privateKey).toString(16);
        }
        return   web3jService.transactionErc20Token(contractAddress, Credentials.create(pkHex), toAddress, amount);
    }


    //获取交易详情
    @RequestMapping(value = "/transaction/{transactionHash}", method = RequestMethod.GET)
    TransactionReceipt transfer(@RequestParam String transactionHash) throws Exception {
        return web3jService.ethGetTransactionReceipt(transactionHash);
    }

    private String encode(String name){
        try {
            return URLEncoder.encode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpHeaders headers(String fileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename*=UTF-8''"+encode(fileName));
        return httpHeaders;
    }


    //    @RequestMapping(value="/transfer/password", method = RequestMethod.GET)
//    void transfer(@RequestParam  String address , @RequestParam  String password , @RequestParam  String toAddress , @RequestParam double value) throws Exception {
//           web3jService.unlockWallet(address, password, toAddress,  value);
//    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    String getVersion() {
        return web3jService.getClientVersion();
    }


}
