package com.btb.controller;

import com.btb.util.GitUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/form-gitLab")
@Api(value = "Git推送接口", tags = {"Git推送接口"})
@Slf4j
public class GitController {

    @Value("${ssh.privateKey}")
    private String privateKey;

    @Value("${gitLab.sshUrl}")
    private String sshUrl;

    @Autowired
    private GitUtil gitUtil;


    @PostMapping("/push")
    public void push(@RequestParam String branch) {
        // 拿私钥文件生成临时私钥，拿到私钥路径
        String[] lines = privateKey.split("&&");
        File file = new File("id_rsa");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            for (String line : lines) {
                writer.write(line + System.lineSeparator()); // 将每行写入文件
            }
            writer.close();
            // 在这里使用文件携带私钥提交
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        // 读取模板文件，添加东西到Vue文件中
        // 提交代码到远程gitUtil.push(branch,file);
    }


}
