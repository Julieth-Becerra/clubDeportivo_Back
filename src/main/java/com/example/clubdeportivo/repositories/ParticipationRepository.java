package com.example.clubdeportivo.repositories;

import com.example.clubdeportivo.entities.Event;
import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    List<Participation> findByMember(Member member);

    List<Participation> findByEvent(Event event);
}
