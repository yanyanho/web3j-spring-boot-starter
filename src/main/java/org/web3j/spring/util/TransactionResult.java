package org.web3j.spring.util;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;


public class TransactionResult {
    String status;
    String message;
    List<TransactionReceiptWithMore> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransactionReceiptWithMore> getResult() {
        return result;
    }

    public void setResult(List<TransactionReceiptWithMore> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
