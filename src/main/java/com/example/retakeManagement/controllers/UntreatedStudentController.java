package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.RetakeDTO;
import com.example.retakeManagement.models.UntreatedStudent;
import com.example.retakeManagement.services.SpecializationService;
import com.example.retakeManagement.services.UntreatedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/untreatedStudents")
public class UntreatedStudentController {
    private final UntreatedStudentService untreatedStudentService;
    private final SpecializationService specializationService;

    /**
     * Конструктор контроллера для необработанных студентов.
     * @param untreatedStudentService сервис для работы с необработанными студентами
     * @param specializationService сервис для работы с специализациями
     */
    @Autowired
    public UntreatedStudentController(UntreatedStudentService untreatedStudentService,
                                      SpecializationService specializationService) {
        this.untreatedStudentService = untreatedStudentService;
        this.specializationService = specializationService;
    }

    /**
     * Метод для отображения всех необработанных студентов.
     * @param model объект модели для передачи данных в представление
     * @return представление для отображения всех необработанных студентов
     */
    @GetMapping
    public String findAll(Model model){
        List<UntreatedStudent> untreatedStudents = untreatedStudentService.findAll();
        model.addAttribute("untreatedStudents", untreatedStudents);
        return "untreatedStudents/untreatedStudents";
    }

    /**
     * Метод для отображения информации о конкретном необработанном студенте.
     * @param id идентификатор необработанного студента
     * @param model объект модели для передачи данных в представление
     * @return представление для отображения информации о необработанном студенте
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model){
        Integer specialityId  = untreatedStudentService.findById(id).getUser().getSpecialization().getId();
        model.addAttribute("courses", specializationService.findCoursesBySpecializationId(specialityId));
        model.addAttribute("retake", new RetakeDTO());
        model.addAttribute("user", untreatedStudentService.findById(id));
        return "untreatedStudents/untreatedStudent";
    }
}
