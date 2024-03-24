package com.example.retakeManagement.repo;

import com.example.retakeManagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

    /**
     * Метод для поиска курса по названию, номеру и семестру.
     *
     * @param name     название курса
     * @param number   номер курса
     * @param Semester семестр, в котором проходит курс
     * @return Optional объект Course, содержащий информацию о курсе, если он найден
     */
    Optional<Course> findByNameAndNumberAndSemester(String name, Integer number, Integer Semester);

}