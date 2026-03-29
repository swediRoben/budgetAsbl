package com.app.budget.user.dto;

import com.app.budget.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private Long id;
    private boolean actif;
    private String username;

    private Long idRole;
    private String password;
    private Role role;

    private Long idFonctionnaire;
    private String fonctionnaireNom;
}
