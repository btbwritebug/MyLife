package com.btb.init;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class CommandlineRunnerFirst implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("CommandLineRunner Args:{}", JSON.toJSONString(args));
        log.info("This is {} Command Line Runner", "first");
    }
}
