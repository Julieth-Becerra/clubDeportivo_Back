package com.example.clubdeportivo.services;

import com.example.clubdeportivo.entities.Event;
import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.Participation;
import com.example.clubdeportivo.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Participation> findAll() {
        return participationRepository.findAll();
    }

    public Optional<Participation> findById(Integer id) {
        return participationRepository.findById(id);
    }

    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    public void deleteById(Integer id) {
        participationRepository.deleteById(id);
    }

    public Participation update(Participation participation) {
        Optional<Participation> existingParticipation = participationRepository.findById(participation.getId());
        if (existingParticipation.isPresent()) {
            return participationRepository.save(participation);
        } else {
            throw new RuntimeException("Participation with  ID " + participation.getId() + " no found.");
        }
    }
}