package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.*;
import com.example.retakeManagement.security.PersonDetails;
import com.example.retakeManagement.services.*;
import com.example.retakeManagement.util.RetakeValidator;
import com.example.retakeManagement.util.UserUpdateValidator;
import com.example.retakeManagement.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с аутентификацией и авторизацией пользователей.
 * В данном классе определены методы для валидации пользователей, регистрации новых пользователей, обновления пользовательских данных,
 * работы с отделениями и специализациями, проведения пересдач, работы с мероприятиями и обработки невыполненных студентов.
 *
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final UserUpdateValidator userUpdateValidator;
    private final DepartmentService departmentService;
    private final SpecializationService specializationService;
    private final RetakeService retakeService;
    private final RetakeValidator retakeValidator;
    private final EventService eventService;
    private final UntreatedStudentService untreatedStudentService;

    /**
     * Конструктор класса, внедряющий необходимые зависимости для работы контроллера.
     *
     * @param userValidator - валидатор пользователей
     * @param registrationService - сервис регистрации пользователей
     * @param userUpdateValidator - валидатор обновления данных пользователей
     * @param departmentService - сервис работы с отделениями
     * @param specializationService - сервис работы со специализациями
     * @param retakeService - сервис проведения пересдач
     * @param retakeValidator - валидатор пересдач
     * @param eventService - сервис работы с мероприятиями
     * @param untreatedStudentService - сервис обработки невыполненных студентов
     */
    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService,
                          UserUpdateValidator userUpdateValidator, DepartmentService departmentService,
                          SpecializationService specializationService, RetakeService retakeService,
                          RetakeValidator retakeValidator, EventService eventService,
                          UntreatedStudentService untreatedStudentService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.userUpdateValidator = userUpdateValidator;
        this.departmentService = departmentService;
        this.specializationService = specializationService;
        this.retakeService = retakeService;
        this.retakeValidator = retakeValidator;
        this.eventService = eventService;
        this.untreatedStudentService = untreatedStudentService;
    }


    /**
     * Метод для отображения страницы авторизации
     *
     * @return  "auth/login"
     */
    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    /**
     * Метод для отображения страницы регистрации пользователей
     *
     * @param user  объект пользователя
     * @param model объект модели
     * @return  "auth/register"
     */
    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("user") User user, Model model){
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("specializations", specializationService.findAll());
        return "auth/register";
    }

    /**
     * Метод для регистрации пользователя
     *
     * @param user           объект пользователя
     * @param bindingResult  объект для связывания результатов
     * @param model          объект модели
     * @return перенаправление на страницу авторизации
     */
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("specializations", specializationService.findAll());
            return "auth/register";
        }
        registrationService.register(user);
        return "redirect:/auth/login";
    }

    /**
     * Метод для отображения страницы профиля пользователя
     *
     * @param model объект модели
     * @return "auth/profile"
     */
    @GetMapping("/profile")
    public String profilePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails);
        return "auth/profile";
    }

    /**
     * Метод для отображения страницы информации о профиле пользователя
     *
     * @param id    идентификатор пользователя
     * @param model объект модели
     * @return  "auth/profile_info" или "auth/login" в случае ошибки доступа
     */
    @GetMapping("/profileInfoT/{id}")
    public String profileInfoPage(@PathVariable Integer id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        model.addAttribute("user", registrationService.findById(id));
        model.addAttribute("user2", personDetails.getUser());
        return "auth/profile_info";
    }

    /**
     * Получает информацию о пересдачах пользователя
     *
     * @param id идентификатор пользователя
     * @param model модель для передачи данных в представление
     * @return представление с информацией о пересдачах
     */
    @GetMapping("/retakesInfo/{id}")
    public String retakesInfo(@PathVariable Integer id, Model model){
        model.addAttribute("user", registrationService.findById(id));
        model.addAttribute("untreatedUser", untreatedStudentService.findByUser(registrationService.findById(id)));
        model.addAttribute("excitingRetakes", registrationService.getCourses(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        return "auth/retakesInfo";
    }

    /**
     * Страница изменения профиля пользователя
     *
     * @param id идентификатор пользователя
     * @param model модель для передачи данных в представление
     * @return представление для изменения профиля
     */
    @GetMapping("/{id}/profileChange")
    public String profileChange(@PathVariable Integer id, Model model){
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("specializations", specializationService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }

        model.addAttribute("user", registrationService.findById(id));
        return "auth/profileChange";
    }


    /**
     * Обновляет информацию о пользователе
     *
     * @param user данные пользователя для обновления
     * @param bindingResult результат валидации данных
     * @param id идентификатор пользователя
     * @return представление после обновления информации
     */
    @PutMapping("/profile/{id}")
    public String update(@ModelAttribute @Valid User user, BindingResult bindingResult, @PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }

        userUpdateValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "auth/profileChange";
        }
        registrationService.update(user, id);
        if (id != personDetails.getUser().getId()){
            return "redirect:/auth/profiles";
        }
        return "redirect:/auth/login";
    }

    /**
     * Удаляет профиль пользователя
     *
     * @param id идентификатор пользователя
     * @return представление после удаления профиля
     */
    @DeleteMapping("/profile/{id}")
    public String delete(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        registrationService.delete(id);
        if (id != personDetails.getUser().getId()){
            return "redirect:/auth/profiles";
        }
        return "redirect:/auth/register";
    }


    /**
     * Метод для регистрации администратора.
     * При успешной регистрации перенаправляет на страницу профиля.
     */
    @PostMapping("/registerAdmin")
    public String registerAdmin(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            return "auth/registerAdmin";
        }
        registrationService.registerAdmin(user);
        return "redirect:/auth/profile";
    }

    /**
     * Метод для получения списка всех пользователей.
     */
    @GetMapping("/profiles")
    public String findAll(Model model){
        List<User> users = registrationService.findAll();
        model.addAttribute("users", users);
        return "auth/profiles";
    }

    /**
     * Метод для отображения страницы обновления информации о пересдаче.
     */
    @GetMapping("/profileInfo/{idR}/{idU}/update")
    public String updatePage(@PathVariable("idU") Integer idU, @PathVariable("idR") Integer idR, Model model){
        model.addAttribute("retake", retakeService.findById(idR));
        model.addAttribute("idU", idU);
        model.addAttribute("idR", idR);
        return "retakes/update";
    }

    /**
     * Метод для обновления информации о пересдаче.
     */
    @PutMapping("/profileInfo/{idR}/{idU}")
    public String update(@ModelAttribute @Valid Retake retake, BindingResult bindingResult,
                         @PathVariable("idU") Integer idU, @PathVariable("idR") Integer idR){
        if (bindingResult.hasErrors()){
            return "retakes/update";
        }
        retakeService.update(retake, idR);
        return "redirect:/auth/profileInfo/" + idU;
    }

    /**
     * Метод для отображения страницы создания новой пересдачи.
     */
    @GetMapping("/profileInfo/{id}/new")
    public String newRetake(Model model, @PathVariable Integer id){
        model.addAttribute("retake", new Retake());
        model.addAttribute("id", id);
        model.addAttribute("courses", registrationService.findById(id).getSpecialization().getCourses());
        return "retakes/new";
    }

    /**
     * Метод для создания новой пересдачи.
     */
    @PostMapping("/profileInfo/{id}")
    public String create(@ModelAttribute("retake") @Valid Retake retake, BindingResult bindingResult, @PathVariable Integer id
            , Model model){
        retake.setUser(registrationService.findById(id));
        retakeValidator.validate(retake, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("id", id);
            model.addAttribute("courses", registrationService.findById(id).getSpecialization().getCourses());
            return "retakes/new";
        }
        retakeService.create(retake);
        return "redirect:/auth/profileInfo/" + id;
    }

    /**
     * Метод для удаления пересдачи.
     */
    @DeleteMapping("/profileInfo/{idR}/{idU}")
    public String deleteRetake(@PathVariable("idR") Integer idR, @PathVariable("idU") Integer idU){
        retakeService.delete(idR);
        return "redirect:/auth/profileInfo/" + idU;
    }



    /**
     * Класс-контроллер, отвечающий за обработку запросов, связанных с повторными экзаменами учителей.
     */
    @GetMapping("/teacherRetakes/{id}")
    public String teacherRetakes(@PathVariable Integer id, Model model){
        // Проверяем, является ли пользователь учителем
        if (!registrationService.findById(id).getRole().equals("ROLE_TEACHER")){
            return "auth/login";
        }
        // Получаем информацию о текущем пользователе
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // Проверяем, имеет ли пользователь роль администратора или совпадает ли его id с запрошенным
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        // Получаем события повторных экзаменов учителя и передаем их на страницу
        model.addAttribute("events", registrationService.findTeacherEvents(id));
        return "auth/teacherRetake";
    }

    /**
     * Метод для подписания на повторный экзамен пользователем.
     */
    @GetMapping("/signRetake/{id}/{idU}")
    public String signRetake(@PathVariable("id") Integer id, @PathVariable("idU") Integer userId, Model model){
        // Проверяем, является ли пользователь студентом
        if (!registrationService.findById(userId).getRole().equals("ROLE_STUDENT")){
            return "auth/login";
        }
        // Получаем информацию о текущем пользователе
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // Проверяем, имеет ли пользователь роль администратора или совпадает ли его id с запрошенным
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != userId) {
            return "auth/login";
        }
        // Получаем информацию о событии повторного экзамена и передаем на страницу
        model.addAttribute("events", retakeService.findEvents(id));
        model.addAttribute("id", userId);
        model.addAttribute("dto", new SignEventDTO());
        return "auth/signRetake";
    }

    /**
     * Метод для подписания пользователя на событие.
     */
    @PostMapping("/signEvent")
    public String signEvent(@ModelAttribute("dto")SignEventDTO signEvent, Model model){
        // Подписываем пользователя на событие
        registrationService.signEvent(signEvent);
        // Получаем информацию о текущем пользователе и передаем на страницу профиля
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails);
        return "auth/profile";
    }

    /**
     * Метод для отображения событий пользователя.
     */
    @GetMapping("/userEvents/{id}")
    public String userEvents(@PathVariable("id") Integer id, Model model){
        // Проверяем, является ли пользователь студентом
        if (!registrationService.findById(id).getRole().equals("ROLE_STUDENT")){
            return "auth/login";
        }
        // Получаем информацию о текущем пользователе
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // Проверяем, имеет ли пользователь роль администратора или совпадает ли его id с запрошенным
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        // Получаем события пользователя и передаем их на страницу
        model.addAttribute("events", registrationService.findEvents(id));
        return "auth/userEvents";
    }


    /**
     * Метод для проверки повторного экзамена.
     */
    @GetMapping("/checkRetake/{id}")
    public String checkRetake(@PathVariable("id") Integer id, Model model){

        model.addAttribute("event", eventService.findById(id));
        model.addAttribute("dto", new CheckRetakeDTO());
        return "auth/checkRetake";
    }

    /**
     * Метод для обработки запроса на проверку пересдач участников.
     * @param checkRetake объект класса CheckRetakeDTO, содержащий информацию о пересдаче
     * @param model объект класса Model для передачи данных в представление
     * @return строку с именем представления "auth/profile"
     */
    @PostMapping("/checkRetakes")
    public String submitRetakes(@ModelAttribute CheckRetakeDTO checkRetake, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("user", personDetails);
        eventService.submitRetakes(checkRetake);
        return "auth/profile";
    }

}
