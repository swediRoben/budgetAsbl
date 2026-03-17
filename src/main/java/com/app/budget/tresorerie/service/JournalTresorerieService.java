package com.app.budget.tresorerie.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.budget.constate.TypeJournal;
import com.app.budget.constate.Typemouvement;
import com.app.budget.domain.Liquidation;
import com.app.budget.domain.PlanComptable;
import com.app.budget.model.LiquidationDTO;
import com.app.budget.repos.ClasseRepository;
import com.app.budget.repos.DeviseRepository;
import com.app.budget.repos.LiquidationRepository;
import com.app.budget.repos.PlanActiviteRepository;
import com.app.budget.repos.PlanComptableRepository;
import com.app.budget.repos.SourceFinacementRepository; 
import com.app.budget.tresorerie.dto.JournalTresorerieDto;
import com.app.budget.tresorerie.dto.JournalTresorerieFilter;
import com.app.budget.tresorerie.entity.Comptabilite;
import com.app.budget.tresorerie.entity.JournalTresorerie;
import com.app.budget.tresorerie.entity.LigneComptable;
import com.app.budget.tresorerie.repository.BanqueRepository;
import com.app.budget.tresorerie.repository.ComptabiliteRepository;
import com.app.budget.tresorerie.repository.CompteBancaireRepository;
import com.app.budget.tresorerie.repository.JournalTresorerieRepository;
import com.app.budget.tresorerie.repository.JournalTresorerieSpecification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JournalTresorerieService {
    private final JournalTresorerieRepository repository;
    private final BanqueRepository banqueRepository;
    private final PlanComptableRepository planComptableRepository;
    private final ClasseRepository classeRepository;
    private final DeviseRepository deviseRepository;
    private final CompteBancaireRepository compteBancaireRepository;
    private final LiquidationRepository liquidationRepository;
    private final PlanActiviteRepository planActiviteRepository;
    private final SourceFinacementRepository sourceFinacementRepository;
    private final ComptabiliteRepository comptabiliteRepository;

    // ================= CREATE =================
    public JournalTresorerieDto create(JournalTresorerieDto dto) {
        JournalTresorerie entity = new JournalTresorerie();
         validateReferences(dto);
        mapToEntity(dto, entity);
        JournalTresorerie journal=repository.save(entity);
        saveComptabilite(journal);
        return toDto(journal);
    }

    // ================= UPDATE =================
    public JournalTresorerieDto update(Long id, JournalTresorerieDto dto) {
        JournalTresorerie entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("JournalTresorerie introuvable"));
        validateReferences(dto);
        mapToEntity(dto, entity);
        return toDto(repository.save(entity));
    }

    private void validateReferences(JournalTresorerieDto dto) {

    if (!banqueRepository.existsById(dto.getBanqueId())) {
        throw new RuntimeException("Banque introuvable");
    }

    if (!compteBancaireRepository.existsById(dto.getCompteBancaireId())) {
        throw new RuntimeException("Compte bancaire introuvable");
    }

    if (!deviseRepository.existsById(dto.getDeviseId())) {
        throw new RuntimeException("Devise introuvable");
    }
}

 private void CheckIfComptabiliser(Long idJournal){
   Optional<Comptabilite> c= comptabiliteRepository.findByIdtresorerie(idJournal);
   if (c.isPresent() && c.get().getType()==TypeJournal.JOURNAL) {
     throw new RuntimeException("Ce déjà été comptilisée");
   }else{
    c.get().setType(TypeJournal.ANNULER);
    comptabiliteRepository.save(c.get());
   }
 }



  public Page<JournalTresorerie> search(
            JournalTresorerieFilter filter,
            Pageable pageable
    ) {
        Specification<JournalTresorerie> spec =
                JournalTresorerieSpecification.withFilter(filter);

        return repository.findAll(spec, pageable)
        ;
    }
    // ================= GET BY ID =================
    @Transactional
    public JournalTresorerieDto getById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("JournalTresorerie introuvable"));
    }

    // ================= DELETE =================
    public void delete(Long id) {
        CheckIfComptabiliser(id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("JournalTresorerie introuvable");
        }
        repository.deleteById(id);
    }

    // ================= FIND ALL AVEC FILTRE =================
    @Transactional
    public List<JournalTresorerieDto> findAll(
            Long exerciceId,
            Long banqueId,
            Long compteBancaireId,
            String numero,
            OffsetDateTime debut,
            OffsetDateTime fin) {

        Specification<JournalTresorerie> spec =
                JournalTresorerieSpecification.filter(
                        exerciceId, banqueId, compteBancaireId,
                        numero, debut, fin);

        return repository.findAll(spec)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // ================= MAPPING =================

    private void mapToEntity(JournalTresorerieDto dto, JournalTresorerie e) {

    // ❌ supprimer si update possible
    // e.setId(null);

    e.setReference(dto.getReference());
    e.setIdExercice(dto.getIdExercice());
    e.setTypemouvement(dto.getTypemouvement());
    e.setTaux(dto.getTaux());
    e.setMontant(dto.getMontant());
    e.setObjet(dto.getObjet());
    e.setDate(dto.getDate());
    e.setProjetId( dto.getProjetId() == null ? null :dto.getProjetId() );
    e.setCategorieId( dto.getCategorieId() == null ? null :dto.getCategorieId() );
    e.setNumroCheque(dto.getNumroCheque());
    e.setModepaiement(dto.getModepaiement());

    // ✅ pattern sécurisé
    e.setBanque(
        dto.getBanqueId() == null ? null :
        banqueRepository.findById(dto.getBanqueId()).orElse(null)
    );

    e.setPlanComptable(
        dto.getPlanComptableId() == null ? null :
        planComptableRepository.findById(dto.getPlanComptableId()).orElse(null)
    );

    e.setClasse(
        dto.getClasseId() == null ? null :
        classeRepository.findById(dto.getClasseId()).orElse(null)
    );

    e.setDevise(
        dto.getDeviseId() == null ? null :
        deviseRepository.findById(dto.getDeviseId()).orElse(null)
    );

    e.setCompteBancaire(
        dto.getCompteBancaireId() == null ? null :
        compteBancaireRepository.findById(dto.getCompteBancaireId()).orElse(null)
    );

    e.setLiquidation(
        dto.getLiquidationId() == null ? null :
        liquidationRepository.findById(dto.getLiquidationId()).orElse(null)
    );

    e.setPlanActivite(
        dto.getIdPlanFondActivite() == null ? null :
        planActiviteRepository.findById(dto.getIdPlanFondActivite()).orElse(null)
    );

    e.setSourceFinacement(
        dto.getSourceFinacementId() == null ? null :
        sourceFinacementRepository.findById(dto.getSourceFinacementId()).orElse(null)
    );
}

    private JournalTresorerieDto toDto(JournalTresorerie e) {
        return JournalTresorerieDto.builder()
                .id(e.getId())
                .reference(e.getReference())
                .idExercice(e.getIdExercice()) 
                .typemouvement(e.getTypemouvement())
                .taux(e.getTaux())
                .montant(e.getMontant())
                .objet(e.getObjet())
                .date(e.getDate())
                .projetId(e.getProjetId() == null ? null :e.getProjetId())
                .categorieId( e.getCategorieId() == null ? null :e.getCategorieId() )
                .numroCheque(e.getNumroCheque())
                .modepaiement(e.getModepaiement())
                .banqueId(e.getBanque() != null ? e.getBanque().getId() : null)
                .planComptableId(e.getPlanComptable() != null ? e.getPlanComptable().getId() : null)
                .classeId(e.getClasse() != null ? e.getClasse().getId() : null)
                .deviseId(e.getDevise() != null ? e.getDevise().getId() : null)
                .compteBancaireId(e.getCompteBancaire() != null ? e.getCompteBancaire().getId() : null)
                .liquidationId(e.getLiquidation() != null ? e.getLiquidation().getId() : null)
                .idPlanFondActivite(e.getPlanActivite() != null ? e.getPlanActivite().getId() : null)
                .sourceFinacementId(e.getSourceFinacement() != null ? e.getSourceFinacement().getId() : null)
                
                .banque(e.getBanque() != null ? e.getBanque() : null)
                .planComptable(e.getPlanComptable() != null ? e.getPlanComptable() : null)
                .classe(e.getClasse() != null ? e.getClasse(): null)
                .devise(e.getDevise() != null ? e.getDevise() : null)
                .compteBancaire(e.getCompteBancaire() != null ? e.getCompteBancaire() : null)
                .liquidation(e.getLiquidation() != null ? mapToDTO(e.getLiquidation(), new LiquidationDTO()) : null)
                .planActivite(e.getPlanActivite() != null ? e.getPlanActivite() : null)
                .sourceFinacement(e.getSourceFinacement() != null ? e.getSourceFinacement() : null)
                
                .build();
    }
    
        private LiquidationDTO mapToDTO(final Liquidation Liquidation, final LiquidationDTO LiquidationDTO) {
        LiquidationDTO.setId(Liquidation.getId());
        LiquidationDTO.setBonEngagment(Liquidation.getBonEngagment());
        LiquidationDTO.setIdEngagement(Liquidation.getIdEngagement());
        LiquidationDTO.setPiece(Liquidation.getPiece());
        LiquidationDTO.setIdExercice(Liquidation.getIdExercice()); 
        LiquidationDTO.setDataEnAttente(Liquidation.getDataEnAttente());
        LiquidationDTO.setDataReception(Liquidation.getDataReception());
        LiquidationDTO.setDataValidation(Liquidation.getDataValidation()); 
        LiquidationDTO.setDataRetourner(Liquidation.getDataRetourner()); 
        LiquidationDTO.setDataRejet(Liquidation.getDataRejet());
        LiquidationDTO.setEnAttente(Liquidation.getEnAttente());
        LiquidationDTO.setValidation(Liquidation.getValidation());
        LiquidationDTO.setReception(Liquidation.getReception());
        LiquidationDTO.setRetourner(Liquidation.getRetourner());
        LiquidationDTO.setRejet(Liquidation.getRejet());
        LiquidationDTO.setMontant(Liquidation.getMontant());
        LiquidationDTO.setIdDevise(Liquidation.getIdDevise()); 
        LiquidationDTO.setTauxDevise(Liquidation.getTauxDevise());
        LiquidationDTO.setIdProjet(Liquidation.getIdProjet());
        LiquidationDTO.setIdCategorie(Liquidation.getIdCategorie());
        LiquidationDTO.setIdPlanFondActivite(Liquidation.getIdPlanFondActivite());
        LiquidationDTO.setPlanActivite(Liquidation.getPlanActivite());
        LiquidationDTO.setIdResponsable(Liquidation.getIdResponsable());
        LiquidationDTO.setObjet(Liquidation.getObjet());
        LiquidationDTO.setDevise(Liquidation.getDevise());
        LiquidationDTO.setObservation(Liquidation.getObservation());  
        LiquidationDTO.setResponsable(Liquidation.getResponsable()); 
        return LiquidationDTO;
    }

    private boolean saveComptabilite(JournalTresorerie l){
        Comptabilite c=new Comptabilite();
        c.setBanque(l.getBanque());
        c.setCompteBancaire(l.getCompteBancaire());
        c.setDate(l.getDate());
        c.setIdExerice(l.getIdExercice());
        c.setObjet(l.getObjet());
        c.setIdtresorerie(l.getId());
        c.setType(TypeJournal.BROUILLARD);
        c.setReference(l.getReference());
        
       if (l.getTypemouvement()==Typemouvement.CREDIT) { // decaisse
        List<LigneComptable> list=new ArrayList<>();
        LigneComptable debuter=new LigneComptable();
        debuter.setCompte(l.getPlanComptable());
        debuter.setDebit(l.getMontant().multiply(l.getTaux()));
        debuter.setCredit(BigDecimal.ZERO);
        debuter.setId(null);
        debuter.setLibelle(l.getObjet()); 
        debuter.setDevise(l.getCompteBancaire().getIdDevise());
        debuter.setEcriture(c);
        list.add(debuter);

        LigneComptable crediter=new LigneComptable();
        PlanComptable p=new PlanComptable();
        p.setId(l.getCompteBancaire().getIdComteComptable());

        crediter.setCompte(l.getPlanComptable());
        crediter.setDebit(BigDecimal.ZERO);
        crediter.setCredit(l.getMontant().multiply(l.getTaux()));
        crediter.setId(null);
        crediter.setDevise(l.getCompteBancaire().getIdDevise());
        crediter.setLibelle(l.getCompteBancaire().getBanque().getLibelle()+" : "+l.getCompteBancaire().getNumero()); 
        crediter.setEcriture(c);
        list.add(crediter);

        c.setLignes(list);

       }else if (l.getTypemouvement()==Typemouvement.DEBIT) {   //encaisse
      
        List<LigneComptable> list=new ArrayList<>();
      
         LigneComptable debuter=new LigneComptable();
        PlanComptable p=new PlanComptable();
        p.setId(l.getCompteBancaire().getIdComteComptable());

        debuter.setCompte(l.getPlanComptable());
        debuter.setDebit(l.getMontant().multiply(l.getTaux()));
        debuter.setCredit(BigDecimal.ZERO);
        debuter.setId(null);
        debuter.setDevise(l.getCompteBancaire().getIdDevise());
        debuter.setLibelle(l.getCompteBancaire().getBanque().getLibelle()+" : "+l.getCompteBancaire().getNumero()); 
        debuter.setEcriture(c);
        list.add(debuter); 

         LigneComptable crediter=new LigneComptable();
        crediter.setCompte(l.getPlanComptable());
        crediter.setDebit(l.getMontant().multiply(l.getTaux()));
        crediter.setCredit(BigDecimal.ZERO);
        crediter.setId(null);
        crediter.setLibelle(l.getSourceFinacement().getCode() +" - "+l.getSourceFinacement().getLibelle()); 
        crediter.setDevise(l.getCompteBancaire().getIdDevise());
        crediter.setEcriture(c);
        list.add(crediter);

        c.setLignes(list);
       }

        comptabiliteRepository.save(c);
        return true;
    }

}