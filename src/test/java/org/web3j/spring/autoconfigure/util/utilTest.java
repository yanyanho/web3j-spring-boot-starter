package org.web3j.spring.autoconfigure.util;

import org.junit.Test;
import org.web3j.spring.util.GithubRepoPageProcessor;
import sun.plugin2.message.Message;
import us.codecraft.webmagic.Spider;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.interfaces.ECKey;

import static org.web3j.crypto.Hash.sha3;

//https://www.programcreek.com/java-api-examples/?code=Aptoide/AppCoins-ethereumj/AppCoins-ethereumj-master/ethereumj/ethereumj-core/src/test/java/org/ethereum/net/rlpx/RLPXTest.java
public class utilTest {

    @Test
    public void testScramb() {
        //Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://etherscan.io/address/0x81b9ca8922eB001f11727812C4dd0A9256eBf9AE").thread(2).run();
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
}
