package org.web3j.spring.wallet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;


@Service
public class Web3jService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Admin web3jAdmin;

    //private Credentials credentials;

    @Autowired
    private Transfer transfer;


    private static final Logger log = LoggerFactory.getLogger(Web3jService.class);


    public Credentials loadCredentials(String password, String jsonFile) throws IOException, CipherException {
        //credentials = WalletUtils.loadCredentials(password,  "/UTC--2017-08-21T11-49-30.013Z--8c17ea160c092ae854f81580396ba570d9e62e24.json");
        return WalletUtils.loadCredentials(password, jsonFile);
    }

    public Credentials loadCredentialsByJsonFile(String password, File jsonFile) throws IOException, CipherException {
        //credentials = WalletUtils.loadCredentials(password,  "/UTC--2017-08-21T11-49-30.013Z--8c17ea160c092ae854f81580396ba570d9e62e24.json");
        return WalletUtils.loadCredentials(password, jsonFile);
    }


    public TransactionReceipt transaction(String address, double ethBalance, Credentials credentials) throws Exception {
        return Transfer.sendFunds(web3j, credentials, address, BigDecimal.valueOf(ethBalance), Convert.Unit.ETHER).send();
    }


    public BigInteger getBalance(String address) {
        Future<EthGetBalance> ethGetBalanceFuture = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync();
        try {
            return ethGetBalanceFuture.get().getBalance();
        } catch (Exception e) {
            return BigInteger.ONE;
        }
    }

    public String getWalletAddress(Credentials credentials) {
        return credentials.getAddress();
    }

    public String getClientVersion() {
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3j.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return web3ClientVersion.getWeb3ClientVersion();
    }

    public String newWallet(String password) throws Exception {
        String fileName = WalletUtils.generateNewWalletFile(
                password, new File("src/main/resources/wallet"), false);
        String[] fetchAddress = fileName.split("--");
        String getAddress = fetchAddress[fetchAddress.length - 1].split("\\.")[0];

        log.info("walletFileName>>>>>" + fileName.substring(0));
        log.info("walletFile Address>>>>>" + "0x" + getAddress);

        return fileName;
    }


    public BigInteger getNonce(String address) throws ExecutionException, InterruptedException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        return nonce;
    }

    public void unlockWallet(String address, String password, String toAddress, double ethBalance) throws ExecutionException, InterruptedException {
        PersonalUnlockAccount personalUnlockAccount = web3jAdmin.personalUnlockAccount(address, password).sendAsync().get();
        if (personalUnlockAccount.accountUnlocked()) {
            transfer.sendFunds(toAddress, BigDecimal.valueOf(ethBalance), Convert.Unit.ETHER);
        }
    }

//    public void getJsonFileByAddresee(String address) {
//        for(File file : )
//    }
}



