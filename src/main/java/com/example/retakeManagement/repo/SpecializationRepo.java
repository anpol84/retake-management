package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Specialization (Специализация).
 * Предоставляет методы для сохранения, обновления, удаления и получения объектов Specialization из базы данных.
 */
@Repository
public interface SpecializationRepo extends JpaRepository<Specialization, Integer> {

    /**
     * Метод для поиска специализации по названию.
     * @param name Название специализации
     * @return Optional объект Specialization, содержащий найденную специализацию или null, если специализация не найдена
     */
    Optional<Specialization> findByName(String name);

    /**
     * Метод для поиска специализации по коду.
     * @param code Код специализации
     * @return Optional объект Specialization, содержащий найденную специализацию или null, если специализация не найдена
     */
    Optional<Specialization> findByCode(String code);
}