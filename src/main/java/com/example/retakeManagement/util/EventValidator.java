package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Event;
import com.example.retakeManagement.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности назначения пересдачи в определенном кабинете и время.
 */
@Component
public class EventValidator implements Validator {

    private final EventService eventService;

    /**
     * Конструктор класса EventValidator.
     *
     * @param eventService сервис для работы с событиями (пересдачами)
     */
    @Autowired
    public EventValidator(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;
        Event existingEvent = eventService.findByDateAndNumberAndCabinet(event.getDate(), event.getNumber(), event.getCabinet());

        if (event.getId() == null) {
            if (existingEvent != null) {
                errors.rejectValue("cabinet", "", "В этом кабинете в это время уже назначена пересдача");
            }
        } else {
            if (existingEvent != null && !existingEvent.getId().equals(event.getId())) {
                errors.rejectValue("cabinet", "", "В этом кабинете в это время уже назначена пересдача");
            }
        }
    }
}