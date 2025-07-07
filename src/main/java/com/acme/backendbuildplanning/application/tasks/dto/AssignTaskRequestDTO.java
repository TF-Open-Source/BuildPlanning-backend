package com.acme.backendbuildplanning.application.tasks.dto;

import lombok.Data;

@Data
public class AssignTaskRequestDTO {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
