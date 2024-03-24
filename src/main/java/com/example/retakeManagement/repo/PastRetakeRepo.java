package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.PastRetake;
import com.example.retakeManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с прошлыми пересдачами ({@link PastRetake}).
 * Позволяет осуществлять операции сохранения, обновления, удаления и поиска объектов {@link PastRetake}.
 */
@Repository
public interface PastRetakeRepo extends JpaRepository<PastRetake, Integer> {

    /**
     * Поиск всех прошлых пересдач для указанного пользователя.
     *
     * @param user Пользователь, для которого осуществляется поиск прошлых пересдач
     * @return Список прошлых пересдач для указанного пользователя
     */
    List<PastRetake> findAllByUser(User user);
}