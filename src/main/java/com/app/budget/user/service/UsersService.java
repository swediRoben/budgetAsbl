package com.app.budget.user.service;

import java.util.List;

import com.app.budget.user.dto.UsersDTO;

public interface UsersService {

    UsersDTO login(String username, String password);

    UsersDTO save(UsersDTO dto);

    UsersDTO update(Long id, UsersDTO dto);

    void delete(Long id);

    UsersDTO getById(Long id);

    List<UsersDTO> getAll();
}
