package com.btb.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(4)
public class ApplicationRunnerFirst implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {} Application Runner", "first");
    }
}
