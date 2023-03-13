package com.example.demotask.rest;

import com.example.demotask.model.MyUser;
import com.example.demotask.model.Role;
import com.example.demotask.model.Task;
import com.example.demotask.service.TaskService;
import com.example.demotask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskRestController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }

    @PostMapping
    public void createTask(@RequestBody Task task){
        taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable long id, @RequestBody Task task){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser user = userService.getUserByUsername(username);
        Task taskInDb = taskService.getTaskById(id);

        if(taskInDb.getMyUser().getUsername().equals(username) ||
                user.getRole().equals(Role.ROLE_ADMIN)){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Task updateTask = taskService.getTaskById(id);

            updateTask.setType(task.getType());
            updateTask.setStatus(task.getStatus());
            updateTask.setMessage(task.getMessage());
            updateTask.setCreationDate(task.getCreationDate());
            try {
                updateTask.setUpdateDate(format.parse(format.format(new Date())));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            taskService.saveTask(updateTask);
        }else {
            System.out.println("Not allowed!");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable(name = "id") long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser user = userService.getUserByUsername(username);
        Task taskInDb = taskService.getTaskById(id);

        if(taskInDb.getMyUser().getUsername().equals(username) ||
                user.getRole().equals(Role.ROLE_ADMIN)){
            taskService.deleteTask(id);
        }else {
            System.out.println("Not allowed!");
        }
    }
}
