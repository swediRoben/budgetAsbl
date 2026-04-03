package com.app.budget.user.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.budget.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
public class UsersDTO {

    private Long id;
    private boolean actif;
    private String username;

    private Long idRole;
    private String password;
    private Role role;

    private Long idFonctionnaire;
    private String fonctionnaireNom;
    private List<MenuDTO> permission=new ArrayList<>();
    public UsersDTO(Long id, boolean actif, String username, Long idRole, String password, Role role,
            Long idFonctionnaire, String fonctionnaireNom) {
        this.id = id;
        this.actif = actif;
        this.username = username;
        this.idRole = idRole;
        this.password = password;
        this.role = role;
        this.idFonctionnaire = idFonctionnaire;
        this.fonctionnaireNom = fonctionnaireNom;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isActif() {
        return actif;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getIdRole() {
        return idRole;
    }
    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Long getIdFonctionnaire() {
        return idFonctionnaire;
    }
    public void setIdFonctionnaire(Long idFonctionnaire) {
        this.idFonctionnaire = idFonctionnaire;
    }
    public String getFonctionnaireNom() {
        return fonctionnaireNom;
    }
    public void setFonctionnaireNom(String fonctionnaireNom) {
        this.fonctionnaireNom = fonctionnaireNom;
    }
    public List<MenuDTO> getPermission() {
        return permission;
    }
    public void setPermission(List<MenuDTO> permission) {
        this.permission = permission;
    }

    
}
