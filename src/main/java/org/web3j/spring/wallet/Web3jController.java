package org.web3j.spring.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class Web3jController {
    @Autowired
    Web3jService web3jService;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    String getVersion() {
        return web3jService.getClientVersion();
    }

    @RequestMapping(value = "/new-wallet/{password}", method = RequestMethod.GET)
    String getNewWallert(@PathVariable String password) throws Exception {
        return web3jService.newWallet(password);
    }

    @RequestMapping(value ="/{address}/balance", method = RequestMethod.GET)
    BigInteger getBalance(@PathVariable String address) {
        return web3jService.getBalance(address);
    }



}
