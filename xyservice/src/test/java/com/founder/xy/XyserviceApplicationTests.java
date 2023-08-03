package com.founder.xy;

import com.founder.xy.config.MyConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class XyserviceApplicationTests {

    @Autowired
    private MyConfiguration configuration;

    @Test
    void contextLoads() {
        System.out.println(configuration);
    }

    @Before
    public void before(){

    }

}
