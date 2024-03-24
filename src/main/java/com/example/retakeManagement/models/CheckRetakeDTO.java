package com.example.retakeManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс для передачи данных о возможности пересдачи пользователей на мероприятии.
 *
 * Поля:
 * userIds - список идентификаторов пользователей
 * isRetake - список признаков возможности пересдачи для каждого пользователя
 * eventId - идентификатор мероприятия
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRetakeDTO {
    private List<Integer> userIds;
    private List<String> isRetake;
    private Integer eventId;
}