package com.example.demotask.service;

import com.example.demotask.model.Project;
import com.example.demotask.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public List<Project> getProjects(){
        return projectRepo.findAll();
    }

    public Project getProjectById(long id){
        return projectRepo.findById(id).get();
    }

    public void saveProject(Project project){
        projectRepo.save(project);
    }

    public void deleteProject(long id){
        projectRepo.deleteById(id);
    }
}
