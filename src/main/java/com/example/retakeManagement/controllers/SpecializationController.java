package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.Specialization;
import com.example.retakeManagement.services.InstituteService;
import com.example.retakeManagement.services.SpecializationService;
import com.example.retakeManagement.util.SpecializationValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/specializations")
public class SpecializationController {

    /**
     * Сервис для работы со специализациями.
     */
    private final SpecializationService specializationService;

    /**
     * Валидатор для специализаций.
     */
    private final SpecializationValidator specializationValidator;

    /**
     * Сервис для работы с институтами.
     */
    private final InstituteService instituteService;

    /**
     * Конструктор для инъекции зависимостей.
     * @param specializationService Сервис для работы со специализациями.
     * @param specializationValidator Валидатор для специализаций.
     * @param instituteService Сервис для работы с институтами.
     */
    @Autowired
    public SpecializationController(SpecializationService specializationService,
                                    SpecializationValidator specializationValidator, InstituteService instituteService) {
        this.specializationService = specializationService;
        this.specializationValidator = specializationValidator;
        this.instituteService = instituteService;
    }

    /**
     * Обработка запроса на получение всех специализаций.
     * @param model Модель данных.
     * @return Представление со списком специализаций.
     */
    @GetMapping
    public String findAll(Model model){
        List<Specialization> specializations = specializationService.findAll();
        model.addAttribute("specializations", specializations);
        return "specializations/specializations";
    }

    /**
     * Обработка запроса на получение специализации по идентификатору.
     * @param id Идентификатор специализации.
     * @param model Модель данных.
     * @return Представление с информацией о специализации.
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("specialization", specializationService.findById(id));
        return "specializations/specialization";
    }

    /**
     * Обработка запроса на страницу редактирования специализации.
     * @param id Идентификатор специализации.
     * @param model Модель данных.
     * @return Представление для редактирования специализации.
     */
    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("specialization", specializationService.findById(id));
        model.addAttribute("institutes", instituteService.findAll());
        return "specializations/update";
    }

    /**
     * Обновление информации о специализации.
     * @param specialization Обновленная специализация.
     * @param bindingResult Результаты валидации.
     * @param id Идентификатор специализации.
     * @return Редирект на страницу со списком специализаций.
     */
    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Specialization specialization,
                         BindingResult bindingResult, @PathVariable Integer id){
        specializationValidator.validate(specialization, bindingResult);
        if (bindingResult.hasErrors()){
            return "specializations/update";
        }
        specializationService.update(specialization, id);

        return "redirect:/specializations";
    }

    /**
     * Обработка запроса на создание новой специализации.
     * @param model Модель данных.
     * @return Представление для создания новой специализации.
     */
    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("specialization", new Specialization());
        model.addAttribute("institutes", instituteService.findAll());
        return "specializations/new";
    }

    /**
     * Создание новой специализации.
     * @param specialization Новая специализация.
     * @param bindingResult Результаты валидации.
     * @return Редирект на страницу со списком специализаций.
     */
    @PostMapping
    public String create(@ModelAttribute("specialization") @Valid Specialization specialization, BindingResult bindingResult){
        specializationValidator.validate(specialization, bindingResult);
        if (bindingResult.hasErrors()){
            return "specializations/new";
        }
        specializationService.save(specialization);
        return "redirect:/specializations";
    }

    /**
     * Удаление новой специализации.
     * @return Редирект на страницу со списком специализаций.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        specializationService.delete(id);
        return "redirect:/specializations";
    }
}
