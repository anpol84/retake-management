package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Course;
import com.example.retakeManagement.models.Retake;
import com.example.retakeManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с пересдачами (Retake) в базе данных.
 */
@Repository
public interface RetakeRepo extends JpaRepository<Retake, Integer> {

    /**
     * Метод для поиска пересдачи по пользователю и курсу.
     *
     * @param user Пользователь, для которого ищется пересдача.
     * @param course Курс, по которому ищется пересдача.
     * @return Найденная пересдача, обернутая в Optional. Если пересдача не найдена, вернется пустой Optional.
     */
    Optional<Retake> findByUserAndCourse(User user, Course course);
}