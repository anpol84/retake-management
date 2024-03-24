package com.example.retakeManagement.services;

import com.example.retakeManagement.models.Department;
import com.example.retakeManagement.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с отделами.
 */
@Service
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    /**
     * Конструктор класса DepartmentService.
     *
     * @param departmentRepo репозиторий для работы с отделами
     */
    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    /**
     * Получить все отделы.
     *
     * @return список всех отделов
     */
    public List<Department> findAll(){
        return departmentRepo.findAll();
    }

    /**
     * Найти отдел по идентификатору.
     *
     * @param id идентификатор отдела
     * @return найденный отдел или null, если отдел не найден
     */
    public Department findById(Integer id){
        return departmentRepo.findById(id).orElse(null);
    }

    /**
     * Найти отдел по имени.
     *
     * @param name название отдела
     * @return найденный отдел или null, если отдел не найден
     */
    public Department findByName(String name){
        return departmentRepo.findByName(name).orElse(null);
    }

    /**
     * Сохранить отдел.
     *
     * @param department отдел для сохранения
     */
    public void save(Department department){
        departmentRepo.save(department);
    }

    /**
     * Обновить информацию об отделе.
     *
     * @param department новая информация об отделе
     * @param id идентификатор отдела для обновления
     */
    public void update(Department department, Integer id){
        Department department1 = departmentRepo.findById(id).orElse(null);
        department1.setId(department.getId());
        department1.setName(department.getName());
        department1.setInstitute(department.getInstitute());
        department1.setTeachers(department.getTeachers());
        departmentRepo.save(department1);
    }

    /**
     * Удалить отдел по идентификатору.
     *
     * @param id идентификатор отдела для удаления
     */
    public void delete(Integer id){
        departmentRepo.deleteById(id);
    }
}