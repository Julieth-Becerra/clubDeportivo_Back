package com.example.clubdeportivo.controllers;

import com.example.clubdeportivo.entities.Event;
import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.Participation;
import com.example.clubdeportivo.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @GetMapping
    public ResponseEntity<List<Participation>> getAllParticipations() {
        List<Participation> participations = participationService.findAll();
        return ResponseEntity.ok(participations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Integer id) {
        Participation participation = participationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Participation not found with ID: " + id));
        return ResponseEntity.ok(participation);
    }


    @PostMapping
    public ResponseEntity<Participation> createParticipation(@RequestBody Participation participation) {
        Participation savedParticipation = participationService.save(participation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participation> updateParticipation(@PathVariable Integer id, @RequestBody Participation participation) {
        participation.setId(id);
        Participation updatedParticipation = participationService.update(participation);
        return ResponseEntity.ok(updatedParticipation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Integer id) {
        // Buscar la participación a eliminar
        Participation participation = participationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Participation not found with ID: " + id));

        // Obtener el evento asociado a la participación
        Event event = participation.getEvent();

        // Eliminar la participación del evento
        event.getParticipations().remove(participation);

        // Eliminar la participación de la base de datos
        participationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
