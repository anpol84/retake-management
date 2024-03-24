package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Institute;
import com.example.retakeManagement.services.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности названия института.
 */
@Component
public class InstituteValidator implements Validator {

    private final InstituteService instituteService;

    /**
     * Конструктор класса InstituteValidator.
     *
     * @param instituteService сервис для работы с институтами
     */
    @Autowired
    public InstituteValidator(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Institute.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Institute institute = (Institute) target;
        Institute existingInstitute = instituteService.findByName(institute.getName());

        if (institute.getId() == null) {
            if (existingInstitute != null) {
                errors.rejectValue("name", "", "Институт с таким названием уже существует");
            }
        } else {
            if (existingInstitute != null && !existingInstitute.getId().equals(institute.getId())) {
                errors.rejectValue("name", "", "Институт с таким названием уже существует");
            }
        }
    }
}