package com.example.retakeManagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс UntreatedStudent представляет собой сущность студента без обработки.
 *
 * Содержит информацию об идентификаторе студента и соответствующем пользователе.
 */
@Entity
@Table(name = "untreated_student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UntreatedStudent {

    /**
     * Уникальный идентификатор студента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Пользователь, связанный с данным студентом.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}