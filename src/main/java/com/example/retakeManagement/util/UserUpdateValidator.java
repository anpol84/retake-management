package com.example.retakeManagement.util;

import com.example.retakeManagement.models.User;
import com.example.retakeManagement.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности логина и email при обновлении пользователя.
 */
@Component
public class UserUpdateValidator implements Validator {

    private final RegistrationService registrationService;

    /**
     * Конструктор класса UserUpdateValidator.
     *
     * @param registrationService сервис для работы с регистрацией пользователей
     */
    @Autowired
    public UserUpdateValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User userByLogin = registrationService.findByLogin(user.getLogin());
        if (userByLogin != null) {
            if (!user.getId().equals(userByLogin.getId())) {
                errors.rejectValue("login", "", "Пользователь с таким логином уже существует");
            }
        }
        User userByEmail = registrationService.findByEmail(user.getEmail());
        if (userByEmail != null) {
            if (!user.getId().equals(userByEmail.getId())) {
                errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
            }
        }
    }
}