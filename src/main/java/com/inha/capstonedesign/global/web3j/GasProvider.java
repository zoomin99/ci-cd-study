package com.inha.capstonedesign.global.web3j;

import org.springframework.stereotype.Component;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

@Component
public class GasProvider implements ContractGasProvider {

    private final GasProperties gasProperties;

    private final BigInteger gasPrice;
    private final BigInteger gasLimit;

    public GasProvider(GasProperties gasProperties) {
        this.gasProperties = gasProperties;
        this.gasPrice = new BigInteger(gasProperties.getGasPrice());
        this.gasLimit = new BigInteger(gasProperties.getGasLimit());
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return gasPrice;
    }

    @Override
    public BigInteger getGasPrice() {
        return gasPrice;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return gasLimit;
    }

    @Override
    public BigInteger getGasLimit() {
        return gasLimit;
    }
}
