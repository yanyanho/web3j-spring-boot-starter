// tinkey  0x96b36df8792cfbc0f79271d84e65c2d15f11527e

contract TinkeyToken is StandardToken,Pausable{
    string public name = 'Tinkey Token';
    string public symbol = 'TKY';
    uint public decimals = 18;
    uint public INITIAL_SUPPLY = 10000000000 *10**decimals;

    function TinkeyToken() {
        totalSupply_ = INITIAL_SUPPLY;
        balances[msg.sender] = INITIAL_SUPPLY;
    }

     function () {
        //if ether is sent to this address, send it back.
        revert();
    }
}
