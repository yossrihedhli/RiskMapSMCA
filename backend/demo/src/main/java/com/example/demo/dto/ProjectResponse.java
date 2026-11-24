package com.example.demo.dto;

import com.example.demo.entity.Project;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ProjectResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String organization;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String status;
    private final LocalDateTime createdAt;

    public ProjectResponse(Project p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.organization = p.getOrganization();
        this.startDate = p.getStartDate();
        this.endDate = p.getEndDate();
        this.status = p.getStatus();
        this.createdAt = p.getCreatedAt();
    }
}