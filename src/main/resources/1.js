//初始化基本对象var
 Web3 = require('web3');

 var web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"));
 var version = web3.version.ethereum;
 var account = web3.eth.accounts[0];
 var account1 = web3.eth.accounts.privateKeyToAccount("0x76e0304a9aedee8bc2a749935a25a45b64b9c996b966c0e9ba6af17e38ad1697");
 var sha3Msg = web3.utils.sha3("abc");
 //var signedData = web3.eth.sign(account, sha3Msg);
 console.log("account: " + account);
 console.log("account1: " + account1);
 console.log("sha3(message): " + sha3Msg);
 //console.log("Signed data: " + signedData);