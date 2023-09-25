package com.inha.capstonedesign.global.web3j;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "web3j")
@ConstructorBinding
public class Web3jProperties {

    // Infura RPC 엔드포인트 URL
    private final String rpcEndpointUrl;

    //내 지갑의 privateKey
    private final String walletPrivateKey;

    //배포된 smart contract의 address
    private final String contractAddress;

    //내 지갑의 address
    private final String walletAddress;
}
