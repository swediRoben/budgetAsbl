package com.app.budget.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.budget.user.dto.LoginRequest;
import com.app.budget.user.dto.UsersDTO;
import com.app.budget.user.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<UsersDTO> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                usersService.login(request.getUsername(), request.getPassword())
        );
    }

    @PostMapping
    public ResponseEntity<UsersDTO> create(@RequestBody UsersDTO dto) {
        return ResponseEntity.ok(usersService.save(dto));
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> update(@PathVariable Long id,
                                           @RequestBody UsersDTO dto) {
        return ResponseEntity.ok(usersService.update(id, dto));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getById(id));
    }
 
    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAll() {
        return ResponseEntity.ok(usersService.getAll());
    }
}