package com.app.budget.imports.service;

import java.io.InputStream;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.budget.domain.Classe;
import com.app.budget.domain.PlanComptable;
import com.app.budget.repos.ClasseRepository;
import com.app.budget.repos.PlanComptableRepository;

@Service
public class ExcelImportService {

    private final PlanComptableRepository repository;
    private final ClasseRepository classeRepository;

    public ExcelImportService(PlanComptableRepository repository, ClasseRepository classeRepository) {
        this.repository = repository;
        this.classeRepository = classeRepository;
    }


    public void importExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                Row row = sheet.getRow(i);

                if (row == null) continue;
                Optional<PlanComptable> compte=repository.findByNumero(row.getCell(0).getStringCellValue());
                if (compte.isPresent()) {
                   PlanComptable data = new PlanComptable();
                data.setId(compte.get().getId());
                data.setNumero(row.getCell(0).getStringCellValue());
                data.setLibelle(row.getCell(1).getStringCellValue());
                data.setSens(row.getCell(2).getStringCellValue());
                Optional<Classe> classes=classeRepository.findByLibelle(row.getCell(3).getStringCellValue());
                if (classes.isPresent()) {
                  data.setClasse(classes.get());  
                }else{
                   data.setClasse(null);      
                 }
                repository.save(data); 
                }else{
                 PlanComptable data = new PlanComptable();
                data.setId(null);
                data.setNumero(row.getCell(0).getStringCellValue());
                data.setLibelle(row.getCell(1).getStringCellValue());
                data.setSens(row.getCell(2).getStringCellValue());
                Optional<Classe> classes=classeRepository.findByLibelle(row.getCell(3).getStringCellValue());
                if (classes.isPresent()) {
                  data.setClasse(classes.get());  
                }else{
                   data.setClasse(null);  
                }
                repository.save(data);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur import Excel", e);
        }
    }
}
