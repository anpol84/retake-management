package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Department;
import com.example.retakeManagement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности названия кафедры.
 */
@Component
public class DepartmentValidator implements Validator {

    private final DepartmentService departmentService;

    /**
     * Конструктор класса DepartmentValidator.
     *
     * @param departmentService сервис для работы с кафедрами
     */
    @Autowired
    public DepartmentValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Department.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;
        Department department1 = departmentService.findByName(department.getName());

        if (department.getId() == null) {
            if (department1 != null) {
                errors.rejectValue("name", "", "Кафедра с таким названием уже существует");
            }
        } else {
            if (department1 != null && department1.getId() != department.getId()) {
                errors.rejectValue("name", "", "Кафедра с таким названием уже существует");
            }
        }
    }
}
