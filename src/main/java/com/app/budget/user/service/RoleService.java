package com.app.budget.user.service;

import java.util.List;

import com.app.budget.user.dto.RoleDTO;

public interface RoleService {

    RoleDTO save(RoleDTO dto);

    List<RoleDTO> getAll();
}
