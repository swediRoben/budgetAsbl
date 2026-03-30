package com.app.budget.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.budget.user.dto.MenuDTO;
import com.app.budget.user.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MenuController {

    private final MenuService menuService;
 
    @PostMapping
    public ResponseEntity<List<MenuDTO>> create(@RequestBody List<MenuDTO> dto) {
        return ResponseEntity.ok(menuService.save(dto));
    }
 
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<MenuDTO>> getByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(menuService.getByRole(roleId));
    }
}
