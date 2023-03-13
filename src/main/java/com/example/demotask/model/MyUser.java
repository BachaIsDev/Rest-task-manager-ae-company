package com.example.demotask.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "my_user")
public class MyUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "keyword")
    private String keyword;

    @OneToMany(mappedBy = "myUser", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Task> userTaskList;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
