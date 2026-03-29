package com.app.budget.user.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

    private Long id;
    private String menu;

    private Long idRole;
    private String roleName;

    private List<SousMenuDTO> sousMenus=new ArrayList<>();
}