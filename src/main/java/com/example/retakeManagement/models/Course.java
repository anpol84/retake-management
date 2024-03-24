package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий курс обучения.
 */
@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    /**
     * Уникальный идентификатор курса.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Название курса.
     */
    @NotEmpty(message = "Название курса не должно быть пустым")
    private String name;

    /**
     * Номер курса.
     */
    private Integer number;

    /**
     * Семестр, на котором проходится курс.
     */
    private Integer semester;

    /**
     * Тип аттестации по данному курсу.
     */
    @NotEmpty(message = "Тип аттестации не может быть пустым")
    private String type;

    /**
     * Институты, предоставляющие данный курс.
     */
    @ManyToMany
    @JoinTable(
            name = "course_institute",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "institute_id")
    )
    @NotEmpty(message = "У курса должен быть институт")
    private List<Institute> institutes;

    /**
     * Специальности
     */
    @ManyToMany
    @NotEmpty(message = "У курса должна быть специальность")
    @JoinTable(
            name = "course_specialization",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<Specialization> specializations;

    /**
     * Пересдачи курса.
     */
    @OneToMany(mappedBy = "course")
    private List<Retake> retakes;

    /**
     * События, связанные с данным курсом.
     */
    @OneToMany(mappedBy = "course")
    private List<Event> events;
}