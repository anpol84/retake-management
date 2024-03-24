package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.Institute;
import com.example.retakeManagement.services.InstituteService;
import com.example.retakeManagement.util.InstituteValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления институтами.
 */
@Controller
@RequestMapping("/institutes")
public class InstituteController {

    private final InstituteService instituteService;
    private final InstituteValidator instituteValidator;

    /**
     * Конструктор класса InstituteController.
     * @param instituteService сервис для работы с институтами
     * @param instituteValidator валидатор для проверки данных института
     */
    @Autowired
    public InstituteController(InstituteService instituteService, InstituteValidator instituteValidator) {
        this.instituteService = instituteService;
        this.instituteValidator = instituteValidator;
    }

    /**
     * Метод для отображения всех институтов.
     * @param model модель для передачи данных в представление
     * @return представление со списком всех институтов
     */
    @GetMapping
    public String findAll(Model model){
        List<Institute> institutes = instituteService.findAll();
        model.addAttribute("institutes", institutes);
        return "institutes/institutes";
    }

    /**
     * Метод для отображения информации об институте по его id.
     * @param id идентификатор института
     * @param model модель для передачи данных в представление
     * @return представление с информацией об институте
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("institute", instituteService.findById(id));
        return "institutes/institute";
    }

    /**
     * Метод для отображения страницы обновления информации об институте.
     * @param id идентификатор института
     * @param model модель для передачи данных в представление
     * @return представление для обновления информации об институте
     */
    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("institute", instituteService.findById(id));
        return "institutes/update";
    }

    /**
     * Метод для обновления информации об институте.
     * @param institute данные института для обновления
     * @param bindingResult результаты валидации данных
     * @param id идентификатор института
     * @return перенаправление на страницу со списком всех институтов
     */
    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Institute institute, BindingResult bindingResult, @PathVariable Integer id){
        instituteValidator.validate(institute, bindingResult);
        if (bindingResult.hasErrors()){
            return "institutes/update";
        }
        instituteService.update(institute, id);
        return "redirect:/institutes";
    }

    /**
     * Метод для отображения страницы создания нового института.
     * @param model модель для передачи данных в представление
     * @return представление для создания нового института
     */
    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("institute", new Institute());
        return "institutes/new";
    }

    /**
     * Метод для создания нового института.
     * @param institute данные нового института
     * @param bindingResult результаты валидации данных
     * @return перенаправление на страницу со списком всех институтов
     */
    @PostMapping
    public String create(@ModelAttribute("institute") @Valid Institute institute, BindingResult bindingResult){
        instituteValidator.validate(institute, bindingResult);
        if (bindingResult.hasErrors()){
            return "institutes/new";
        }
        instituteService.save(institute);
        return "redirect:/institutes";
    }

    /**
     * Метод для удаления института по его id.
     * @param id идентификатор института
     * @return перенаправление на страницу со списком всех институтов
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        instituteService.delete(id);
        return "redirect:/institutes";
    }
}
