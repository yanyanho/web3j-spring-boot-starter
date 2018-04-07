package org.web3j.spring.wallet;


import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.Future;


public class WalletUtil {


    @Autowired
    private Web3j web3j;

    private  Credentials credentials;

    private  TransactionReceipt transactionReceipt;



    public  void loadCredentials(String password, String jsonFile) throws IOException, CipherException {
        //credentials = WalletUtils.loadCredentials(password,  "/UTC--2017-08-21T11-49-30.013Z--8c17ea160c092ae854f81580396ba570d9e62e24.json");
        credentials = WalletUtils.loadCredentials(password,  jsonFile);
    }


    public  TransactionReceipt transaction(String address, double ethBalance) throws Exception {
        return transactionReceipt = Transfer.sendFunds(web3j, credentials, address, BigDecimal.valueOf(ethBalance), Convert.Unit.ETHER).send();
    }


    public  BigInteger getBalance() {
        Future<EthGetBalance> ethGetBalanceFuture = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync();
        try {
            return ethGetBalanceFuture.get().getBalance();
        } catch (Exception e) {
            return BigInteger.ONE;
        }
    }

    public  String getWalletAddress() {
        return credentials.getAddress();
    }
}
