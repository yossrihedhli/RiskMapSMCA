package com.example.demo.service;

import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectMember;
import com.example.demo.entity.ProjectMemberId;
import com.example.demo.entity.User;
import com.example.demo.repository.ProjectMemberRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository memberRepo;
    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    public ProjectMemberService(ProjectMemberRepository memberRepo,
                                ProjectRepository projectRepo,
                                UserRepository userRepo) {
        this.memberRepo = memberRepo;
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public MemberResponse add(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet introuvable"));
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        ProjectMemberId id = new ProjectMemberId(projectId, userId);
        if (memberRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet utilisateur est déjà membre du projet");
        }

        ProjectMember m = new ProjectMember();
        m.setId(id);
        m.setProject(project);
        m.setUser(user);
        memberRepo.saveAndFlush(m);
        return new MemberResponse(m);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> list(Long projectId) {
        if (!projectRepo.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projet introuvable");
        }
        return memberRepo.findByIdProjectId(projectId).stream().map(MemberResponse::new).toList();
    }

    @Transactional
    public void remove(Long projectId, Long userId) {
        ProjectMemberId id = new ProjectMemberId(projectId, userId);
        if (!memberRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre introuvable dans ce projet");
        }
        memberRepo.deleteById(id);
    }
}