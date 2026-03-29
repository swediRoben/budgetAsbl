package com.app.budget.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.constate.MenuType;
import com.app.budget.user.dto.MenuDTO;
import com.app.budget.user.dto.SousMenuDTO;
import com.app.budget.user.entity.Menu;
import com.app.budget.user.entity.Role;
import com.app.budget.user.entity.SousMenu;
import com.app.budget.user.repository.MenuRepository;
import com.app.budget.user.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RoleRepository roleRepository;

    @Override
    public MenuDTO save(MenuDTO dto) {

        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setMenu(MenuType.valueOf(dto.getMenu()));

        if (dto.getIdRole() != null) {
            Role role = roleRepository.findById(dto.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            menu.setRole(role);
            menu.setIdRole(role.getId());
        }

        List<SousMenu> sousMenus = dto.getSousMenus().stream()
                .map(sm -> {
                    SousMenu s = new SousMenu();
                    s.setId(sm.getId());
                    s.setSousmenu(sm.getSousmenu());
                    s.setMenu(menu);
                    return s;
                }).toList();

        menu.setDetails(sousMenus);

        return toDTO(menuRepository.save(menu));
    }

    @Override
    public List<MenuDTO> getByRole(Long roleId) {
        return menuRepository.findMenusWithSousMenus(roleId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private MenuDTO toDTO(Menu menu) {
        List<SousMenuDTO> sousMenus = menu.getDetails().stream()
                .map(sm -> new SousMenuDTO(
                        sm.getId(),
                        sm.getSousmenu()
                )).toList();

        return new MenuDTO(
                menu.getId(),
                menu.getMenu().name(),
                menu.getIdRole(),
                menu.getRole() != null ? menu.getRole().getRole() : null,
                sousMenus
        );
    }
}