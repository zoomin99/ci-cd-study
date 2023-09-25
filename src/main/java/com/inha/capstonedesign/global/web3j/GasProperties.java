package com.inha.capstonedesign.global.web3j;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "gas")
@ConstructorBinding
public class GasProperties {
    private final String gasPrice;
    private final String gasLimit;
}
