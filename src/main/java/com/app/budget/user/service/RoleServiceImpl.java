package com.app.budget.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.user.dto.RoleDTO;
import com.app.budget.user.entity.Role;
import com.app.budget.user.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDTO save(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setRole(dto.getRole());
        role.setActif(dto.isActif());

        return toDTO(roleRepository.save(role));
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private RoleDTO toDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRole(),
                role.isActif()
        );
    }
}
