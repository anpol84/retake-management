package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью "Кабинет"
 */
@Repository
public interface CabinetRepo extends JpaRepository<Cabinet, Integer> {

    /**
     * Метод для поиска кабинета по номеру
     * @param number Номер кабинета
     * @return Optional объект кабинета, если найден, иначе пустой Optional
     */
    Optional<Cabinet> findByNumber(Integer number);
}
