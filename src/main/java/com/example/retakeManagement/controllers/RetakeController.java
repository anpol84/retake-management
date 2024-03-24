package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.RetakeDTO;
import com.example.retakeManagement.services.RegistrationService;
import com.example.retakeManagement.services.RetakeService;
import com.example.retakeManagement.services.UntreatedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/retakes")
public class RetakeController {
    private final RetakeService retakeService;
    private final UntreatedStudentService untreatedStudentService;

    @Autowired
    public RetakeController(RetakeService retakeService, RegistrationService registrationService,
                            UntreatedStudentService untreatedStudentService) {
        this.retakeService = retakeService;
        this.untreatedStudentService = untreatedStudentService;

    }

    /**
     * Метод для назначения пересдачи студенту по его id.
     * Создает объект RetakeDTO, устанавливает пользователя и количество попыток для каждого курса.
     * Затем вызывает сервис для сохранения информации о пересдаче и удаляет студента из списка необработанных студентов.
     *
     * @param retake объект RetakeDTO содержащий информацию о пересдаче
     * @param id     идентификатор студента, для которого назначается пересдача
     * @return перенаправляет на страницу со списком необработанных студентов
     */
    @PostMapping("/{id}")
    public String set(@ModelAttribute("retake") RetakeDTO retake, @PathVariable Integer id){
        retake.setUser(untreatedStudentService.findById(id).getUser());
        retake.setAttempts(new ArrayList<>());
        for (int i = 0; i < retake.getCourses().size(); i++){
            retake.getAttempts().add(3);
        }
        retakeService.set(retake);
        untreatedStudentService.delete(id);
        return "redirect:/untreatedStudents";
    }


}
