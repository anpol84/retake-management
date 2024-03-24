package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Cabinet;
import com.example.retakeManagement.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    /**
     * Метод для поиска события по дате, номеру и кабинету.
     *
     * @param date    дата события
     * @param number  номер события
     * @param cabinet кабинет, в котором происходит событие
     * @return Optional объект события, найденного по заданным параметрам
     */
    Optional<Event> findByDateAndNumberAndCabinet(Date date, Integer number, Cabinet cabinet);
}