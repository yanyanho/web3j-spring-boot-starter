package org.web3j.spring.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.spring.token.Erc20TokenWrapper;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;



//https://stevenocean.github.io/2018/04/06/web3j-ethereum-token.html
@Service
public class Web3jService {


    @Autowired
    Web3j web3j;

    public static final Logger logger = LoggerFactory.getLogger(Web3jService.class);

    public static final BigInteger gas_price = BigInteger.valueOf(2_000_000_000L);

    //private Credentials credentials;

//    @Autowired
//    private Transfer transfer;


    private static final Logger log = LoggerFactory.getLogger(Web3jService.class);


//    public Credentials loadCredentials(String password, String jsonFile) throws IOException, CipherException {
//        //credentials = WalletUtils.loadCredentials(password,  "/UTC--2017-08-21T11-49-30.013Z--8c17ea160c092ae854f81580396ba570d9e62e24.json");
//        return WalletUtils.loadCredentials(password, jsonFile);
//    }

    public Credentials loadCredentialsByJsonFile(String password, MultipartFile jsonFile) throws IOException, CipherException {

        //File dir = new File(new ClassPathResource("wallet").getPath());
        //dir.mkdirs();
       // File convFile = new File(new ClassPathResource("wallet/"+jsonFile.getName()).getPath());
       // logger.info("**************+convFile is ***" +convFile.getAbsolutePath() );
        //convFile.mkdirs();
        //jsonFile.transferTo(convFile);
       // File file= new File("1.json");
        File convFile = new File(jsonFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(jsonFile.getBytes());
        fos.close();


        //credentials = WalletUtils.loadCredentials(password,  "/UTC--2017-08-21T11-49-30.013Z--8c17ea160c092ae854f81580396ba570d9e62e24.json");
        return WalletUtils.loadCredentials(password, convFile);
    }


    public CompletableFuture<TransactionReceipt> transaction(String address, double ethBalance, Credentials credentials) throws Exception {
        return Transfer.sendFunds(web3j, credentials, address, BigDecimal.valueOf(ethBalance), Convert.Unit.ETHER).sendAsync();
    }


    public BigInteger getBalance(String address) {
        try {
            return   web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return BigInteger.ZERO;
    }

    public String getWalletAddress(Credentials credentials) {
        return credentials.getAddress();
    }


    public TransactionReceipt ethGetTransactionReceipt(String transactionHash) throws IOException {
       return  web3j.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt().get();
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

    public FileContent newWallet(String password) throws Exception {
        String fileName = WalletUtils.generateNewWalletFile(
                password, new ClassPathResource("wallet").getFile(), false);
        Thread.sleep(1000);

        InputStream inputStream = new ClassPathResource("wallet/"+fileName).getInputStream();
        return new FileContent(fileName,inputStream);
//        return null;
//        String[] fetchAddress = fileName.split("--");
//        String getAddress = fetchAddress[fetchAddress.length - 1].split("\\.")[0];
//
//        log.info("walletFileName>>>>>" + fileName.substring(0));
//        log.info("walletFile Address>>>>>" + "0x" + getAddress);
    }


    public BigInteger getNonce(String address) throws ExecutionException, InterruptedException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        return nonce;
    }

//    public void unlockWallet(String address, String password, String toAddress, double ethBalance) throws ExecutionException, InterruptedException {
//        PersonalUnlockAccount personalUnlockAccount = web3jAdmin.personalUnlockAccount(address, password).sendAsync().get();
//        if (personalUnlockAccount.accountUnlocked()) {
//            transfer.sendFunds(toAddress, BigDecimal.valueOf(ethBalance), Convert.Unit.ETHER);
//        }
//    }

//    public void getJsonFileByAddresee(String address) {
//        for(File file : )
//    }

    public String transactionErc20Token(String contractAddress, Credentials credentials, String toAddress, double amount) throws IOException {

        //通过load智能合约调用transfer函数；
        //load(String BINARY, String contractAddress, Web3j web3j, Credentials credentials) {
        //     Erc20TokenWrapper erc20TokenWrapper = Erc20TokenWrapper.load(binary,contractAddress,web3j,credentials,gas_price, Contract.GAS_LIMIT);
        //    Uint256 amount1 = Uint256(BigInteger.valueOf(amount));
        //  return erc20TokenWrapper.transfer(toAddress, amount1);

        Erc20TokenWrapper contract1 = Erc20TokenWrapper.load(contractAddress, web3j, credentials, gas_price, Contract.GAS_LIMIT);

        Uint8 decimal = contract1.decimals();

        BigInteger amountBigInteger = new BigDecimal(amount).multiply(BigDecimal.TEN.pow(decimal.getValue().intValue())).toBigInteger();

        Function function = new Function(
                "transfer",
                Arrays.<Type>asList(new Address(toAddress),
                        new Uint256(amountBigInteger)),
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);

        // 创建 tx 管理器，并通过 txManager 来发起合约转账
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
        EthSendTransaction transactionResponse = txManager.sendTransaction(
                gas_price, Contract.GAS_LIMIT,
                contractAddress, encodedFunction, BigInteger.ZERO);

        // 获取 TxHash
        return transactionResponse.getTransactionHash();
    }
}


