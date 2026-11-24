package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectRequest {

    @NotBlank(message = "Le nom du projet est obligatoire")
    @Size(max = 150)
    private String name;

    private String description;

    @Size(max = 150)
    private String organization;

    private LocalDate startDate;
    private LocalDate endDate;

    @Size(max = 30)
    private String status;
}