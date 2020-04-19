package com.gamesys.hatef;

import com.gamesys.hatef.controller.CandleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SpringForexApiApplicationTests {
    
    SpringForexApiApplication application = new SpringForexApiApplication();
    
    @Autowired
    CandleController candleController;
    
    @Test
    void shouldCreateCacheManagerOnStartup() {
        CacheManager result = application.cacheManager();
        Assertions.assertNotNull(result);
    }
    
    @Test
    void shouldLoadCandleControllerOnContextInitialization() {
        assertThat(candleController).isNotNull();
    }
}
