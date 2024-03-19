package com.example.clubdeportivo.controllers;

import com.example.clubdeportivo.entities.Event;
import com.example.clubdeportivo.entities.Participation;
import com.example.clubdeportivo.responses.ResponseHandler;
import com.example.clubdeportivo.services.EventService;
import com.example.clubdeportivo.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private ParticipationService participationService;

    @GetMapping("/events")
    public ResponseEntity<Object> findAllEvents() {
        try {
            List<Event> events = eventService.findAll();
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, events);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Object> findEventById(@PathVariable Integer id) {
        try {
            Event event = eventService.findById(id);
            if (event != null) {
                return ResponseHandler.generateResponse("Success", HttpStatus.OK, event);
            }
            return ResponseHandler.generateResponse("Event not found", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        try {
            Event savedEvent = eventService.saveEvent(event);
            return ResponseHandler.generateResponse("Event added successfully", HttpStatus.CREATED, savedEvent);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Integer id) {
        try {
            Event event = eventService.findById(id);
            if (event != null) {
                // Obtener la lista de participaciones asociadas al evento
                List<Participation> participations = event.getParticipations();

                // Actualizar manualmente las participaciones para eliminar la referencia al evento
                for (Participation participation : participations) {
                    participation.setEvent(null);
                    // Guardar la participación actualizada
                    participationService.update(participation);
                }

                // Eliminar el evento
                eventService.deleteEvent(event);

                return ResponseHandler.generateResponse("Event deleted successfully", HttpStatus.OK, event);
            }
            return ResponseHandler.generateResponse("Event not found", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }





    @PutMapping("/events/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        try {
            event.setId(id);
            Event updatedEvent = eventService.updateEvent(event);
            return ResponseHandler.generateResponse("Event updated successfully", HttpStatus.OK, updatedEvent);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
