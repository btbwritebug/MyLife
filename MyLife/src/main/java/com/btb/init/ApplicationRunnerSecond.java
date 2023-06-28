package com.btb.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(2)
public class ApplicationRunnerSecond implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {} Application Runner", "second");
    }
}
