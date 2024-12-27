package com.API.API.service;

import com.API.API.model.EventType;
import com.API.API.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public Optional<EventType> getEventTypeById(Integer id) {
        return eventTypeRepository.findById(id);
    }

    public EventType createEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    public EventType updateEventType(Integer id, EventType updatedEventType) {
        return eventTypeRepository.findById(id)
                .map(eventType -> {
                    eventType.setEventTypeName(updatedEventType.getEventTypeName());
                    return eventTypeRepository.save(eventType);
                })
                .orElseThrow(() -> new RuntimeException("Event Type not found"));
    }

    public void deleteEventType(Integer id) {
        eventTypeRepository.deleteById(id);
    }
}
