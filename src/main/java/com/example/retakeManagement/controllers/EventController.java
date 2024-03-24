package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.Event;
import com.example.retakeManagement.services.CabinetService;
import com.example.retakeManagement.services.CourseService;
import com.example.retakeManagement.services.EventService;
import com.example.retakeManagement.services.RegistrationService;
import com.example.retakeManagement.util.EventValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с событиями.
 */
@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventValidator eventValidator;
    private final CourseService courseService;
    private final CabinetService cabinetService;
    private final RegistrationService registrationService;

    /**
     * Конструктор класса EventController.
     * @param eventService сервис для работы с событиями
     * @param eventValidator валидатор событий
     * @param courseService сервис для работы с курсами
     * @param cabinetService сервис для работы с кабинетами
     * @param registrationService сервис для работы с регистрациями
     */
    @Autowired
    public EventController(EventService eventService, EventValidator eventValidator, CourseService courseService,
                           CabinetService cabinetService, RegistrationService registrationService) {
        this.eventService = eventService;
        this.eventValidator = eventValidator;
        this.courseService = courseService;
        this.cabinetService = cabinetService;
        this.registrationService = registrationService;
    }

    /**
     * Метод для отображения всех событий.
     * @param model объект модели
     * @return шаблон страницы со списком всех событий
     */
    @GetMapping
    public String findAll(Model model){
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);

        return "events/events";
    }

    /**
     * Метод для отображения информации о конкретном событии.
     * @param id идентификатор события
     * @param model объект модели
     * @return шаблон страницы с информацией о событии
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("event", eventService.findById(id));
        return "events/event";
    }

    /**
     * Метод для отображения страницы редактирования события.
     * @param id идентификатор события
     * @param model объект модели
     * @return шаблон страницы редактирования события
     */
    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("event", eventService.findById(id));
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("cabinets", cabinetService.findAll());
        model.addAttribute("teachers", registrationService.findTeachers());
        return "events/update";
    }

    /**
     * Метод для обновления информации о событии.
     * @param event объект события
     * @param bindingResult объект для обработки ошибок валидации
     * @param id идентификатор события
     * @param model объект модели
     * @return перенаправление на страницу всех событий
     */
    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Event event,
                         BindingResult bindingResult, @PathVariable Integer id, Model model){
        eventValidator.validate(event, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("cabinets", cabinetService.findAll());
            model.addAttribute("teachers", registrationService.findTeachers());
            return "events/update";
        }
        eventService.update(event, id);
        return "redirect:/events";
    }

    /**
     * Метод для отображения страницы создания нового события.
     * @param model объект модели
     * @return шаблон страницы создания нового события
     */
    @GetMapping("/new")
    public String newEvent(Model model){
        model.addAttribute("event", new Event());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("cabinets", cabinetService.findAll());
        model.addAttribute("teachers", registrationService.findTeachers());
        return "events/new";
    }

    /**
     * Метод для создания нового события.
     *
     * @param event         Событие, которое необходимо создать
     * @param bindingResult Результат валидации данных
     * @param model         Модель данных
     * @return              Страница для создания нового события или перенаправление на список событий
     */
    @PostMapping
    public String create(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult, Model model){
        eventValidator.validate(event, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("cabinets", cabinetService.findAll());
            model.addAttribute("teachers", registrationService.findTeachers());
            return "events/new";
        }

        eventService.save(event);
        return "redirect:/events";
    }

    /**
     * Метод для удаления события по его идентификатору.
     *
     * @param id Идентификатор события, которое необходимо удалить
     * @return   Перенаправление на список событий
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        eventService.delete(id);
        return "redirect:/events";
    }
}
