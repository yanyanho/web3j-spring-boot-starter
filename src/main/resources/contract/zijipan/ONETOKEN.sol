//0xe73acf4d86bcb199fe82bab196febefb3e7c0b2c
pragma solidity ^0.4.4;
import "https://github.com/OpenZeppelin/openzeppelin-solidity/contracts/token/ERC20/ERC20Mintable.sol";
import "https://github.com/OpenZeppelin/openzeppelin-solidity/contracts/token/ERC20/ERC20Pausable.sol";
import "https://github.com/OpenZeppelin/openzeppelin-solidity/contracts/token/ERC20/ERC20Detailed.sol";
import "https://github.com/OpenZeppelin/openzeppelin-solidity/contracts/token/ERC20/ERC20Burnable.sol";

contract ONEToken is ERC20Mintable,ERC20Pausable,ERC20Burnable,ERC20Detailed('ONE TOKEN','ONET',8){

    uint public decimals = 8;
    uint public INITIAL_SUPPLY = 840000000 *10**decimals;

    function ONEToken() {    //xiugai
        //  totalSupply = INITIAL_SUPPLY;
        // balances[msg.sender] = INITIAL_SUPPLY;
        _mint(msg.sender, INITIAL_SUPPLY);
        // ERC20Detailed(name,symbol,decimals);

    }

    function () {
        //if ether is sent to this address, send it back.
        revert();
    }

    uint public numDrops;
    address[] public failDropperInfo;
    event TokenDrop( address receiver);

    function multisend(address[] dests, uint[] values) onlyOwner public
    returns (uint256) {
        uint256 i = 0;
        while (i < dests.length) {

            bool success = transfer(dests[i], values[i]);
            if(success){
                numDrops++;
                TokenDrop(dests[i]);
            } else {
                failDropperInfo.push(dests[i]);
            }
            i += 1;
        }
        return(i);
    }


}