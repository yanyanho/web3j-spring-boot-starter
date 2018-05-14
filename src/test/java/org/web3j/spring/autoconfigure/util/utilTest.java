package org.web3j.spring.autoconfigure.util;

import org.junit.Test;
import org.web3j.spring.util.GithubRepoPageProcessor;
import us.codecraft.webmagic.Spider;

public class utilTest {

    @Test
    public void testScramb() {
        //Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://etherscan.io/address/0x81b9ca8922eB001f11727812C4dd0A9256eBf9AE").thread(2).run();
    }
}
