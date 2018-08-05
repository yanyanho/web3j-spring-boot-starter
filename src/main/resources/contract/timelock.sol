 pragma solidity ^0.4.19;
 // address 0x4F3c2e6A20c3921192499Bb5eE286B5E8dF8b0Aa
 contract timelock {

      uint public freezeBlocks = 50;       //number of blocks to keep a lockers (5)


   struct locker{
      uint freedom;
      uint bal;
    }
    mapping (address => locker) public lockers;

    event Locked(address indexed locker, uint indexed amount);
    event Released(address indexed locker, uint indexed amount);


/* public functions */
    function() payable public {
        locker storage l = lockers[msg.sender];
        l.freedom =  block.number + freezeBlocks; //this will reset the freedom clock
        l.bal = l.bal + msg.value;

        Locked(msg.sender, msg.value);
    }

    function withdraw() public {
        locker storage l = lockers[msg.sender];
        require (block.number > l.freedom && l.bal > 0);

        // avoid recursive call

        uint value = l.bal;
        l.bal = 0;
        msg.sender.transfer(value);
        Released(msg.sender, value);
    }
 }