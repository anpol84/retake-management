package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с данными об институтах.
 */
@Repository
public interface InstituteRepo extends JpaRepository<Institute, Integer> {

    /**
     * Находит институт по его названию.
     *
     * @param name название института
     * @return Optional с институтом, если он найден, или пустое значение, если не найден
     */
    Optional<Institute> findByName(String name);
}