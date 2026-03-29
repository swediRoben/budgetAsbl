package com.app.budget.user.service;

import java.util.List;

import com.app.budget.user.dto.MenuDTO;

public interface MenuService {

    MenuDTO save(MenuDTO dto);

    List<MenuDTO> getByRole(Long roleId);
}