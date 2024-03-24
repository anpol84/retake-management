package com.example.retakeManagement.services;

import com.example.retakeManagement.models.User;
import com.example.retakeManagement.repo.UserRepo;
import com.example.retakeManagement.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис пользователя, реализующий интерфейс UserDetailsService.
 */
@Service
public class UserService implements UserDetailsService {

    /**
     * Репозиторий пользователей.
     */
    private final UserRepo userRepo;

    /**
     * Конструктор класса UserService.
     * @param userRepo репозиторий пользователей
     */
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Загрузка пользователя по его имени пользователя (логину).
     * @param username имя пользователя (логин)
     * @return пользователь с заданным именем пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByLogin(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(user.get());
    }

    /**
     * Поиск пользователя по его электронной почте.
     * @param email электронная почта пользователя
     * @return пользователь с заданной электронной почтой
     */
    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }
}