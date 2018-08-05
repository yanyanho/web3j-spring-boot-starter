package org.web3j.spring.autoconfigure.wallet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
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
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.spring.autoconfigure.ApiTestBase;
import org.web3j.spring.token.Erc20TokenWrapper;
import org.web3j.spring.util.TransactionReceiptWithMore;
import org.web3j.spring.util.TransactionResult;
import org.web3j.spring.wallet.FileContent;
import org.web3j.spring.wallet.Web3jService;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.web3j.crypto.Hash.sha3;
import static org.web3j.spring.wallet.Web3jService.gas_price;



public class WalletTest extends ApiTestBase {

    //    @Autowired
//    RestTemplate restTemplate;
    @Autowired
    Web3jService web3jService;
    @Autowired
    Web3j web3j;

    @Test
    public void transferTest() {
        RestTemplate restTemplate = new RestTemplate();
//https://api.etherscan.io/api?module=account&action=tokenbalance&contractaddress=0x57d90b64a1a57749b0f932f1a3395792e12e7055&address=0xe04f27eb70e025b78871a2ad7eabe85e61212761&tag=latest&apikey=YourApiKeyToken
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://api.etherscan.io/api?module=contract&action=getabi&address=0xcb97e65f07da24d46bcdd078ebebd7c6e6e3d750&apikey=YourApiKeyToken", String.class);
        // ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://etherscan.io/address/0xb5a05cdddc65516463674c88fb0cc91d9f62342a", String.class);
        //ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.etherscan.io/api?module=stats&action=tokensupply&contractaddress=0x57d90b64a1a57749b0f932f1a3395792e12e7055&apikey=YourApiKeyToken", String.class);
        System.out.println(responseEntity.getBody());

    }


//    @Test
//    public void testGetTransactionListByAddress() {
//        RestTemplate restTemplate = new RestTemplate();
//        ParameterizedTypeReference<List<TransactionReceipt>> typeRef = new ParameterizedTypeReference<List<TransactionReceipt>>() {
//        };
//        ResponseEntity<TransactionResult> responseEntity = restTemplate.getForEntity("http://api.etherscan.io/api?module=account&action=txlist&address=0x797EBd22372f3941d16D51fE98e840BFfd20FDB9&sort=asc", TransactionResult.class);
//        // ResponseEntity<TransactionResult> responseEntity = restTemplate.exchange("http://api.etherscan.io/api?module=account&action=txlist&address=0x797EBd22372f3941d16D51fE98e840BFfd20FDB9&sort=asc", HttpMethod.GET, null,typeRef);
//
//        List<TransactionReceiptWithMore> result = web3jService.getTransactionLogByAddress("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9");
//        System.out.println(responseEntity.getBody().getResult());
//        System.out.println(result.get(0));
//
//    }


    @Ignore
    @Test
    public void erc20TransferTest() throws IOException, CipherException, TransactionException {


        //String contractAddress = "0xD8002cD05d5B2a85557e1CAAa179cC2408D5ad42";
        String contractAddress = "0x29B17f2D0af2357f0Adc00f83EC595d7bc2C8285";


        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/A6YAM6J99HuuW8LafBEv"));


        Credentials credentials = WalletUtils.loadCredentials("hsy19910520",
                "/Users/ruanyang/Library/Ethereum/testnet/keystore/UTC--2018-01-26T03-49-23.608000000Z--dd46729ee7a43cf328e9927f5429275ac8b904a0.json");


        //直接调用智能合约！！
        Erc20TokenWrapper contract1 = Erc20TokenWrapper.load(contractAddress, web3j, credentials, gas_price, Contract.GAS_LIMIT);

        Uint8 decimal = contract1.decimals();
        contract1.transfer(new Address("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9"), new Uint256(BigInteger.valueOf(1000000000)));

        BigInteger amountBigInteger = new BigDecimal(200).multiply(BigDecimal.TEN.pow(decimal.getValue().intValue())).toBigInteger();

        Function function = new Function(
                "transfer",
                Arrays.<Type>asList(new Address("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9"),
                        new Uint256(amountBigInteger)),
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);


        // 创建 tx 管理器，并通过 txManager 来发起合约转账
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
        EthSendTransaction transactionResponse = txManager.sendTransaction(
                gas_price, Contract.GAS_LIMIT,
                contractAddress, encodedFunction, BigInteger.ZERO);

        // 获取 TxHash
        System.out.println(transactionResponse.getTransactionHash());
    }

    @Test
    public void testBalance() {
        BigInteger b = web3jService.getBalance("0xAb72506B1cB942180B4EB2398d7B7Fa79073e38C");
        assertTrue(b.intValue() > 0);
    }

    @Test
    public void testCreateNewWallet() throws Exception {
        InputStream inputStream = new ClassPathResource("wallet/" + "UTC--2018-05-10T06-35-26.535000000Z--b6ac716329c5995e9a64899cd44ca5900bd98530.json").getInputStream();
        FileContent file = web3jService.newWallet("hsy123456XXX"); // XXX
        assertNotNull(file);
        assertNotNull(file.getFileName());
    }

    @Test
    public void testClassPathResource() throws Exception {
        File file = new ClassPathResource("wallet/" + "UTC--2018-05-10T06-35-26.535000000Z--b6ac716329c5995e9a64899cd44ca5900bd98530.json").getFile();
        assertNotNull(file);
    }

    @Test
    public void testLoadWallet() throws Exception {
        File dir = new File(new ClassPathResource("wallet5").getPath());
        dir.mkdir();
        File origin = new ClassPathResource("wallet2/" + "UTC--2018-05-10T06-35-26.535000000Z--b6ac716329c5995e9a64899cd44ca5900bd98530.json").getFile();
        MultipartFile jsonFile = new MockMultipartFile("1.json", "1.json", null, new FileInputStream(origin));
        // File convFile =  new File( dir.getPath()+jsonFile.getOriginalFilename());
        // File convFile =   new ClassPathResource("wallet/"+ jsonFile.getOriginalFilename()).getFile();
        //jsonFile.transferTo(convFile);
        Credentials credentials = web3jService.loadCredentialsByJsonFile("hsy19910520", jsonFile);
        assertEquals(credentials.getAddress(), "0xb6ac716329c5995e9a64899cd44ca5900bd98530");

    }

    @Test
    public void testTransfer() throws Exception {
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "hsy19910520",
                        "/Users/ruanyang/Library/Ethereum/testnet/keystore/UTC--2018-01-26T03-49-23.608000000Z--dd46729ee7a43cf328e9927f5429275ac8b904a0.json");
        System.out.println(credentials.getEcKeyPair().getPrivateKey());
        String pr = "57043026691738733056252641900788096178513846282170550194593777662393432978057";
        String pr1 = "8c5af3d623dad6786dbe24d17d8047a168dd0ed1033035ef6784fe78a69af20d";

        String pkhex = new BigInteger(pr, 10).toString(16);
        String pk1hex = new BigInteger(pr1, 16).toString(16);
        System.out.println(pk1hex.length());
        String add = Credentials.create(pkhex).getAddress();
        String add1 = Credentials.create(pk1hex).getAddress();
        System.out.println(add); //0x4fdb0369cbaf8fb9282f228eb94e6c44314ee3d0
        System.out.println(add1);

        TransactionReceipt transactionReceipt = web3jService.transaction("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9", 0.00001, Credentials.create("57043026691738733056252641900788096178513846282170550194593777662393432978057"));
        assertNotNull(transactionReceipt);
    }

    //对数据进行RLP 压缩，然后hash
    @Test
    public void testTransferWord() throws IOException, CipherException {

        Credentials credentials =
                WalletUtils.loadCredentials(
                        "hsy19910520",
                        "/Users/ruanyang/Library/Ethereum/testnet/keystore/UTC--2018-01-26T03-49-23.608000000Z--dd46729ee7a43cf328e9927f5429275ac8b904a0.json");
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
        EthSendTransaction transactionResponse = txManager.sendTransaction(
                gas_price, Contract.GAS_LIMIT, "0x797EBd22372f3941d16D51fE98e840BFfd20FDB9", Numeric.toHexString("何硕彦的第一条去中心化微博！！！ ".getBytes("UTF-8")), BigInteger.ZERO);


        // 获取 TxHash
        //System.out.println(transactionResponse.getTransactionHash());
    }

    @Test
    public void hash() {

            byte[] bytes = Numeric.hexStringToByteArray("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9");
            byte[] result = sha3(bytes);
             System.out.println(Numeric.toHexString(result));
        }
}