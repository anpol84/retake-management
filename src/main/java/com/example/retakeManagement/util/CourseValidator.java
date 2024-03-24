package com.example.retakeManagement.util;

import com.example.retakeManagement.models.Course;
import com.example.retakeManagement.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Валидатор для проверки уникальности названия курса, его номера и семестра.
 */
@Component
public class CourseValidator implements Validator {

    private final CourseService courseService;

    /**
     * Конструктор класса CourseValidator.
     *
     * @param courseService сервис для работы с курсами
     */
    @Autowired
    public CourseValidator(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Поддерживает ли данный класс валидацию.
     *
     * @param clazz класс, который необходимо проверить
     * @return true, если класс поддерживается, иначе false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Course.class.equals(clazz);
    }

    /**
     * Метод для выполнения валидации объекта.
     *
     * @param target объект, который необходимо проверить
     * @param errors объект для сохранения ошибок валидации
     */
    @Override
    public void validate(Object target, Errors errors) {
        Course course = (Course) target;
        Course course1 = courseService.findByNameAndNumberAndSemester(course.getName(), course.getNumber(), course.getSemester());

        if (course.getId() == null) {
            if (course1 != null) {
                errors.rejectValue("name", "", "Такой курс уже существует");
            }
        } else {
            if (course1 != null && course1.getId() != course.getId()) {
                errors.rejectValue("name", "", "Такой курс уже существует");
            }
        }
    }
}
