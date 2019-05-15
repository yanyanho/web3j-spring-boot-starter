package org.web3j.spring.autoconfigure.context;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

import static org.mockito.Mockito.mock;
import static org.web3j.crypto.Hash.sha3;
import static org.web3j.crypto.Sign.signedMessageToKey;

@SpringBootApplication
public class SpringApplicationTest {
    @Bean
    @Primary
    public Web3j nameService() {
        return mock(Web3j.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void hash() {

        byte[] bytes = Numeric.hexStringToByteArray("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9");
        byte[] result = sha3(bytes);
        System.out.println(Numeric.toHexString(result));

        ECKeyPair  ecKeyPair=   ECKeyPair.create(BigInteger.ONE);

        System.out.println(ecKeyPair.getPrivateKey());

        System.out.println(ecKeyPair.getPublicKey());
        Credentials c = Credentials.create(ecKeyPair);
        System.out.println(c.getAddress());
        System.out.println(c.getEcKeyPair().getPrivateKey());
        System.out.println(c.getEcKeyPair().getPublicKey());
       // 0xf7ac9c2e ,0x0dcd2f752394c41875e259e00bb44fd505297caf,0x7e5f4552091a69125d5dfcb7b8c2659029395bdf,500,5,1


                //  request
        //  "0x0dcd2f752394c41875e259e00bb44fd505297caf","0x7e5f4552091a69125d5dfcb7b8c2659029395bdf","0x14723a09acff6d2a60dcdf7aa4aff308fddc160c",500,5,1

        //hash   0x863cef4558aa072f57fb664ad78700ee53d500d7f669f1c616b04e59716e23d1

        byte[] b = Hash.sha3("hello world".getBytes());
        System.out.println(bytesToHex(b));

        Sign.SignatureData sigData = Sign.signMessage("hello world".getBytes(),
                ECKeyPair.create(BigInteger.ONE));


                byte v = sigData.getV();

        String hexR = bytesToHex(sigData.getR());
        String hexS = bytesToHex(sigData.getS());

        byte[] rBytes = hexStringToBytes(hexR);
        byte[] sBytes = hexStringToBytes(hexS);

        BigInteger r = new BigInteger(rBytes);
        BigInteger s = new BigInteger(sBytes);

        System.out.println("Signature v value: " + v);
        System.out.println("Signature r value: 0x" + hexR);
        System.out.println("Signature s value: 0x" + hexS);

        String hexString = signatureDataToString(sigData);
        System.out.println(hexString);
        //6376237641841063197511976314280597438149211753541909917794154839912978489307502705320406087313832827571435203289817719151821525032884964596839752540411064
    }


    private static String bytesToHex(byte[] bytes)
    {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        String finalHex = new String(hexChars);
        return finalHex;
    }

    private static byte[] hexStringToBytes(String s)
    {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    static public Sign.SignatureData stringToSignatureData(String signatureData)
    {
        byte[] byte_3 = Numeric.hexStringToByteArray(signatureData);
        byte[] signR = new byte[32];
        System.arraycopy(byte_3, 1, signR, 0, signR.length);
        byte[] signS = new byte[32];
        System.arraycopy(byte_3, 1+signR.length, signS, 0, signS.length);
        return  new Sign.SignatureData(byte_3[0],signR,signS);
    }

    static public  String  signatureDataToString(Sign.SignatureData signatureData)
    {
        byte[] byte_3 = new byte[1+signatureData.getR().length+signatureData.getS().length];

        System.arraycopy(signatureData.getR(), 0, byte_3, 0, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte_3, signatureData.getR().length, signatureData.getS().length);
        byte_3[signatureData.getR().length+signatureData.getS().length] = signatureData.getV();
        return  Numeric.toHexString(byte_3,0,byte_3.length,false);
    }

    @Test
    public void testHash() throws SignatureException {
        byte[] hash = Hash.sha3("hsy".getBytes());
        String hash1 = bytesToHex(hash);

        System.out.println(hash1);

        String message = "\\x19Ethereum Signed Message:\n" + hash1.length() + hash1;

        byte[] data = message.getBytes();
        System.out.println("0x" + bytesToHex(Hash.sha3(data)));
        ECKeyPair ecKeyPair = ECKeyPair.create(BigInteger.ONE);
        Credentials c = Credentials.create(ecKeyPair);
        System.out.println("address " +  c.getAddress());
        Sign.SignatureData sigData = Sign.signMessage("hsy".getBytes(), ECKeyPair.create(BigInteger.ONE));


        System.out.println("Signature v value: " + sigData.getV());
        System.out.println("Signature r value: 0x" + bytesToHex(sigData.getR()));
        System.out.println("Signature s value: 0x" + bytesToHex(sigData.getS()));

        System.out.println( "Signature: 0x" + signatureDataToString(sigData));
        System.out.println( "address " + "0x" + Keys.getAddress(signedMessageToKey("hsy".getBytes(),sigData))
        );

    }


}

