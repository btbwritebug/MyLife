package com.btb.service.impl;

import com.btb.dto.FormRelationBean;
import com.btb.dto.ValidationProgress;
import com.btb.service.MyService;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MyServiceImpl implements MyService {

    private final Map<String, ValidationProgress> progressMap = new ConcurrentHashMap<>();
    @Override
    public CompletableFuture<Void> validateAsync(List<FormRelationBean> formRelationBeanList) {
        String taskId = UUID.randomUUID().toString();
        // 校验进度
        ValidationProgress progress = new ValidationProgress();
        progress.setTotal(formRelationBeanList.size());
        progressMap.put(taskId,progress);

        // 使用并发处理校验任务
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        return null;
    }

    @Override
    public ValidationProgress getValidationProgress(String taskId) {
        return null;
    }

    public void CreateGitRepo() throws IOException {
        Repository newlyCreatedRepo = FileRepositoryBuilder.create(
                new File("/tmp/new_repo/.git"));
        newlyCreatedRepo.create();
    }
}
