package org.web3j.spring.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;


import java.math.BigDecimal;
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

    @RequestMapping(value="/transfer", method = RequestMethod.GET)
    TransactionReceipt transfer(@RequestParam  String address , @RequestParam double value, @RequestParam Credentials credentials) throws Exception {
        return   web3jService.transaction(address, value,  credentials);
    }


//    @RequestMapping(value="/transfer/password", method = RequestMethod.GET)
//    void transfer(@RequestParam  String address , @RequestParam  String password , @RequestParam  String toAddress , @RequestParam double value) throws Exception {
//           web3jService.unlockWallet(address, password, toAddress,  value);
//    }






}
