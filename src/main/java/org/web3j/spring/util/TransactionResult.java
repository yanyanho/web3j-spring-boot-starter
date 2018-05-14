package org.web3j.spring.util;

import lombok.Data;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

@Data
public class TransactionResult {
    String status;
    String message;
    List<TransactionReceiptWithMore> result;
}
