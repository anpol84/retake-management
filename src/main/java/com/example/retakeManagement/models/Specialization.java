package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий сущность "Специализация" в базе данных.
 */
@Entity
@Table(name = "specialization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {
    /** Уникальный идентификатор специализации */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Название специализации */
    @NotEmpty(message = "Название специальности не может быть пустым")
    private String name;

    /** Код специализации */
    @NotEmpty(message = "Код не может быть пустым")
    @Size(min = 8, max = 8, message = "Код должен состоять из 8 символов")
    @Pattern(regexp = "\\d\\d\\.\\d\\d\\.\\d\\d", message = "Код должен состоять из 6 цифр, разделенных точками по 2 цифры")
    private String code;

    /** Институт, в котором доступна специализация */
    @ManyToOne
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    private Institute institute;

    /** Список курсов, относящихся к данной специализации */
    @ManyToMany(mappedBy = "specializations")
    private List<Course> courses;

    /** Список студентов, обучающихся по данной специализации */
    @OneToMany(mappedBy = "specialization")
    private List<User> students;

    /**
     * Переопределенный метод toString для удобного отображения объекта в виде строки.
     * @return Название специализации
     */
    @Override
    public String toString() {
        return name;
    }
}
