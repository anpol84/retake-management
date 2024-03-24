package com.example.retakeManagement.services;

import com.example.retakeManagement.models.*;
import com.example.retakeManagement.repo.CourseRepo;
import com.example.retakeManagement.repo.RetakeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с пересдачами
 */
@Service
public class RetakeService {
    private final RetakeRepo retakeRepo;
    private final CourseRepo courseRepo;
    /**
     * Конструктор класса
     * @param retakeRepo Репозиторий для пересдач
     * @param courseRepo Репозиторий для курсов
     */
    @Autowired
    public RetakeService(RetakeRepo retakeRepo, CourseRepo courseRepo) {
        this.retakeRepo = retakeRepo;
        this.courseRepo = courseRepo;
    }

    /**
     * Метод для установки пересдачи
     * @param retake Объект DTO для пересдачи
     */
    public void set(RetakeDTO retake){
        for (int i = 0; i < retake.getCourses().size(); i++){
            Retake retake1 = new Retake();
            retake1.setAttempt(retake.getAttempts().get(i));
            retake1.setUser(retake.getUser());
            retake1.setCourse(courseRepo.findById(retake.getCourses().get(i)).get());
            retakeRepo.save(retake1);
        }
    }

    /**
     * Метод для получения всех пересдач
     * @return Список всех пересдач
     */
    public List<Retake> findAll(){
        return retakeRepo.findAll();
    }

    /**
     * Метод для поиска пересдачи по идентификатору
     * @param id Идентификатор пересдачи
     * @return Найденная пересдача или null, если не найдено
     */
    public Retake findById(Integer id){
        return retakeRepo.findById(id).orElse(null);
    }

    /**
     * Метод для создания новой пересдачи
     * @param retake Объект пересдачи
     */
    public void create(Retake retake){
        retakeRepo.save(retake);
    }

    /**
     * Метод для обновления информации о пересдаче
     * @param retake Объект пересдачи
     * @param id Идентификатор пересдачи
     */
    public void update(Retake retake, Integer id){
        Retake retake1 = retakeRepo.findById(id).orElse(null);

        retake1.setAttempt(retake.getAttempt());
        retakeRepo.save(retake1);
    }

    /**
     * Метод для удаления пересдачи по идентификатору
     * @param id Идентификатор пересдачи
     */
    public void delete(Integer id){
        retakeRepo.deleteById(id);
    }


    /**
     * Метод для поиска событий, связанных с пересдачей
     * @param id Идентификатор пересдачи
     * @return Список событий
     */
    public List<Event> findEvents(Integer id) {
        List<Event> temp = retakeRepo.findById(id).orElse(null).getCourse().getEvents();
        List<Event> ans = new ArrayList<>();
        for (Event event : temp){
            if (event.getCount() > 0){
                ans.add(event);
            }
        }
        return ans;
    }

    /**
     * Метод для поиска пересдачи по пользователю и курсу
     * @param user Пользователь
     * @param course Курс
     * @return Найденная пересдача или null, если не найдено
     */
    public Retake findByUserAndCourse(User user, Course course){
        return retakeRepo.findByUserAndCourse(user, course).orElse(null);
    }
}
