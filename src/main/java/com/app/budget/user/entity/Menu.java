package com.app.budget.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.app.budget.constate.MenuType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "menus")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MenuType menu;
    @Column(name = "id_role")
    private Long idRole;
    
    @ManyToOne
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private Role role;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SousMenu> details = new ArrayList<>();
}
