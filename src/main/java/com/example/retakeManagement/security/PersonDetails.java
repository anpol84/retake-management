package com.example.retakeManagement.security;

import com.example.retakeManagement.models.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Реализация интерфейса UserDetails для представления информации о пользователе.
 */
public class PersonDetails implements UserDetails {

    /**
     * Пользователь, информация о котором будет представлена
     */
    private final User user;

    /**
     * Конструктор класса PersonDetails.
     *
     * @param user Пользователь, информация о котором будет представлена
     */
    public PersonDetails(User user) {
        this.user = user;
    }

    /**
     * Получение ролей пользователя.
     *
     * @return Список ролей пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * Получение пароля пользователя.
     *
     * @return Пароль пользователя
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Получение логина пользователя.
     *
     * @return Логин пользователя
     */
    @Override
    public String getUsername() {
        return user.getLogin();
    }

    /**
     * Проверка, актуален ли аккаунт пользователя.
     *
     * @return true, если аккаунт не истек
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверка, не заблокирован ли аккаунт пользователя.
     *
     * @return true, если аккаунт не заблокирован
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверка, не истек ли у пользователя срок действия учетных данных.
     *
     * @return true, если данные пользователя не истекли
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверка, активен ли пользователь.
     *
     * @return true, если пользователь активен
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Получение объекта пользователя.
     *
     * @return Пользователь
     */
    public User getUser() {
        return user;
    }
}