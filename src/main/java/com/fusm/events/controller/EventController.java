package com.fusm.events.controller;

import com.fusm.events.model.EventGeneralRequest;
import com.fusm.events.model.EventModel;
import com.fusm.events.model.EventSearch;
import com.fusm.events.service.IEventService;
import com.fusm.events.util.AppRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase que expone todos los servicios relacionados con los eventos del calendario
 * ITSense Inc - Andrea Gómez
 */

@RestController
@RequestMapping(value = AppRoutes.EVENTS_ROUTE)
public class EventController {

    @Autowired
    private IEventService eventService;


    /**
     * Crea un evento general
     * @param eventRequest Modelo que contiene la información para crear un evento general
     * @return OK
     */
    @PostMapping("/general")
    public ResponseEntity<String> createGeneralEvent(
            @RequestBody EventGeneralRequest eventRequest
            ) {
        eventService.createGeneralEvent(eventRequest);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Crea un evento personal
     * @param eventRequest Modelo que contiene la información para crear un evento personal
     * @return OK
     */
    @PostMapping("/personal")
    public ResponseEntity<String> createPersonalEvent(
            @RequestBody EventGeneralRequest eventRequest
    ) {
        eventService.createPersonalEvent(eventRequest);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Obtiene la lista de eventos
     * @param eventSearch Modelo que contiene los parámetros de búsqueda para realizar filtrados
     * @return lista de eventos
     */
    @PostMapping("/get")
    public ResponseEntity<List<EventModel>> getEvents(
            @RequestBody EventSearch eventSearch
            ) {
        return ResponseEntity.ok(eventService.getEvents(eventSearch));
    }

    /**
     * Actualiza un evento
     * @param eventId ID del evento
     * @param isPersonal Identifica si el evento es personal o no mediante un TRUE o FALSE
     * @param eventGeneralRequest Modelo que contiene la información a actualizar del evento
     * @return OK
     */
    @PutMapping("/{id}/is-personal/{personal}")
    public ResponseEntity<String> updateEvent(
            @PathVariable("id") Integer eventId,
            @PathVariable("personal") Boolean isPersonal,
            @RequestBody EventGeneralRequest eventGeneralRequest
    ) {
        eventService.updateGeneralEvent(eventGeneralRequest, eventId, isPersonal);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Deshabilita un evento
     * @param eventId ID del evento
     * @param isPersonal Identifica si el evento es personal o no mediante un TRUE o FALSE
     * @return OK
     */
    @DeleteMapping("/{id}/is-personal/{personal}")
    public ResponseEntity<String> disableEvent(
            @PathVariable("id") Integer eventId,
            @PathVariable("personal") Boolean isPersonal
    ) {
        eventService.disableEvent(eventId, isPersonal);
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

}
