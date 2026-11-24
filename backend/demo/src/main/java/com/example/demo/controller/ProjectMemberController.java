package com.example.demo.controller;

import com.example.demo.dto.AddMemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService service;

    public ProjectMemberController(ProjectMemberService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','RISK_MANAGER')")
    public MemberResponse add(@PathVariable Long projectId, @Valid @RequestBody AddMemberRequest req) {
        return service.add(projectId, req.getUserId());
    }

    @GetMapping
    public List<MemberResponse> list(@PathVariable Long projectId) {
        return service.list(projectId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','RISK_MANAGER')")
    public void remove(@PathVariable Long projectId, @PathVariable Long userId) {
        service.remove(projectId, userId);
    }
}