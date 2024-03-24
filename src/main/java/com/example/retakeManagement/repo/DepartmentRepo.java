package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Department.
 */
@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    /**
     * Поиск отдела по имени.
     *
     * @param name Название отдела.
     * @return Optional сущности Department, содержащий отдел с указанным именем, если он существует.
     */
    Optional<Department> findByName(String name);
}