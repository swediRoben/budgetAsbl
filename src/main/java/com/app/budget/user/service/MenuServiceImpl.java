package com.app.budget.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<MenuDTO> save(List<MenuDTO> dtos) {
        List<MenuDTO> data=new ArrayList<>();
        for (MenuDTO dto : dtos) { 
       MenuType menuType = MenuType.valueOf(dto.getMenu());
         Optional<Menu> men=menuRepository.findByIdRoleAndMenu(dto.getIdRole(),menuType);
          if (!men.isPresent()) {
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
        Menu m= menuRepository.save(menu);
         data.add(toDTO(m));
       }  else{
          Menu menu = new Menu();
        menu.setId(men.get().getId());
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

            // 🔍 chercher si existe déjà dans optionalMenu
            Optional<SousMenu> existing = men.get().getDetails().stream()
                    .filter(v -> v.getSousmenu().equals(sm.getSousmenu()))
                    .findFirst();

            if (existing.isPresent()) {
                // ✅ existe → on reprend l’ID
                s.setId(existing.get().getId());
            } else {
                // ➕ nouveau
                s.setId(null);
            }

            s.setSousmenu(sm.getSousmenu());
            s.setMenu(menu);
            s.setActif(true); // 🔥 important

            return s;
        })
        .toList();
        menu.setDetails(sousMenus);
        Menu m= menuRepository.save(menu);
         data.add(toDTO(m));
       };
          }
        return data;
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