pragma solidity ^0.4.17;

import "./Utils.sol";
import "./Token.sol";
import "./TokenNetwork.sol";


// SecretRegisry
 //  0xb370c538b80e5f7d0641d4497fbce125772e8613

//0x598a3898bd2764e649a7acc3e59d37dc794bfc0f
   //TokenNetworksRegistry  0x598a3898bd2764e649a7acc3e59d37dc794bfc0f

   //TokenNetwork
   //0xAa64E21C9e3f4fa335125e33D8FDD0b556a26369
contract TokenNetworksRegistry is Utils {

    /*
     *  Data structures
     */

    string constant public contract_version = "0.3._";
    address public secret_registry_address;
    uint256 public chain_id;

    // Token address => TokenNetwork address
    mapping(address => address) public token_to_token_networks;

    /*
     *  Events
     */

    event TokenNetworkCreated(address token_address, address token_network_address);

    /*
     *  Constructor
     */

    function TokenNetworksRegistry(address _secret_registry_address, uint256 _chain_id) public {
        require(_chain_id > 0);
        require(_secret_registry_address != 0x0);
        require(contractExists(_secret_registry_address));
        secret_registry_address = _secret_registry_address;
        chain_id = _chain_id;
    }

    /*
     *  External Functions
     */

    function createERC20TokenNetwork(address _token_address)
        external
        returns (address token_network_address)
    {
        require(token_to_token_networks[_token_address] == 0x0);

        // Token contract checks are in the corresponding TokenNetwork contract

        token_network_address = new TokenNetwork(
            _token_address,
            secret_registry_address,
            chain_id
        );

        token_to_token_networks[_token_address] = token_network_address;
        TokenNetworkCreated(_token_address, token_network_address);

        return token_network_address;
    }
}
