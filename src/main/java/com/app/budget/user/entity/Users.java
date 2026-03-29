package com.app.budget.user.entity;

import com.app.budget.domain.Fonctionnaire;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sous_menu")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_role")
    private Long idRole;
    @Column(name = "id_foncitonnaire")
    private Long idFonctionnaire;
    private boolean actif;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "id_foncitonnaire", insertable = false, updatable = false)
    private Fonctionnaire fonctionnaire;
}
