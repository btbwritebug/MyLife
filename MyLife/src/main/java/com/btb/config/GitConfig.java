package com.btb.config;


import org.eclipse.jgit.transport.SshSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitConfig {
    @Bean("sshSessionFactory")
    public SshSessionFactory sshSessionFactory() {
        return new CustomSshSessionFactory();
    }
}
