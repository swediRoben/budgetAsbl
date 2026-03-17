package com.app.budget.model;
 

import com.app.budget.domain.Devise; 
import com.app.budget.domain.Projet;

import lombok.Data;

@Data
public class NombreExecution { 
    private Integer nombre;
    private Projet projet;
    private Boolean enAttente;
    private Boolean validation;
    private Boolean reception;
    private Boolean retourner;
    private Boolean rejet;
    private String observation; 
    private Devise devise;
}
