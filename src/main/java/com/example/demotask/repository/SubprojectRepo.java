package com.example.demotask.repository;

import com.example.demotask.model.Subproject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubprojectRepo extends JpaRepository<Subproject, Long> {
}
