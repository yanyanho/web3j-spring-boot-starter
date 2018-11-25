package org.web3j.spring.autoconfigure.util;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.*;
import org.web3j.spring.util.GithubRepoPageProcessor;
import org.web3j.utils.Numeric;
import sun.plugin2.message.Message;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.security.interfaces.ECKey;

import static org.junit.Assert.assertEquals;
import static org.web3j.crypto.Hash.sha3;

//https://www.programcreek.com/java-api-examples/?code=Aptoide/AppCoins-ethereumj/AppCoins-ethereumj-master/ethereumj/ethereumj-core/src/test/java/org/ethereum/net/rlpx/RLPXTest.java
public class utilTest {

    @Test
    public void testScramb() {
        //Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
       // CmbFabricClientImpl  client = new CmbFabricClientImpl();

        //Spider.create(new GithubRepoPageProcessor()).addUrl("https://etherscan.io/address/0x81b9ca8922eB001f11727812C4dd0A9256eBf9AE").thread(2).run();
    }

//    @Test // find node message
//    public void test4() {
//
//        byte[] id = sha3("+++".getBytes(Charset.forName("UTF-8")));
//        ECKey key = ECKey.fromPrivate(BigInteger.TEN);
//
//        Message findNode = FindNodeMessage.create(id, key);
//        logger.info("{}", findNode);
//
//        byte[] wire = findNode.getPacket();
//        FindNodeMessage findNode2 = (FindNodeMessage) Message.decode(wire);
//        logger.info("{}", findNode2);
//
//        assertEquals(findNode.toString(), findNode2.toString());
//
//        String key2 = findNode2.getKey().toString();
//        assertEquals(key.toString(), key2.toString());
//    }


    @Test
    public void sign() throws IOException, CipherException, SignatureException {

        Credentials credentials =
                WalletUtils.loadCredentials(
                        "hsy19910520",
                        "/Users/ruanyang/Library/Ethereum/testnet/keystore/UTC--2018-01-26T03-49-23.608000000Z--dd46729ee7a43cf328e9927f5429275ac8b904a0.json");
        byte[]  messageBytes = "hello world".getBytes();
        Sign.SignatureData sig =Sign.signMessage(messageBytes, credentials.getEcKeyPair());
        System.out.println(Numeric.toHexString(messageBytes));


        System.out.println(Numeric.toHexString(sig.getR()));
        System.out.println(Numeric.toHexString(sig.getS()));
        System.out.println(sig.getV());
        byte[] result = sha3(messageBytes);

        //signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
       // Bytes32 bytes32 = new Bytes32(result);


        Sign.SignatureData signatureData = Sign.signMessage(
                result, credentials.getEcKeyPair());

        // 0xdf9bbE68C77F57866722C0C74B9be15612d94d29

     //   System.out.println(bytes32.toString());
        String pubKey = Sign.signedMessageToKey(messageBytes, sig).toString(16);
        System.out.println(pubKey);
        System.out.println(credentials.getEcKeyPair().getPublicKey().toString(16));


    }

//String to Bytes32:
    public static Bytes32 stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

    //Bytes32 to String:
   // StringUtils.newStringUsAscii(varTypeBytes32.getValue());
    @Test
    public void testSha256_EmptyString() {
        String expected1 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String result1 = Hex.toHexString(Hash.sha256(new byte[0]));
        assertEquals(expected1, result1);
    }


    @Test
    public void testSha3_Test() {
        String expected2 = "0x4d55361a8f362c8fc244dbd1e4968ca4b96d58e63a0f0c01a2cad1149106568a";
        String result2 = Numeric.toHexString(Hash.sha3("hello world".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testSha256_random() {
        //0xd71c3895b0b1cd4013ab17e804066c6f1cd3c2948711fe18be11ff32d446bc3e
        String expected2 = "0x4d55361a8f362c8fc244dbd1e4968ca4b96d58e63a0f0c01a2cad1149106568a";
        String result2 = Numeric.toHexString(Hash.sha256("8CbaC5e4d803bE2A3A5cd3DbE7174504c6DD0c1C".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void sign_test() {
        //0xd71c3895b0b1cd4013ab17e804066c6f1cd3c2948711fe18be11ff32d446bc3e
        String expected2 = "0x4d55361a8f362c8fc244dbd1e4968ca4b96d58e63a0f0c01a2cad1149106568a";
        String result2 = Numeric.toHexString(Hash.sha256("8CbaC5e4d803bE2A3A5cd3DbE7174504c6DD0c1C".getBytes()));
        assertEquals(expected2, result2);
    }


}
