package com.example.demotask.rest;

import com.example.demotask.model.*;
import com.example.demotask.service.ProjectService;
import com.example.demotask.service.SubprojectService;
import com.example.demotask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/project")
public class ProjectRestController {
    private final ProjectService projectService;
    private final SubprojectService subprojectService;
    private final TaskService taskService;

    @Autowired
    public ProjectRestController(ProjectService projectService, SubprojectService subprojectService, TaskService taskService) {
        this.projectService = projectService;
        this.subprojectService = subprojectService;
        this.taskService = taskService;
    }

    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getProjects();
    }

    @GetMapping("/subproject")
    public List<Subproject> getAllSubprojects(){
        return subprojectService.getAllSubprojects();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void createProject(@RequestBody Project project){
        createNewProject(project);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public void updateProject(@PathVariable long id ,@RequestBody Project project){
        Project updateProject = projectService.getProjectById(id);

        updateProject.setTitle(project.getTitle());
        projectService.saveProject(updateProject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable(name = "id") long id){
        projectService.deleteProject(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/subproject/{id}")
    public void deleteSubproject(@PathVariable(name = "id") long id){
        subprojectService.deleteSubprojectById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subproject")
    public void createSubproject(@RequestBody Subproject subproject){
        createNewSubproject(subproject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/subproject/{id}")
    public void updateSubproject(@PathVariable long id, @RequestBody Subproject subproject){
        Subproject updateSubproject = subprojectService.getSubprojectById(id);
        updateSubproject.setTitle(subproject.getTitle());

        subprojectService.saveSubproject(updateSubproject);
    }

    private void createNewProject(Project project){
        if(!(project.getSubprojects() == null)){
            List<Subproject> list = project.getSubprojects();

            projectService.saveProject(project);

            for(Subproject sub: list){
                sub.setProject(project);
                createNewSubproject(sub);
            }
        }else {
            projectService.saveProject(project);
        }
    }

    private void createNewSubproject(Subproject subproject){
        if(!(subproject.getProjectTaskList() == null)){
            List<Task> list = subproject.getProjectTaskList();

            subprojectService.saveSubproject(subproject);

            for(Task task: list){
                task.setSubproject(subproject);
                taskService.saveTask(task);
            }

        }else {
            subprojectService.saveSubproject(subproject);
        }
    }
}
