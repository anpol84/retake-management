package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий сущность "Кафедра" в базе данных.
 */
@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    /**
     * Уникальный идентификатор кафедры.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Название кафедры.
     */
    @NotEmpty(message = "Название кафедры не может быть пустым")
    private String name;

    /**
     * Институт, к которому относится кафедра.
     */
    @ManyToOne
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    private Institute institute;

    /**
     * Список преподавателей, работающих на данной кафедре.
     */
    @OneToMany(mappedBy = "department")
    private List<User> teachers;
}