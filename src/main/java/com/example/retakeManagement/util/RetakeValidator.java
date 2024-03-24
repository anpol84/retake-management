package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Retake;
import com.example.retakeManagement.services.RetakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Валидатор для проверки уникальности пересдачи по предмету для пользователя.
 */
@Component
public class RetakeValidator implements Validator {

    private final RetakeService retakeService;

    /**
     * Конструктор класса RetakeValidator.
     *
     * @param retakeService сервис для работы с пересдачами
     */
    @Autowired
    public RetakeValidator(RetakeService retakeService) {
        this.retakeService = retakeService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Retake.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Retake retake = (Retake) target;
        List<Retake> retakes = retakeService.findAll();

        for (Retake retake1 : retakes) {
            if (retake.getUser().getId().equals(retake1.getUser().getId()) &&
                    retake.getCourse().getId().equals(retake1.getCourse().getId())) {
                errors.rejectValue("course", "", "Пересдача такого предмета уже существует");
            }
        }
    }
}