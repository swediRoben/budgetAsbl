package com.app.budget.user.service;

import java.util.List;

import com.app.budget.user.dto.MenuDTO;

public interface MenuService {

    List<MenuDTO> save(List<MenuDTO> dto);

    List<MenuDTO> getByRole(Long roleId);
}