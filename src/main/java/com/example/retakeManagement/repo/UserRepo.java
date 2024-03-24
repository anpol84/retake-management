package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    /**
     * Поиск пользователя по логину.
     *
     * @param login логин пользователя
     * @return Optional с объектом пользователя, если такой найден
     */
    Optional<User> findByLogin(String login);

    /**
     * Поиск пользователя по электронной почте.
     *
     * @param email электронная почта пользователя
     * @return Optional с объектом пользователя, если такой найден
     */
    Optional<User> findByEmail(String email);

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return Optional с объектом пользователя, если такой найден
     */
    Optional<User> findById(Integer id);

    /**
     * Получение списка пользователей с указанной ролью.
     *
     * @param role роль пользователей
     * @return Список объектов пользователей с указанной ролью
     */
    List<User> findAllByRole(String role);
}