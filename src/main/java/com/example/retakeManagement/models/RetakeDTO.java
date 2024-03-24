package com.example.retakeManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/* Класс представляет собой сущность "Пересдача" и отображается в таблице "retake"
        */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetakeDTO {

    /* Пользователь, который проходит пересдачу
     */
    private User user;

    /* Список идентификаторов курсов, по которым проходит пересдачу
     */
    private List<Integer> courses;

    /* Список номеров попыток пересдачи
     */
    private List<Integer> attempts;

}