package org.web3j.spring.autoconfigure.context;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Numeric;

import static org.mockito.Mockito.mock;
import static org.web3j.crypto.Hash.sha3;

@SpringBootApplication
public class SpringApplicationTest {
    @Bean
    @Primary
    public Web3j nameService() {
        return mock(Web3j.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void hash() {

        byte[] bytes = Numeric.hexStringToByteArray("0x797EBd22372f3941d16D51fE98e840BFfd20FDB9");
        byte[] result = sha3(bytes);
        System.out.println(Numeric.toHexString(result));




    }


}

