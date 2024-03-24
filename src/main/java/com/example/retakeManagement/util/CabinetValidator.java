package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Cabinet;
import com.example.retakeManagement.services.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности номера кабинета.
 */
@Component
public class CabinetValidator implements Validator {

    private final CabinetService cabinetService;

    /**
     * Конструктор класса CabinetValidator.
     *
     * @param cabinetService сервис для работы с кабинетами
     */
    @Autowired
    public CabinetValidator(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Cabinet.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Cabinet cabinet = (Cabinet) target;
        Cabinet cabinet1 = cabinetService.findByNumber(cabinet.getNumber());
        if (cabinet.getId() == null) {
            if (cabinet1 != null) {
                errors.rejectValue("number", "", "Такой кабинет уже существует");
            }
        } else {
            if (cabinet1 != null && cabinet1.getId() != cabinet.getId()) {
                errors.rejectValue("number", "", "Такой кабинет уже существует");
            }
        }
    }
}