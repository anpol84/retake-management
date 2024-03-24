package com.example.retakeManagement.services;

import com.example.retakeManagement.models.UntreatedStudent;
import com.example.retakeManagement.models.User;
import com.example.retakeManagement.repo.UntreatedStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с необработанными студентами
 */
@Service
public class UntreatedStudentService {

    private final UntreatedStudentRepo untreatedStudentRepo;

    /**
     * Конструктор класса
     * @param untreatedStudentRepo репозиторий для необработанных студентов
     */
    @Autowired
    public UntreatedStudentService(UntreatedStudentRepo untreatedStudentRepo) {
        this.untreatedStudentRepo = untreatedStudentRepo;
    }

    /**
     * Метод для получения всех необработанных студентов
     * @return список всех необработанных студентов
     */
    public List<UntreatedStudent> findAll(){
        return untreatedStudentRepo.findAll();
    }

    /**
     * Метод для поиска необработанного студента по идентификатору
     * @param id идентификатор искомого студента
     * @return объект необработанного студента
     */
    public UntreatedStudent findById(Integer id){
        return untreatedStudentRepo.findById(id).orElse(null);
    }

    /**
     * Метод для сохранения необработанного студента
     * @param untreatedStudent объект необработанного студента для сохранения
     */
    public void save(UntreatedStudent untreatedStudent){
        untreatedStudentRepo.save(untreatedStudent);
    }

    /**
     * Метод для удаления необработанного студента по идентификатору
     * @param id идентификатор студента для удаления
     */
    public void delete(Integer id){
        untreatedStudentRepo.deleteById(id);
    }

    /**
     * Метод для поиска необработанного студента по пользователю
     * @param user пользователь, связанный с необработанным студентом
     * @return объект необработанного студента
     */
    public Object findByUser(User user) {
        return untreatedStudentRepo.findByUser(user).orElse(null);
    }
}