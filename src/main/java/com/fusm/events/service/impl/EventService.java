package com.fusm.events.service.impl;

import com.fusm.events.entity.EventGeneral;
import com.fusm.events.entity.EventPersonal;
import com.fusm.events.entity.UserLevel;
import com.fusm.events.external.IMoodleService;
import com.fusm.events.model.*;
import com.fusm.events.repository.EventGeneralCustomRepository;
import com.fusm.events.repository.IEventGeneralRepository;
import com.fusm.events.repository.IEventPersonalRepository;
import com.fusm.events.repository.IUserLevelRepository;
import com.fusm.events.service.IEventService;
import com.fusm.events.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {

    @Autowired
    private IEventGeneralRepository eventGeneralRepository;

    @Autowired
    private IEventPersonalRepository eventPersonalRepository;

    @Autowired
    private EventGeneralCustomRepository eventGeneralCustomRepository;

    @Autowired
    private IUserLevelRepository userLevelRepository;

    @Autowired
    private IMoodleService moodleService;


    @Override
    public List<EventModel> getEvents(EventSearch eventSearch) {
        List<EventModel> generalEvents = mapGeneralEvent(eventSearch);
        List<EventModel> eventList = new ArrayList<>(filterEvents(eventSearch.getFilter(), generalEvents));
        if (eventSearch.getFilter() != null) {
            if (eventSearch.getFilter().getTypes().contains(Constant.PERSONAL_TYPE)) eventList.addAll(mapPersonalEvent(eventSearch));
            if (eventSearch.getFilter().getTypes().contains(Constant.MOODLE_TYPE)) eventList.addAll(mapMoodleEvent(eventSearch));
        }
        eventList.sort(Comparator.comparing(EventModel::getStart));
        return eventList;
    }

    @Override
    public void createGeneralEvent(EventGeneralRequest eventRequest) {
        List<LocalDate> eventDates = createRepetition(eventRequest);
        Date currentDate = new Date();
        String serial = (!eventRequest.getEventType().equals(Constant.REPEAT_NO)) ?
                UUID.randomUUID().toString() + currentDate.getTime() : null;
        for (LocalDate eventDate : eventDates) {
            eventGeneralRepository.save(
                    EventGeneral.builder()
                            .tittle(eventRequest.getTittle())
                            .startDate((!eventRequest.getRepetition().equals(Constant.REPEAT_NO)) ? eventDate : eventRequest.getStartDate())
                            .endDate((!eventRequest.getRepetition().equals(Constant.REPEAT_NO)) ? eventDate : eventRequest.getEndDate())
                            .startHour((eventRequest.getIsAllDay()) ? null : eventRequest.getStartHour())
                            .endHour((eventRequest.getIsAllDay()) ? null : eventRequest.getEndHour())
                            .repetition(eventRequest.getRepetition())
                            .feedback(eventRequest.getFeedback())
                            .eventUrl(eventRequest.getEventUrl())
                            .eventType(eventRequest.getEventType())
                            .allDay(eventRequest.getIsAllDay())
                            .createdBy(eventRequest.getCreatedBy())
                            .createdAt(new Date())
                            .enabled(true)
                            .serialRepetition(serial)
                            .facultyId(eventRequest.getFacultyId())
                            .programId(eventRequest.getProgramId())
                            .roleId(eventRequest.getRoleId())
                            .roleType(eventRequest.getRoleType())
                            .build()
            );
        }
    }

    @Override
    public void createPersonalEvent(EventGeneralRequest eventRequest) {
        List<LocalDate> eventDates = createRepetition(eventRequest);
        Date currentDate = new Date();
        String serial = (!eventRequest.getEventType().equals(Constant.REPEAT_NO)) ?
                UUID.randomUUID().toString() + currentDate.getTime() : null;
        for (LocalDate eventDate : eventDates) {
            eventPersonalRepository.save(
                    EventPersonal.builder()
                            .tittle(eventRequest.getTittle())
                            .startDate((!eventRequest.getRepetition().equals(Constant.REPEAT_NO)) ? eventDate : eventRequest.getStartDate())
                            .endDate((!eventRequest.getRepetition().equals(Constant.REPEAT_NO)) ? eventDate : eventRequest.getEndDate())
                            .startHour((eventRequest.getIsAllDay()) ? null : eventRequest.getStartHour())
                            .endHour((eventRequest.getIsAllDay()) ? null : eventRequest.getEndHour())
                            .repetition(eventRequest.getRepetition())
                            .feedback(eventRequest.getFeedback())
                            .eventUrl(eventRequest.getEventUrl())
                            .allDay(eventRequest.getIsAllDay())
                            .createdBy(eventRequest.getCreatedBy())
                            .createdAt(new Date())
                            .enabled(true)
                            .serialRepetition(serial)
                            .userId(eventRequest.getCreatedBy())
                            .build()
            );
        }
    }

    @Override
    public void updateGeneralEvent(EventGeneralRequest eventGeneralRequest, Integer eventId, Boolean isPersonal) {
        if (!isPersonal) {
            Optional<EventGeneral> eventGeneralOptional = eventGeneralRepository.findById(eventId);
            if (eventGeneralOptional.isPresent()) {
                EventGeneral eventGeneral = eventGeneralOptional.get();
                disableGeneralEvent(eventGeneral.getSerialRepetition());
                createGeneralEvent(eventGeneralRequest);
            }
        } else {
            Optional<EventPersonal> eventPersonalOptional = eventPersonalRepository.findById(eventId);
            if (eventPersonalOptional.isPresent()) {
                EventPersonal eventPersonal = eventPersonalOptional.get();
                disablePersonalEvent(eventPersonal.getSerialRepetition());
                createPersonalEvent(eventGeneralRequest);
            }
        }

    }

    @Override
    public void disableEvent(Integer eventId, Boolean isPersonal) {
        if (!isPersonal) {
            Optional<EventGeneral> eventGeneralOptional = eventGeneralRepository.findById(eventId);
            eventGeneralOptional.ifPresent(eventGeneral -> disableGeneralEvent(eventGeneral.getSerialRepetition()));
        } else {
            Optional<EventPersonal> eventPersonalOptional = eventPersonalRepository.findById(eventId);
            eventPersonalOptional.ifPresent(eventPersonal -> disablePersonalEvent(eventPersonal.getSerialRepetition()));
        }

    }

    private List<LocalDate> createRepetition(EventGeneralRequest eventRequest) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = eventRequest.getStartDate();

        while (!currentDate.isAfter(eventRequest.getEndDate())) {
            dates.add(currentDate);
            if (eventRequest.getRepetition().equals(Constant.REPEAT_NO)) {
                break;
            } else if (eventRequest.getRepetition().equals(Constant.REPEAT_DAILY)) {
                currentDate = currentDate.plusDays(1);
            } else if (eventRequest.getRepetition().equals(Constant.REPEAT_WEEKLY)) {
                currentDate = currentDate.plusWeeks(1);
            } else if (eventRequest.getRepetition().equals(Constant.REPEAT_MONTHLY)) {
                currentDate = currentDate.plusMonths(1);
            } else {
                break;
            }
        }

        if (dates.isEmpty()) dates.add(currentDate);

        return dates;
    }

    private void disableGeneralEvent(String serial) {
        List<EventGeneral> eventGenerals = eventGeneralRepository.findAllBySerialRepetitionAndEnabled(serial, true);
        for (EventGeneral general : eventGenerals) {
            general.setEnabled(false);
            eventGeneralRepository.save(general);
        }
    }

    private void disablePersonalEvent(String serial) {
        List<EventPersonal> eventPersonals = eventPersonalRepository.findAllBySerialRepetitionAndEnabled(serial, true);
        for (EventPersonal personal : eventPersonals) {
            personal.setEnabled(false);
            eventPersonalRepository.save(personal);
        }
    }

    private List<EventModel> mapGeneralEvent(EventSearch eventSearch) {
        Optional<UserLevel> userLevelOptional = userLevelRepository.findByRoleId(eventSearch.getRoleId());
        Integer userLevel = userLevelOptional.map(UserLevel::getLevel).orElse(1);
        List<EventModel> eventModelList = eventGeneralCustomRepository.callDynamicSelect(eventSearch, userLevel);
        for (EventModel eventModel : eventModelList) {
            eventModel.getMeta().setCanEdit(eventSearch.getCreatedBy().equals(eventModel.getMeta().getCreatedBy()));
        }
        return eventModelList;
    }

    private List<EventModel> mapPersonalEvent(EventSearch eventSearch) {
        List<EventPersonal> eventPersonalList = eventPersonalRepository
                .findPersonalEvents(eventSearch.getStartDate(), eventSearch.getEndDate(), eventSearch.getCreatedBy());
        List<EventModel> eventModelList = new ArrayList<>();
        for (EventPersonal event : eventPersonalList) {
            eventModelList.add(
                    EventModel.builder()
                            .tittle(event.getTittle())
                            .start(Timestamp.valueOf(LocalDateTime.of(event.getStartDate(), event.getStartHour())))
                            .end(Timestamp.valueOf(LocalDateTime.of(event.getEndDate(), event.getEndHour())))
                            .allDay(event.getAllDay())
                            .type(Constant.PERSONAL_TYPE)
                            .meta(
                                    EventMeta.builder()
                                            .feedback(event.getFeedback())
                                            .eventUrl(event.getEventUrl())
                                            .canEdit(eventSearch.getCreatedBy().equals(event.getCreatedBy()))
                                            .isPersonal(true)
                                            .startDate(event.getStartDate())
                                            .endDate(event.getEndDate())
                                            .startHour(event.getStartHour())
                                            .endHour(event.getEndHour())
                                            .isAllDay(event.getAllDay())
                                            .eventId(event.getEventId())
                                            .repetition(event.getRepetition())
                                            .build()
                            )
                            .build()
            );
        }

        return eventModelList;
    }

    private List<EventModel> mapMoodleEvent(EventSearch eventSearch) {
        CalendarEvent calendarEvent = moodleService.getMoodleEvents(
                EventSearchMoodle.builder()
                        .createdBy(eventSearch.getCreatedBy())
                        .startDate(eventSearch.getStartDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC))
                        .endDate(eventSearch.getEndDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC))
                        .build()
        );
        List<EventModel> eventModelList = new ArrayList<>();
        if (calendarEvent != null) {
            for (EventModelMoodle eventMoodle: calendarEvent.getEvents()) {
                eventModelList.add(
                        EventModel.builder()
                                .tittle(eventMoodle.getName())
                                .start(new Date(eventMoodle.getTimestart()))
                                .end(new Date(eventMoodle.getTimestart() + eventMoodle.getTimeduration()))
                                .allDay(false)
                                .type(Constant.MOODLE_TYPE)
                                .meta(
                                        EventMeta.builder()
                                                .feedback(eventMoodle.getDescription())
                                                .canEdit(false)
                                                .isPersonal(false)
                                                .build()
                                )
                                .build()
                );
            }
        }
        return eventModelList;
    }

    private List<EventModel> filterEvents(Filter filter, List<EventModel> eventModelList) {
        if (filter != null) {
            if (filter.getProgramId() != null) {
                eventModelList = eventModelList.stream().filter(eventModel -> eventModel.getMeta().getProgramId().equals(filter.getProgramId()))
                        .collect(Collectors.toList());
            }

            if (filter.getFacultyId() != null) {
                eventModelList = eventModelList.stream().filter(eventModel -> eventModel.getMeta().getFacultyId().equals(filter.getFacultyId()))
                        .collect(Collectors.toList());
            }

            if (filter.getTypes() != null) {
                eventModelList = eventModelList.stream().filter(eventModel -> filter.getTypes().contains(eventModel.getType()))
                        .collect(Collectors.toList());
            }
        }

        return eventModelList;
    }

}
