package org.aston.greenteam.gateway.converter;

import org.aston.greenteam.gateway.client.BaseClient;
import org.springframework.web.client.RestTemplate;

public class ConverterClient extends BaseClient {
    public ConverterClient(RestTemplate rest) {
        super(rest);
    }
}
