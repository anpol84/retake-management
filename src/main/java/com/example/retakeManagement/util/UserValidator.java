package com.example.retakeManagement.util;


import com.example.retakeManagement.models.User;
import com.example.retakeManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности логина и email при создании нового пользователя.
 */
@Component
public class UserValidator implements Validator {

    private final UserService userService;

    /**
     * Конструктор класса UserValidator.
     *
     * @param userService сервис для работы с пользователями
     */
    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
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
        try {
            userService.loadUserByUsername(user.getLogin());
        } catch (UsernameNotFoundException e) {
            // Логин уникален, проверяем по email
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
            }
            return;
        }
        // Логин уже занят
        errors.rejectValue("login", "", "Пользователь с таким логином уже существует");
    }
}