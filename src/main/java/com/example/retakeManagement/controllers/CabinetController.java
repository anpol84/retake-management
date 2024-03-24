package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.Cabinet;
import com.example.retakeManagement.services.CabinetService;
import com.example.retakeManagement.util.CabinetValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с данными о кабинетах.
 */
@Controller
@RequestMapping("/cabinets")
public class CabinetController {

    private final CabinetService cabinetService;
    private final CabinetValidator cabinetValidator;

    /**
     * Конструктор класса CabinetController.
     *
     * @param cabinetService   сервис для работы с данными о кабинетах
     * @param cabinetValidator валидатор для проверки данных о кабинетах
     */
    @Autowired
    public CabinetController(CabinetService cabinetService, CabinetValidator cabinetValidator) {
        this.cabinetService = cabinetService;
        this.cabinetValidator = cabinetValidator;
    }

    /**
     * Метод для отображения всех кабинетов.
     *
     * @param model модель для передачи данных в представление
     * @return представление со списком всех кабинетов
     */
    @GetMapping
    public String findAll(Model model){
        List<Cabinet> cabinets = cabinetService.findAll();
        model.addAttribute("cabinets", cabinets);
        return "cabinets/cabinets";
    }

    /**
     * Метод для поиска шкафа по id.
     *
     * @param id    идентификатор кабинета
     * @param model модель для передачи данных в представление
     * @return представление с информацией о кабинете
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("cabinet", cabinetService.findById(id));
        return "cabinets/cabinet";
    }

    /**
     * Метод для отображения страницы обновления данных о кабинете.
     *
     * @param id    идентификатор кабинета
     * @param model модель для передачи данных в представление
     * @return представление для обновления данных о кабинете
     */
    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("cabinet", cabinetService.findById(id));
        return "cabinets/update";
    }

    /**
     * Метод для обновления данных о кабинете.
     *
     * @param cabinet        объект кабинета для обновления
     * @param bindingResult  результат валидации данных
     * @param id             идентификатор кабинета
     * @param model          модель для передачи данных в представление
     * @return перенаправление на страницу со списком всех кабинетов
     */
    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Cabinet cabinet, BindingResult bindingResult, @PathVariable Integer id,
                         Model model){
        cabinetValidator.validate(cabinet, bindingResult);
        if (bindingResult.hasErrors()){
            return "cabinets/update";
        }
        cabinetService.update(cabinet, id);
        return "redirect:/cabinets";
    }

    /**
     * Метод для отображения формы создания нового кабинета.
     *
     * @param model модель для передачи данных в представление
     * @return представление для создания нового кабинета
     */
    @GetMapping("/new")
    public String newCabinet(Model model){
        model.addAttribute("cabinet", new Cabinet());
        return "cabinets/new";
    }

    /**
     * Метод для создания нового кабинета.
     *
     * @param cabinet        объект кабинета для создания
     * @param bindingResult  результат валидации данных
     * @param model          модель для передачи данных в представление
     * @return перенаправление на страницу со списком всех кабинетов
     */
    @PostMapping
    public String create(@ModelAttribute("cabinet") @Valid Cabinet cabinet, BindingResult bindingResult, Model model){
        cabinetValidator.validate(cabinet, bindingResult);
        if (bindingResult.hasErrors()){
            return "cabinets/new";
        }
        cabinetService.save(cabinet);
        return "redirect:/cabinets";
    }

    /**
     * Метод для удаления кабинета по id.
     *
     * @param id идентификатор кабинета
     * @return перенаправление на страницу со списком всех кабинетов
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        cabinetService.delete(id);
        return "redirect:/cabinets";
    }
}