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

    private final String uploadDir = "uploads/";

    // CREATE avec fichier
    public Structure save(Structure structure, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);

            dest.getParentFile().mkdirs(); 
            file.transferTo(dest);

            structure.setFileName(fileName);
        }

        return repository.save(structure);
    }

    // READ ALL
    public List<Structure> findAll() {
        return repository.findAll();
    }

    // READ ONE
    public Structure findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    // UPDATE
    public Structure update(Long id, Structure newData, MultipartFile file) throws IOException {
        Structure old = repository.findById(id).orElseThrow();

        old.setEmail(newData.getEmail());
        old.setTelephone(newData.getTelephone());
        old.setAdresse(newData.getAdresse());
        old.setReseausocial(newData.getReseausocial());

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);

            dest.getParentFile().mkdirs();
            file.transferTo(dest);

            old.setFileName(fileName);
        }

        return repository.save(old);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}