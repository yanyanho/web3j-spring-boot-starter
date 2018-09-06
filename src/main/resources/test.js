
var Web3 = require('web3');
var web3 = new Web3(new Web3.providers.HttpProvider("https://ropsten.infura.io/A6YAM6J99HuuW8LafBEv"));
var version = web3.version.ethereum;
console.log(version); // 60

var hash = web3.utils.sha3("hello world");
console.log(hash); // "0xed973b234cf2238052c9ac87072c71bcf33abc1bbd721018e0cca448ef79b379"

var hash1 = web3.utils.soliditySha3('hello world');
console.log(hash1);

var hashOfHash = web3.utils.sha3(hash, {encoding: 'hex'});
console.log(hashOfHash); // "0x85dd39c91a64167ba20732b228251e67caed1462d4bcf036af88dc6856d0fdcc"