package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.Department;
import com.example.retakeManagement.services.DepartmentService;
import com.example.retakeManagement.services.InstituteService;
import com.example.retakeManagement.util.DepartmentValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с сущностью "кафедра"
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentValidator departmentValidator;
    private final InstituteService instituteService;

    /**
     * Конструктор контроллера кафедры
     * @param departmentService сервис для работы с кафедрой
     * @param departmentValidator валидатор для кафедр
     * @param instituteService сервис для работы с кафедрами
     */
    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentValidator departmentValidator,
                                InstituteService instituteService) {
        this.departmentService = departmentService;
        this.departmentValidator = departmentValidator;
        this.instituteService = instituteService;
    }

    /**
     * Метод для отображения всех кафедр
     * @param model модель для передачи данных на страницу
     * @return страницу со списком кафедр
     */
    @GetMapping
    public String findAll(Model model){
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "departments/departments";
    }

    /**
     * Метод для отображения информации о кафедре по его id
     * @param id идентификатор кафедры
     * @param model модель для передачи данных на страницу
     * @return страницу с информацией о кафедре
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("department", departmentService.findById(id));
        return "departments/department";
    }

    /**
     * Отображает страницу обновления информации о кафедре.
     *
     * @param id    идентификатор кафедры
     * @param model модель для передачи данных на страницу
     * @return страницу обновления информации о кафедре
     */
    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("department", departmentService.findById(id));
        model.addAttribute("institutes", instituteService.findAll());
        return "departments/update";
    }

    /**
     * Обновляет информацию о кафедре.
     *
     * @param department      объект кафедры для обновления
     * @param bindingResult   результат проверки на наличие ошибок
     * @param id              идентификатор кафедры
     * @return перенаправление на страницу со списком кафедр
     */
    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Department department,
                         BindingResult bindingResult, @PathVariable Integer id){
        departmentValidator.validate(department, bindingResult);
        if (bindingResult.hasErrors()){
            return "departments/update";
        }
        departmentService.update(department, id);
        return "redirect:/departments";
    }

    /**
     * Отображает страницу создания новой кафедры.
     *
     * @param model модель для передачи данных на страницу
     * @return страницу создания новой кафедры
     */
    @GetMapping("/new")
    public String newDepartment(Model model){
        model.addAttribute("department", new Department());
        model.addAttribute("institutes", instituteService.findAll());
        return "departments/new";
    }

    /**
     * Создает новую кафедру.
     *
     * @param department      объект кафедры для создания
     * @param bindingResult   результат проверки на наличие ошибок
     * @return перенаправление на страницу со списком кафедр
     */
    @PostMapping
    public String create(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult){
        departmentValidator.validate(department, bindingResult);
        if (bindingResult.hasErrors()){
            return "departments/new";
        }
        departmentService.save(department);
        return "redirect:/departments";
    }

    /**
     * Удаляет кафедру.
     *
     * @param id идентификатор кафедры
     * @return перенаправление на страницу со списком кафедр
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
