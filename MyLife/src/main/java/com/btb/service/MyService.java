package com.btb.service;

import com.btb.dto.FormRelationBean;
import com.btb.dto.ValidationProgress;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MyService {
    CompletableFuture<Void> validateAsync(List<FormRelationBean> formRelationBeanList);

    ValidationProgress getValidationProgress(String taskId);
}
