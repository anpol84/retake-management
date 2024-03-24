package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Класс, представляющий сущность "Мероприятие" в системе
 */
@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    /**
     * Уникальный идентификатор мероприятия
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Дата проведения мероприятия
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Номер пары, на которой проводится мероприятие
     */
    @Min(value = 1, message = "Номер пары должен быть положительным")
    @Max(value = 6, message = "Номер пары не может быть больше 6")
    private Integer number;

    /**
     * Количество доступных мест на мероприятии
     */
    @Min(value = 0, message = "Количество доступных мест должно быть неотрицательным")
    @Max(value = 30, message = "Количество доступных мест не должно быть больше 30")
    private Integer count;

    /**
     * Курс, к которому относится мероприятие
     */
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    /**
     * Преподаватель, проводящий мероприятие
     */
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User teacher;

    /**
     * Аудитория, где проводится мероприятие
     */
    @ManyToOne
    @JoinColumn(name = "cabinet_id", referencedColumnName = "id")
    private Cabinet cabinet;

    /**
     * Список студентов, зарегистрированных на мероприятие
     */
    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> students;
}
