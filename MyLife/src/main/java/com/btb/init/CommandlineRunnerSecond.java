package com.btb.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(3)
public class CommandlineRunnerSecond implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("This is {} Command Line Runner", "second");
    }
}