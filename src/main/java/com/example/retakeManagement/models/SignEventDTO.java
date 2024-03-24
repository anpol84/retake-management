package com.example.retakeManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс SignEventDTO представляет собой объект события.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignEventDTO {
    /**
     * Идентификатор пользователя, который идет на пересдачу.
     */
    private Integer userId;

    /**
     * Пересдача, на которую пользователь записывается.
     */
    private Integer eventId;
}
