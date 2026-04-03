package com.app.budget.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.budget.user.dto.UsersDTO;
import com.app.budget.user.entity.Role;
import com.app.budget.user.entity.Users;
import com.app.budget.user.repository.RoleRepository;
import com.app.budget.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService { 
    private final MenuService menuService;

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @Override
    public UsersDTO login(String username, String password) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return toDTO(user);
    }

    @Override
    public UsersDTO save(UsersDTO dto) {
        Users user = toEntity(dto);
        return toDTO(usersRepository.save(user));
    }

    @Override
    public UsersDTO update(Long id, UsersDTO dto) {
        Users existing = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setUsername(dto.getUsername());
        existing.setActif(dto.isActif());

        if (dto.getIdRole() != null) {
            Role role = roleRepository.findById(dto.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            existing.setRole(role);
        }

        return toDTO(usersRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDTO getById(Long id) {
        return usersRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UsersDTO> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private UsersDTO toDTO(Users user) {
        UsersDTO data= new UsersDTO(
                user.getId(),
                user.isActif(),
                user.getUsername(), 
                user.getIdRole(),
                null, 
                 user.getRole(), 
                user.getIdFonctionnaire(),
                user.getFonctionnaire().getNom()+"-"+user.getFonctionnaire().getPrenom()
        );
        data.setPermission(menuService.getByRole(user.getIdRole()));
        return data;
    }

    private Users toEntity(UsersDTO dto) {
        Users user = new Users();

        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setActif(dto.isActif());
        user.setPassword(dto.getPassword()); // ⚠️ à hasher

        if (dto.getIdRole() != null) {
            Role role = roleRepository.findById(dto.getIdRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
            user.setIdRole(role.getId());
        }

        user.setIdFonctionnaire(dto.getIdFonctionnaire());

        return user;
    }
}