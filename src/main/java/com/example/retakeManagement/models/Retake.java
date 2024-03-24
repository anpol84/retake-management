package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс представляет сущность "Пересдача" и отображается в таблице "retake"
 */
@Entity
@Table(name = "retake")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Retake {
    /**
     * Уникальный идентификатор пересдачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Количество попыток пересдачи (от 1 до 5)
     */
    @Min(value = 1, message = "Количество попыток должно быть как минимум 1")
    @Max(value = 5, message = "Количество попыток должно быть не больше 5")
    private Integer attempt;

    /**
     * Курс, по которому проходила пересдача
     */
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    /**
     * Пользователь, проходивший пересдачу
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @Override
    public String toString() {
        return "Retake{" +
                "id=" + id +
                ", attempt=" + attempt +
                ", course=" + course.getId() +
                ", user=" + user.getId() +
                '}';
    }
}