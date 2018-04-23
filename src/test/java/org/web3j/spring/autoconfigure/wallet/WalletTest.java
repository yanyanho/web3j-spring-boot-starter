package org.web3j.spring.autoconfigure.wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
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
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.spring.token.Erc20TokenWrapper;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import static org.web3j.spring.wallet.Web3jService.gas_price;

@SpringBootApplication
@RunWith(SpringRunner.class)
public class WalletTest {

//    @Autowired
//    RestTemplate restTemplate;


    @Test
     public  void transferTest() {
        RestTemplate restTemplate= new RestTemplate();

       // ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://api.etherscan.io/api?module=contract&action=getabi&address=0xcb97e65f07da24d46bcdd078ebebd7c6e6e3d750&apikey=2QJHBURFK52GJSFSNHCJA37P6I9JC14YPK", String.class);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://etherscan.io/address/0xb5a05cdddc65516463674c88fb0cc91d9f62342a", String.class);
       // ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://etherscan.io/address/0xf8a06ef897755bbd49707d8a8ce0ca9e6a0f0c34&apikey=2QJHBURFK52GJSFSNHCJA37P6I9JC14YPK", String.class);
         System.out.println(responseEntity.getBody());;

    }

    @Test
    public  void erc20TransferTest() throws IOException, CipherException, TransactionException {


        //String contractAddress = "0xD8002cD05d5B2a85557e1CAAa179cC2408D5ad42";
        String contractAddress = "0x29B17f2D0af2357f0Adc00f83EC595d7bc2C8285";



        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/A6YAM6J99HuuW8LafBEv"));


        Credentials credentials = WalletUtils.loadCredentials("hsy19910520",
                "/Users/ruanyang/Library/Ethereum/testnet/keystore/UTC--2018-01-26T03-49-23.608000000Z--dd46729ee7a43cf328e9927f5429275ac8b904a0.json");


       //直接调用智能合约！！
        Erc20TokenWrapper contract1 = Erc20TokenWrapper.load(contractAddress, web3j, credentials, gas_price, Contract.GAS_LIMIT);

        Uint8 decimal = contract1.decimals();
        contract1.transfer(new Address("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9"),new Uint256(BigInteger.valueOf(1000000000)));

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
        System.out.println( transactionResponse.getTransactionHash());
    }

}
