package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.Course;
import com.example.retakeManagement.services.CourseService;
import com.example.retakeManagement.services.InstituteService;
import com.example.retakeManagement.services.SpecializationService;
import com.example.retakeManagement.util.CourseValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с курсами.
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    /**
     * Сервис для работы с курсами.
     */
    private final CourseService courseService;

    /**
     * Сервис для работы с институтами.
     */
    private final InstituteService instituteService;

    /**
     * Сервис для работы со специализациями.
     */
    private final SpecializationService specializationService;

    /**
     * Валидатор для курсов.
     */
    private final CourseValidator courseValidator;

    /**
     * Конструктор контроллера.
     * @param courseService сервис для работы с курсами
     * @param instituteService сервис для работы с институтами
     * @param specializationService сервис для работы со специализациями
     * @param courseValidator валидатор для курсов
     */
    @Autowired
    public CourseController(CourseService courseService, InstituteService instituteService,
                            SpecializationService specializationService, CourseValidator courseValidator) {
        this.courseService = courseService;
        this.instituteService = instituteService;
        this.specializationService = specializationService;
        this.courseValidator = courseValidator;
    }

    /**
     * Метод для получения всех курсов.
     * @param model модель
     * @return страница с курсами
     */
    @GetMapping
    public String findAll(Model model){
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses/courses";
    }

    /**
     * Метод для получения курса по идентификатору.
     * @param id идентификатор курса
     * @param model модель
     * @return страница курса
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        model.addAttribute("course", courseService.findById(id));
        return "courses/course";
    }

    /**
     * Метод для отображения страницы обновления курса.
     * @param id идентификатор курса
     * @param model модель
     * @return страница обновления курса
     */
    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model){
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("institutes", instituteService.findAll());
        model.addAttribute("specializations", specializationService.findAll());
        return "courses/update";
    }

    /**
     * Метод для обновления курса.
     * @param course курс
     * @param bindingResult результат валидации
     * @param id идентификатор курса
     * @param model модель
     * @return страница обновления курса
     */
    @PutMapping("/{id}")
    public String update(@ModelAttribute @Valid Course course, BindingResult bindingResult, @PathVariable Integer id,
                         Model model){
        courseValidator.validate(course, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("institutes", instituteService.findAll());
            model.addAttribute("specializations", specializationService.findAll());
            return "courses/update";
        }
        courseService.update(course, id);
        return "redirect:/courses";
    }

    /**
     * Метод для создания нового курса.
     * @param model модель
     * @return страница создания нового курса
     */
    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("institutes", instituteService.findAll());
        model.addAttribute("specializations", specializationService.findAll());

        return "courses/new";
    }

    /**
     * Метод для сохранения нового курса.
     * @param course курс
     * @param bindingResult результат валидации
     * @param model модель
     * @return страница создания нового курса
     */
    @PostMapping
    public String create(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult, Model model){
        courseValidator.validate(course, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("institutes", instituteService.findAll());
            model.addAttribute("specializations", specializationService.findAll());
            return "courses/new";
        }
        courseService.save(course);
        return "redirect:/courses";
    }

    /**
     * Метод для удаления нового курса.
     * @return редирект на страницу курсов
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        courseService.deleteById(id);
        return "redirect:/courses";
    }
}
