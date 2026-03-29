package com.app.budget.user.dto;

import com.app.budget.constate.SousMenuType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SousMenuDTO {

    private Long id;
    private SousMenuType sousmenu;
}
