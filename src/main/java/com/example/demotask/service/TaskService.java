package com.example.demotask.service;

import com.example.demotask.model.MyUser;
import com.example.demotask.model.Task;
import com.example.demotask.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserService userService) {
        this.taskRepo = taskRepo;
        this.userService = userService;
    }

    public Task getTaskById(long id){
        Optional<Task> task = taskRepo.findById(id);
        return task.get();
    }

    public List<Task> getTasks(){
        return taskRepo.findAll();
    }

    public void saveTask(Task task){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser user = userService.getUserByUsername(username);
        Task myTask = new Task();

        myTask.setId(task.getId());
        myTask.setType(task.getType());
        myTask.setStatus(task.getStatus());
        myTask.setMessage(task.getMessage());
        myTask.setCreationDate(task.getCreationDate());
        myTask.setUpdateDate(task.getUpdateDate());
        myTask.setSubproject(task.getSubproject());
        myTask.setMyUser(user);

        taskRepo.save(myTask);
    }

    public void deleteTask(long id){
        taskRepo.deleteById(id);
    }
}
