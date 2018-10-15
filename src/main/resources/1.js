//初始化基本对象var
 Web3 = require('web3');

 var web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"));
 var version = web3.version.ethereum;
 var account = web3.eth.accounts[0];
 var sha3Msg = web3.utils.sha3("abc");
 //var signedData = web3.eth.sign(account, sha3Msg);
 console.log("account: " + account);
 console.log("account1: " + account1);
 console.log("sha3(message): " + sha3Msg);
 //console.log("Signed data: " + signedData);