contract ETFToken is StandardToken,Pausable{
    string public name = 'ETF Token';
    string public symbol = 'ETF';
    uint public decimals = 18;
    uint public INITIAL_SUPPLY = 1000000000 *10**decimals;

    function ETFToken() {
        totalSupply_ = INITIAL_SUPPLY;
        balances[msg.sender] = INITIAL_SUPPLY;
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