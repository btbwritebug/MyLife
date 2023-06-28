package com.btb.controller;

import com.btb.dto.FormRelationBean;
import com.btb.dto.ValidationProgress;
import com.btb.service.MyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/myLife")
@Api(value = "用户接口", tags = {"用户接口"})
public class FirstController {
    @Autowired
    MyService myServiceImpl;

    @PostMapping("/validate")
    public ResponseEntity<String> validateParameters(@RequestBody List<FormRelationBean> parameters) {
        // 异步校验参数
        CompletableFuture<Void> validationTask = myServiceImpl.validateAsync(parameters);
        // 返回任务ID给前端，用于后续获取校验进度
        String taskId = UUID.randomUUID().toString();
        return ResponseEntity.ok(taskId);
    }

    @GetMapping("/progress/{taskId}")
    public ResponseEntity<ValidationProgress> getValidationProgress(@PathVariable String taskId) {
        // 根据任务ID获取校验进度
        ValidationProgress progress = myServiceImpl.getValidationProgress(taskId);

        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
