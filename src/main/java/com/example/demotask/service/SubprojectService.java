package com.example.demotask.service;

import com.example.demotask.model.Subproject;
import com.example.demotask.repository.SubprojectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {
    private final SubprojectRepo subprojectRepo;

    @Autowired
    public SubprojectService(SubprojectRepo subprojectRepo) {
        this.subprojectRepo = subprojectRepo;
    }

    public Subproject getSubprojectById(long id){
        return subprojectRepo.findById(id).get();
    }

    public void saveSubproject(Subproject subproject){
        subprojectRepo.save(subproject);
    }

    public List<Subproject> getAllSubprojects(){
        return subprojectRepo.findAll();
    }
    public void deleteSubprojectById(long id){
        subprojectRepo.deleteById(id);
    }
}
