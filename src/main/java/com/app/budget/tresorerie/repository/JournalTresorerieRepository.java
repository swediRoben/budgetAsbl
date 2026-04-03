package com.app.budget.tresorerie.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.budget.constate.Typemouvement;
import com.app.budget.domain.Projet;
import com.app.budget.tresorerie.dto.etat.VentilationDetailInterface;
import com.app.budget.tresorerie.entity.JournalTresorerie;

@Repository
public interface JournalTresorerieRepository extends
        JpaRepository<JournalTresorerie, Long>,
        JpaSpecificationExecutor<JournalTresorerie> {
   @Query("SELECT SUM(c.montant*c.taux) FROM JournalTresorerie c WHERE c.compteBancaire.id=:idcompte AND  c.typemouvement=:type ")
    BigDecimal sommeCompteByIdComptebancaire(@Param("idcompte") Long idcompte,@Param("type") Typemouvement type);

   @Query("SELECT SUM(c.montant*c.taux) FROM JournalTresorerie c WHERE c.idExercice=:exercice AND c.projetId=:projetid AND c.banque.actif=:type AND  c.typemouvement=:typemouv ")
   BigDecimal sommeBanque(@Param("exercice") Long exercice,@Param("projetid")  Long projetid,@Param("type")  boolean type,@Param("typemouv")  Typemouvement typemouv);

   @Query("SELECT SUM(c.montant*c.taux) FROM JournalTresorerie c WHERE c.idExercice=:exercice AND c.classe.id=:classeid AND  c.typemouvement=:typemouv ") 
   BigDecimal sommeClasse(@Param("exercice") Long exercice,@Param("classeid")  Long classeid,@Param("typemouv")  Typemouvement typemouv);

   @Query("SELECT c.projetId AS projetid,SUM(c.montant*c.taux) AS montant FROM JournalTresorerie c WHERE c.idExercice=:exercice AND c.classe.id=:classeid AND  c.typemouvement=:typemouv GROUP BY c.projetId ") 
   List<VentilationDetailInterface> sommeAndProjetByClasse(@Param("exercice") Long exercice,@Param("classeid")  Long classeid,@Param("typemouv")  Typemouvement typemouv);

   @Query("SELECT SUM(c.montant*c.taux) FROM JournalTresorerie c WHERE c.idExercice=:exercice AND c.projetId=:projetid AND c.typemouvement=:typemouv ")
   BigDecimal sommeRessource(@Param("exercice") Long exercice,@Param("projetid")  Long projetid,@Param("typemouv") Typemouvement typemouv);

}
 
 
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // private String reference;

    // private Long idExercice;

    // private Long projetId;
    // private Long categorieId; 

    // @Enumerated(EnumType.STRING)
    // private Typemouvement typemouvement;
    // private String numroCheque;

    // @Enumerated(EnumType.STRING)
    // private ModePaiement modepaiement;


    // private BigDecimal taux;
    // private BigDecimal montant;
    // private String objet;
    // private OffsetDateTime date; 

    // @ManyToOne
    // @JoinColumn(name = "id_banque")
    // private Banque banque;

    // @ManyToOne
    // @JoinColumn(name = "id_comte_comptable")
    // private PlanComptable planComptable;
    
    // @ManyToOne
    // @JoinColumn(name = "id_classe")
    // private Classe classe;
    
    // @ManyToOne
    // @JoinColumn(name = "id_devise")
    // private Devise devise; 

    // @ManyToOne
    // @JoinColumn(name = "id_compte_bancaire")
    // private CompteBancaire compteBancaire;
    