package com.example.retakeManagement.services;

import com.example.retakeManagement.models.Course;
import com.example.retakeManagement.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с курсами.
 */
@Service
public class CourseService {
    private final CourseRepo courseRepo;

    /**
     * Конструктор класса CourseService.
     *
     * @param courseRepo репозиторий для работы с курсами
     */
    @Autowired
    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    /**
     * Получить все курсы.
     *
     * @return список всех курсов
     */
    public List<Course> findAll(){
        return courseRepo.findAll();
    }

    /**
     * Найти курс по идентификатору.
     *
     * @param id идентификатор курса
     * @return найденный курс или null, если курс не найден
     */
    public Course findById(Integer id){
        return courseRepo.findById(id).orElse(null);
    }

    /**
     * Сохранить курс.
     *
     * @param course курс для сохранения
     */
    public void save(Course course){
        courseRepo.save(course);
    }

    /**
     * Обновить информацию о курсе.
     *
     * @param course новая информация о курсе
     * @param id идентификатор курса для обновления
     */
    public void update(Course course, Integer id){
        Course course1 = courseRepo.findById(id).orElse(null);
        course1.setId(course.getId());
        course1.setName(course.getName());
        course1.setNumber(course.getNumber());
        course1.setSemester(course.getSemester());
        course1.setInstitutes(course.getInstitutes());
        course1.setSpecializations(course.getSpecializations());
        course1.setType(course.getType());
        courseRepo.save(course1);
    }

    /**
     * Удалить курс по идентификатору.
     *
     * @param id идентификатор курса для удаления
     */
    public void deleteById(Integer id){
        courseRepo.deleteById(id);
    }

    /**
     * Найти курс по имени, номеру и семестру.
     *
     * @param name название курса
     * @param number номер курса
     * @param semester семестр курса
     * @return найденный курс или null, если курс не найден
     */
    public Course findByNameAndNumberAndSemester(String name, Integer number, Integer semester){
        return courseRepo.findByNameAndNumberAndSemester(name, number, semester).orElse(null);
    }
}