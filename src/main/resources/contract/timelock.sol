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





  - it MUST ensure that if participants use the latest valid balance proofs,
  provided by the official Raiden client, the participants will be able
  to receive correct final balances at the end of the channel lifecycle
  - it MUST ensure that the participants cannot cheat by providing an
  old, valid balance proof of their partner; meaning that their partner MUST
  receive at least the amount of tokens that he would have received if
  the latest valid balance proofs are used.
  - the contract cannot determine if a balance proof is invalid (values
  are not within the constraints enforced by the official Raiden client),
  therefore it cannot ensure correctness. Users MUST use the official
  Raiden clients for signing balance proofs.