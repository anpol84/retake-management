package com.example.retakeManagement.services;

import com.example.retakeManagement.models.Institute;
import com.example.retakeManagement.repo.InstituteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с институтами.
 */
@Service
public class InstituteService {

    private final InstituteRepo instituteRepo;

    /**
     * Конструктор класса InstituteService.
     *
     * @param instituteRepo репозиторий для работы с институтами
     */
    @Autowired
    public InstituteService(InstituteRepo instituteRepo) {
        this.instituteRepo = instituteRepo;
    }

    /**
     * Найти все институты.
     *
     * @return список всех институтов
     */
    public List<Institute> findAll() {
        return instituteRepo.findAll();
    }

    /**
     * Найти институт по идентификатору.
     *
     * @param id идентификатор института
     * @return найденный институт или null, если не найден
     */
    public Institute findById(Integer id) {
        return instituteRepo.findById(id).orElse(null);
    }

    /**
     * Сохранить институт.
     *
     * @param institute институт для сохранения
     */
    public void save(Institute institute) {
        instituteRepo.save(institute);
    }

    /**
     * Обновить информацию об институте.
     *
     * @param institute новая информация об институте
     * @param id идентификатор института для обновления
     */
    public void update(Institute institute, Integer id) {
        Institute existingInstitute = instituteRepo.findById(id).orElse(null);
        if (existingInstitute != null) {
            existingInstitute.setSpecializations(institute.getSpecializations());
            existingInstitute.setCourses(institute.getCourses());
            existingInstitute.setDepartments(institute.getDepartments());
            existingInstitute.setName(institute.getName());
            instituteRepo.save(existingInstitute);
        }
    }

    /**
     * Удалить институт по идентификатору.
     *
     * @param id идентификатор института для удаления
     */
    public void delete(Integer id) {
        instituteRepo.deleteById(id);
    }

    /**
     * Найти институт по имени.
     *
     * @param name имя института
     * @return найденный институт или null, если не найден
     */
    public Institute findByName(String name) {
        return instituteRepo.findByName(name).orElse(null);
    }
}