package com.example.clubdeportivo.services;

import com.example.clubdeportivo.entities.SportDiscipline;
import com.example.clubdeportivo.repositories.SportDisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportDisciplineService {

    @Autowired
    private SportDisciplineRepository sportDisciplineRepository;

    public List<SportDiscipline> findAll() {
        return sportDisciplineRepository.findAll();
    }

    public SportDiscipline findById(Integer id) {
        Optional<SportDiscipline> optional = sportDisciplineRepository.findById(id);
        return optional.orElse(null);
    }

    public SportDiscipline saveSportDiscipline(SportDiscipline sportDiscipline) {
        return sportDisciplineRepository.save(sportDiscipline);
    }

    public void deleteSportDiscipline(SportDiscipline sportDiscipline) {
        sportDisciplineRepository.delete(sportDiscipline);
    }

    public SportDiscipline updateSportDiscipline(SportDiscipline sportDiscipline) {
        return sportDisciplineRepository.save(sportDiscipline);
    }
}
