package org.web3j.spring.autoconfigure.util;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.web3j.crypto.Hash.sha3;

import java.math.BigInteger;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


//https://www.programcreek.com/java-api-examples/?code=Aptoide/AppCoins-ethereumj/AppCoins-ethereumj-master/ethereumj/ethereumj-core/src/test/java/org/ethereum/net/rlpx/RLPXTest.java
public class utilTest {

    @Test
    public void testScramb() {
        //Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
      //  CmbFabricClientImpl  client = new CmbFabricClientImpl();

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


        System.out.println("R" +    Numeric.toHexString(sig.getR()));
        System.out.println("S" +Numeric.toHexString(sig.getS()));
        System.out.println("V" +sig.getV());

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

    @Test
    public void sign1() throws IOException, CipherException, SignatureException {

        String privateKey1 = "fba4137335dc20dc23ad3dcd9f4ad728370b09131a6a14abf6c839748700780d";
        Credentials credentials = Credentials.create(privateKey1);
        System.out.println("Address: " + credentials.getAddress());
        byte[] data = "TEST".getBytes();
        System.out.println("Sha3: " + Numeric.toHexString(Hash.sha3(data)));
        System.out.println("Data: " + Numeric.toHexString(data));
        Sign.SignatureData signature = Sign.signMessage(data, credentials.getEcKeyPair());

        System.out.println("R: " + Numeric.toHexString(signature.getR()));
        System.out.println("S: " + Numeric.toHexString(signature.getS()));
        System.out.println("V: " + Integer.toString(signature.getV()));


    }

    @Test
    public void sign1111() throws IOException, CipherException, SignatureException {

        //Address: 0x807ca048d473c3f706397a30756a552b11f62925
        //Sha3: 0xb64dfaace7b0d5207e4449cefc4130f682cbe54c00b40a3e36ee353661f5fcec
        //Data: 0x5c783139457468657265756d205369676e6564204d6573736167653a0a345b42403166353534623036
        //R: 0xbb257a03614aed1ce898753708b7c87486d02d03b31dec5f278b2adcf92d835a
        //S: 0x66ef623e8436af8566285862825beaef58ad851a9b680ffbe0d1bb3549492100
        //V: 28
        String privateKey1 = "0x7e1d357e9696ef7d1fdb3aebc17d265bb1bd8bc9223a2ce38667f80d85a4b289";
        Credentials credentials = Credentials.create(privateKey1);
        System.out.println("Address: " + credentials.getAddress());
        byte[] hash = "TEST".getBytes();

        String message = "\u0019Ethereum Signed Message:\n" + hash.length + hash;
        //String preamble = "\u0019Ethereum Signed Message:\n" + hash.length() + hash;

        byte[] data = message.getBytes();
        System.out.println("hash: " + Numeric.toHexString(Hash.sha3(hash)));
        System.out.println("Sha3: " + Numeric.toHexString(Hash.sha3(data)));
        System.out.println("Data: " + Numeric.toHexString(data));
        Sign.SignatureData signature = Sign.signMessage(data, credentials.getEcKeyPair());

        System.out.println("R: " + Numeric.toHexString(signature.getR()));
        System.out.println("S: " + Numeric.toHexString(signature.getS()));
        System.out.println("V: " + Integer.toString(signature.getV()));


    }
    //0x5d46da45a1af583990eeb142199e593480d0b1e5f88903e287ff67209140dcc646384d275b500ded85ca16dea5ff369532a27fc92721189cef68d652ab18580a1c
    @Test
    public void sign2() throws IOException, CipherException, SignatureException {

        String privateKey1 = "0x7e1d357e9696ef7d1fdb3aebc17d265bb1bd8bc9223a2ce38667f80d85a4b289";
        Credentials credentials = Credentials.create(privateKey1);
        System.out.println("Address: " + credentials.getAddress());

        String hash = Hex.toHexString(Hash.sha3("TEST".getBytes()));
        //0xfb96181ff706848b10a93f4028537caf17026e28ce5c0cce90af46b4d3ad04c6
        System.out.println("Hash" + hash);

        String message = "\\x19Ethereum Signed Message:\n" + hash.length() + hash;
        //String preamble = "\u0019Ethereum Signed Message:\n" + hash.length() + hash;

        byte[] data = message.getBytes();

        Sign.SignatureData signature = Sign.signMessage(data, credentials.getEcKeyPair());

        System.out.println("R: " + Numeric.toHexString(signature.getR()));
        System.out.println("S: " + Numeric.toHexString(signature.getS()));
        System.out.println("V: " + Integer.toString(signature.getV()));


    }

    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

//    @Test
//    public void testRecoverAddressFromSignature() {
//        //CHECKSTYLE:OFF
//        String signature = "0x2c6401216c9031b9a6fb8cbfccab4fcec6c951cdf40e2320108d1856eb532250576865fbcd452bcdc4c57321b619ed7a9cfd38bd973c3e1e0243ac2777fe9d5b1b";
//        //CHECKSTYLE:ON
//        String address = "0x31b26e43651e9371c88af3d36c14cfd938baf4fd";
//        String message = "v0G9u7huK4mJb2K1";
//
//        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
//        byte[] msgHash = Hash.sha3((prefix + message).getBytes());
//
//        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
//        byte v = signatureBytes[64];
//        if (v < 27) {
//            v += 27;
//        }
//
//        Sign.SignatureData sd = new Sign.SignatureData(
//                v,
//                (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
//                (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));
//
//        String addressRecovered = null;
//        boolean match = false;
//
//        // Iterate for each possible key to recover
//        for (int i = 0; i < 4; i++) {
//            BigInteger publicKey = Sign.recoverFromSignature(
//                    (byte) i,
//                    new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
//                    msgHash);
//
//            if (publicKey != null) {
//                addressRecovered = "0x" + Keys.getAddress(publicKey);
//
//                if (addressRecovered.equals(address)) {
//                    match = true;
//                    break;
//                }
//            }
//        }
//
//        //assertThat(addressRecovered, address);
//        assertTrue(match);
//    }



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
    public void testSha3_Helloworld() {
        //0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad
        String expected2 = "0x4d55361a8f362c8fc244dbd1e4968ca4b96d58e63a0f0c01a2cad1149106568a";
        String result2 = Numeric.toHexString(Hash.sha3("hello world".getBytes()));
        assertEquals(expected2, result2);
    }

    //0xd9eba16ed0ecae432b71fe008c98cc872bb4cc214d3220a36f365326cf807d68

    @Test
    public void testSha3_Helloworldwith() {
        //0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad
        String expected2 = "0x4d55361a8f362c8fc244dbd1e4968ca4b96d58e63a0f0c01a2cad1149106568a";
        String result2 = Numeric.toHexString(Hash.sha3("hello world".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testSha3_Test_test() {
        String expected2 = "9c22ff5f21f0b81b113e63f7db6da94fedef11b2119b4088b89664fb9a3cb658";
        String result2 = Hex.toHexString(Hash.sha3("test".getBytes()));
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

    //Hex format : 21a57f2fe765e1ae4a8bf15d73fc1bf2a533f547f2343d12a499d9c0592044d4
    @Test
    public void sha() throws NoSuchAlgorithmException, IOException {
     MessageDigest md = MessageDigest.getInstance("SHA-256");
     FileInputStream fis = new FileInputStream("c:\\loging.log");

     byte[] dataBytes = new byte[1024];

     int nread = 0;
     while ((nread = fis.read(dataBytes)) != -1) {
         md.update(dataBytes, 0, nread);
     };
     byte[] mdbytes = md.digest();

     //convert the byte to hex format method 1
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < mdbytes.length; i++) {
         sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
     }

     System.out.println("Hex format : " + sb.toString());

     //convert the byte to hex format method 2
     StringBuffer hexString = new StringBuffer();
     for (int i=0;i<mdbytes.length;i++) {
         hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
     }

     System.out.println("Hex format : " + hexString.toString());
 }

//Hex format : 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
    @Test
    public void sha1() throws NoSuchAlgorithmException, IOException {
        String password = "123456";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hex format : " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println("Hex format : " + hexString.toString());
    }



}
