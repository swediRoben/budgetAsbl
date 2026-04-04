package com.app.budget.tresorerie.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.budget.constate.TypeClasse;
import com.app.budget.domain.Classe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationComptableDto {  
    private Long id; 
    private TypeClasse type; 
    private Long classeid;
    private Classe classe;
    private List<OperationComptableDetailDto> details=new ArrayList<>();
}
