package com.example.retakeManagement.services;

import com.example.retakeManagement.models.Course;
import com.example.retakeManagement.models.Specialization;
import com.example.retakeManagement.repo.SpecializationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с специализациями
 */
@Service
public class SpecializationService {

    private final SpecializationRepo specializationRepo;

    /**
     * Конструктор класса
     * @param specializationRepo репозиторий специализаций
     */
    @Autowired
    public SpecializationService(SpecializationRepo specializationRepo) {
        this.specializationRepo = specializationRepo;
    }

    /**
     * Найти специализацию по идентификатору
     * @param id идентификатор специализации
     * @return найденная специализация
     */
    public Specialization findById(Integer id){
        return specializationRepo.findById(id).orElse(null);
    }

    /**
     * Найти специализацию по названию
     * @param name название специализации
     * @return найденная специализация
     */
    public Specialization findByName(String name){
        return specializationRepo.findByName(name).orElse(null);
    }

    /**
     * Найти специализацию по коду
     * @param code код специализации
     * @return найденная специализация
     */
    public Specialization findByCode(String code){
        return specializationRepo.findByCode(code).orElse(null);
    }

    /**
     * Найти все специализации
     * @return список всех специализаций
     */
    public List<Specialization> findAll(){
        return specializationRepo.findAll();
    }

    /**
     * Сохранить специализацию
     * @param specialization специализация для сохранения
     */
    public void save(Specialization specialization){
        specializationRepo.save(specialization);
    }

    /**
     * Обновить специализацию
     * @param specialization новая информация о специализации
     * @param id идентификатор специализации для обновления
     */
    public void update(Specialization specialization, Integer id){
        Specialization specialization1 = specializationRepo.findById(id).orElse(null);
        specialization1.setId(specialization.getId());
        specialization1.setName(specialization.getName());
        specialization1.setCode(specialization.getCode());
        specialization1.setInstitute(specialization.getInstitute());
        specialization1.setCourses(specialization.getCourses());
        specialization1.setInstitute(specialization.getInstitute());
        specializationRepo.save(specialization1);
    }

    /**
     * Удалить специализацию по идентификатору
     * @param id идентификатор специализации для удаления
     */
    public void delete(Integer id){
        specializationRepo.deleteById(id);
    }

    /**
     * Найти все курсы по идентификатору специализации
     * @param id идентификатор специализации
     * @return список курсов для данной специализации
     */
    public List<Course> findCoursesBySpecializationId(Integer id){
        return specializationRepo.findById(id).get().getCourses();
    }
}
