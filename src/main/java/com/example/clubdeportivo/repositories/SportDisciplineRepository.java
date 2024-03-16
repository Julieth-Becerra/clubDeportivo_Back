package com.example.clubdeportivo.repositories;

import com.example.clubdeportivo.entities.SportDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportDisciplineRepository extends JpaRepository<SportDiscipline, Integer> {
}
