package com.example.clubdeportivo.services;

import com.example.clubdeportivo.entities.Event;
import com.example.clubdeportivo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Integer id) {
        Optional<Event> optional = eventRepository.findById(id);
        return optional.orElse(null);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }
}
