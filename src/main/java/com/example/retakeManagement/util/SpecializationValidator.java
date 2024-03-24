package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Specialization;
import com.example.retakeManagement.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**
 * Валидатор для проверки уникальности специализации по названию и коду.
 */
@Component
public class SpecializationValidator implements Validator {

    private final SpecializationService specializationService;

    /**
     * Конструктор класса SpecializationValidator.
     *
     * @param specializationService сервис для работы со специализациями
     */
    @Autowired
    public SpecializationValidator(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Specialization.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Specialization specialization = (Specialization) target;
        Specialization specializationByName = specializationService.findByName(specialization.getName());
        Specialization specializationByCode = specializationService.findByCode(specialization.getCode());

        if (specialization.getId() == null) {
            if (specializationByName != null) {
                errors.rejectValue("name", "", "Специальность с таким названием уже существует");
            }
            if (specializationByCode != null) {
                errors.rejectValue("code", "", "Специальность с таким кодом уже существует");
            }
        } else {
            if (specializationByName != null && !specializationByName.getId().equals(specialization.getId())) {
                errors.rejectValue("name", "", "Специальность с таким названием уже существует");
            }
            if (specializationByCode != null && !specializationByCode.getId().equals(specialization.getId())) {
                errors.rejectValue("code", "", "Специальность с таким кодом уже существует");
            }
        }
    }
}