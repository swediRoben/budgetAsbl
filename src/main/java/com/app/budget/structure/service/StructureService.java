package com.app.budget.structure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.budget.structure.entity.Structure;
import com.app.budget.structure.repository.StructureRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StructureService {

    @Autowired
    private StructureRepository repository;  

    // ✔ dossier externe (PRO)
    private final String uploadDir =
            System.getProperty("user.dir") + "/uploads/";

    public Structure save(Structure structure, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // 🔥 crée dossier uploads
            }

            File dest = new File(dir, fileName);
            file.transferTo(dest);

            structure.setFileName(fileName);
        }

        return repository.save(structure);
    }

    public Structure update(Long id, Structure newData, MultipartFile file) throws IOException {

        Structure old = repository.findById(id).orElseThrow();

        old.setEmail(newData.getEmail());
        old.setTelephone(newData.getTelephone());
        old.setAdresse(newData.getAdresse());
        old.setReseausocial(newData.getReseausocial());

        if (file != null && !file.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File dest = new File(dir, fileName);
            file.transferTo(dest);

            old.setFileName(fileName);
        }

        return repository.save(old);
    }
 
    // READ ALL
    public List<Structure> findAll() {
        return repository.findAll();
    }

    // READ ONE
    public Structure findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
 

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}