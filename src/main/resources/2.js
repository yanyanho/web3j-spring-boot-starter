var Accounts = require('web3-eth-accounts');
var Web3 = require('web3')
var web3 = new Web3();
var accounts = new Accounts();

var acc = accounts.privateKeyToAccount(privateKey1);
console.log(acc);
var sig = acc.sign('TEST');
var add = web3.eth.accounts.recover('TEST','0x9aae2a66c2c4b3d12f36ce26b59024b6deadcb8ac51f4bd14188feaec15b6c2f53037c38b48243e2bb852d7f857ccc2c24c1bb1c1842bb30f0bea555c995a6261c');
//var add = acc.recover('TEST','0x9aae2a66c2c4b3d12f36ce26b59024b6deadcb8ac51f4bd14188feaec15b6c2f53037c38b48243e2bb852d7f857ccc2c24c1bb1c1842bb30f0bea555c995a6261c');
console.log("***" + add);
console.log(sig);
console.log(sig.r);
console.log(sig.s);
console.log(sig.v);