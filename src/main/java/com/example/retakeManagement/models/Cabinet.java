package com.example.retakeManagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Сущность кабинета
 */
@Entity
@Table(name = "cabinet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cabinet {
    /**
     * Уникальный идентификатор кабинета
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Номер кабинета
     */
    @Min(value = 1, message = "Номер кабинета должен быть положительным")
    @Max(value = 1000, message = "Номер кабинета не должен быть более 1000")
    private Integer number;

    /**
     * Список событий, происходящих в данном кабинете
     */
    @OneToMany(mappedBy = "cabinet")
    private List<Event> events;

    /**
     * Переопределенный метод toString для кабинета
     *
     * @return строковое представление кабинета
     */
    @Override
    public String toString() {
        return "Cabinet{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}