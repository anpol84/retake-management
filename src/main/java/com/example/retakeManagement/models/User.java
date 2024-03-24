package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Класс представляет сущность Пользователь в системе
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     * Уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Имя пользователя
     */
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;

    /**
     * Фамилия пользователя
     */
    @NotEmpty(message = "Фамилия не должна быть пустой")
    private String surname;

    /**
     * Отчество пользователя
     */
    @NotEmpty(message = "Отчество не должно быть пустым")
    private String lastname;

    /**
     * Логин пользователя
     */
    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;

    /**
     * Email пользователя
     */
    @Email(message = "Почта не соответствует шаблону почты")
    @NotEmpty(message = "Почта не должна быть пустой")
    private String email;

    /**
     * Пароль пользователя
     */
    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    /**
     * Роль пользователя (например, студент, преподаватель)
     */
    private String role;

    /**
     * Отдел, к которому относится пользователь
     */
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    /**
     * Специализация пользователя
     */
    @ManyToOne
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;

    /**
     * Необработанные данные о студенте для данного пользователя
     */
    @OneToOne(mappedBy = "user")
    private UntreatedStudent untreatedStudent;

    /**
     * Список пересдач для данного пользователя
     */
    @OneToMany(mappedBy = "user")
    private List<Retake> retakes;

    /**
     * События, организованные данным пользователем
     */
    @OneToMany(mappedBy = "teacher")
    private List<Event> events;

    /**
     * События, в которых участвует данный пользователь в качестве студента
     */
    @ManyToMany(mappedBy = "students")
    private List<Event> studentEvents;

    /**
     * Прошедшие пересдачи пользователя
     */
    @OneToMany(mappedBy = "user")
    private List<PastRetake> pastRetakes;

    /**
     * Переопределенный метод для строкового представления объекта
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}