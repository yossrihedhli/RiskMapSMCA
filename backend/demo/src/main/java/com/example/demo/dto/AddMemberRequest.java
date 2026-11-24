package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long userId;
}