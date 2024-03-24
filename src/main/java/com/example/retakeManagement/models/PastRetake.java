package com.example.retakeManagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Класс представляет собой сущность "Прошедший пересдачу экзамена" и отображается в таблице "past_retake"
 */
@Entity
@Table(name = "past_retake")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PastRetake {
    /**
     * Уникальный идентификатор прошедшей пересдачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Пользователь, который прошел пересдачу
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Дата прохождения пересдачи
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Результат пересдачи
     */
    private String result;

    /**
     * Предмет, по которому проходила пересдача
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String subject;
}
