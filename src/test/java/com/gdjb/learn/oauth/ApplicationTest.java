package com.gdjb.learn.oauth;

import com.gdjb.oauth.config.properties.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationProperties applicationProperties;

    @Test
    public void contextLoads() {
    }

    @Test
    public void method() {
        LOGGER.info("clientId:{}", applicationProperties.getApi().getClientId());
        LOGGER.info("JwtSigningKey:{}", applicationProperties.getSecurity().getOauth2().getJwtSigningKey());
    }
}
