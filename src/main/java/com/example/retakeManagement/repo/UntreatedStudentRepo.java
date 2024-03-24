package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.UntreatedStudent;
import com.example.retakeManagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с нераспределенными студентами в базе данных.
 */
@Repository
public interface UntreatedStudentRepo extends JpaRepository<UntreatedStudent, Integer> {

    /**
     * Поиск нераспределенного студента по пользователю.
     *
     * @param user Пользователь, для которого ищется нераспределенный студент.
     * @return Нераспределенный студент, соответствующий указанному пользователю.
     */
    Optional<UntreatedStudent> findByUser(User user);
}