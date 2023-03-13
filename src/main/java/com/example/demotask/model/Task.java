package com.example.demotask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type = Type.FOR_DEVELOPER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.NEW;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date")
    private Date creationDate = new Date();

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "my_user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MyUser myUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subproject_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Subproject subproject;

    @Column(name = "message")
    private String message;

}
