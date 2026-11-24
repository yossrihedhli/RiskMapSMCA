package com.example.demo.dto;

import com.example.demo.entity.ProjectMember;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private final Long userId;
    private final String fullName;
    private final String email;
    private final String role;
    private final LocalDateTime assignedAt;

    public MemberResponse(ProjectMember m) {
        this.userId = m.getUser().getId();
        this.fullName = m.getUser().getFullName();
        this.email = m.getUser().getEmail();
        this.role = m.getUser().getRole().getName();
        this.assignedAt = m.getAssignedAt();
    }
}