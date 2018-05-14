package org.web3j.spring.util;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.response.TransactionReceiptProcessor;

public class TransactionReceiptWithMore  extends TransactionReceipt {
    String hash;
    String value;
    String confirmations;
    String nonce;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
