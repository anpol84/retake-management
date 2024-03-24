package com.example.retakeManagement.services;

import com.example.retakeManagement.models.*;
import com.example.retakeManagement.repo.EventRepo;
import com.example.retakeManagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Сервис регистрации пользователей
 */
@Service
public class RegistrationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UntreatedStudentService untreatedStudentService;
    private final EventRepo eventRepo;

    /**
     * Конструктор сервиса регистрации
     * @param userRepo репозиторий пользователей
     * @param passwordEncoder кодировщик паролей
     * @param untreatedStudentService сервис для необработанных студентов
     * @param eventRepo репозиторий мероприятий
     */
    @Autowired
    public RegistrationService(UserRepo userRepo, PasswordEncoder passwordEncoder,
                               UntreatedStudentService untreatedStudentService, EventRepo eventRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.untreatedStudentService = untreatedStudentService;
        this.eventRepo = eventRepo;
    }

    /**
     * Регистрация пользователя
     * @param user пользователь
     */
    public void register(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        if (user.getRole().equals("ROLE_STUDENT")){
            UntreatedStudent untreatedStudent = new UntreatedStudent();
            untreatedStudent.setUser(user);
            untreatedStudentService.save(untreatedStudent);
        }
    }

    /**
     * Регистрация администратора
     * @param user пользователь
     */
    public void registerAdmin(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_ADMIN");
        userRepo.save(user);
    }


    /**
     * Найти пользователя по идентификатору
     * @param id идентификатор пользователя
     * @return найденный пользователь или null, если не найден
     */
    public User findById(Integer id){
        return userRepo.findById(id).orElse(null);
    }
    /**
     * Найти пользователя по электронной почте
     * @param email электронная почта
     * @return найденный пользователь или null, если не найден
     */
    public User findByEmail(String email){
        return userRepo.findByEmail(email).orElse(null);
    }
    /**
     * Найти пользователя по логину
     * @param login логин
     * @return найденный пользователь или null, если не найден
     */
    public User findByLogin(String login){
        return userRepo.findByLogin(login).orElse(null);
    }
    /**
     * Список учителей
     * @return список учителей
     */
    public List<User> findTeachers(){
        return userRepo.findAllByRole("ROLE_TEACHER");
    }

    /**
     * Обновление информации о пользователе
     * @param user пользователь
     * @param id идентификатор пользователя
     */
    public void update(User user, Integer id){


        User user1 = userRepo.findById(id).orElse(null);
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user1.setId(user.getId());
        user1.setLogin(user.getLogin());
        user1.setPassword(encodedPassword);
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setLastname(user.getLastname());
        user1.setDepartment(user.getDepartment());
        user1.setSpecialization(user.getSpecialization());
        userRepo.save(user1);
    }

    /**
     * Удаление пользователя
     * @param id идентификатор пользователя
     */
    public void delete(Integer id){
        UntreatedStudent untreatedStudent = untreatedStudentService.findById(id);
        if (untreatedStudent != null){
            untreatedStudentService.delete(untreatedStudent.getUser().getId());
        }
        userRepo.deleteById(id);
    }

    /**
     * Получить список всех пользователей
     * @return список всех пользователей
     */
    public List<User> findAll(){
        return userRepo.findAll();
    }

    /**
     * Метод для поиска событий, связанных с преподавателем
     * @param id идентификатор преподавателя
     * @return список событий преподавателя
     */
    public List<Event> findTeacherEvents(Integer id) {
        return userRepo.findById(id).orElse(null).getEvents();
    }

    /**
     * Метод для записи на событие
     * @param signEvent DTO объект с информацией о пользователе и событии
     */
    public void signEvent(SignEventDTO signEvent) {
        User user = userRepo.findById(signEvent.getUserId()).orElse(null);
        Event event = eventRepo.findById(signEvent.getEventId()).orElse(null);
        if (user.getEvents() == null){
            user.setEvents(new ArrayList<>());
        }
        user.getEvents().add(event);
        if (event.getStudents() == null){
            event.setStudents(new ArrayList<>());
        }
        event.getStudents().add(user);
        event.setCount(event.getCount()-1);
        if (event.getCount() < 0){
            event.setCount(0);
        }
        userRepo.save(user);
        eventRepo.save(event);
    }

    /**
     * Метод для поиска событий, связанных с студентом
     * @param id идентификатор студента
     * @return список событий студента
     */
    public List<Event> findEvents(Integer id) {
        return userRepo.findById(id).orElse(null).getStudentEvents();
    }

    /**
     * Метод для получения списка курсов, на которые записан студент
     * @param id идентификатор студента
     * @return список идентификаторов курсов
     */
    public List<Integer> getCourses(Integer id) {
        User user = userRepo.findById(id).orElse(null);
        List<Event> events = user.getStudentEvents();
        List<Integer> courses = new ArrayList<>();
        for (Event event : events){
            courses.add(event.getCourse().getId());
        }
        return courses;
    }

    /**
     * Метод для сохранения информации о пользователе в базе данных
     * @param user объект пользователя для сохранения
     */
    public void save(User user) {
        userRepo.save(user);
    }
}
