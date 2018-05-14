package org.web3j.spring.util;

import lombok.Data;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.response.TransactionReceiptProcessor;

@Data
public class TransactionReceiptWithMore  extends TransactionReceipt {
    String hash;
    String value;
    String confirmations;
    String nonce;

}
