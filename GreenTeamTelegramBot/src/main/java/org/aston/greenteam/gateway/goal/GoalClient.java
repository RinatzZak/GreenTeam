package org.aston.greenteam.gateway.goal;

import org.aston.greenteam.gateway.client.BaseClient;
import org.springframework.web.client.RestTemplate;

public class GoalClient extends BaseClient {
    public GoalClient(RestTemplate rest) {
        super(rest);
    }
}
