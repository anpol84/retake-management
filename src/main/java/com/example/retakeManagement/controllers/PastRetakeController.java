package com.example.retakeManagement.controllers;

import com.example.retakeManagement.models.User;
import com.example.retakeManagement.security.PersonDetails;
import com.example.retakeManagement.services.PastRetakeService;
import com.example.retakeManagement.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PastRetakeController {
    private final PastRetakeService pastRetakeService;
    private final RegistrationService registrationService;

    @Autowired
    public PastRetakeController(PastRetakeService pastRetakeService, RegistrationService registrationService) {
        this.pastRetakeService = pastRetakeService;
        this.registrationService = registrationService;
    }

    /**
     * Метод для отображения страницы с прошлыми пересдачами пользователя.
     *
     * @param id идентификатор пользователя
     * @param model объект Model для передачи данных в представление
     * @return представление с прошлыми пересдачами пользователя
     */
    @GetMapping("/auth/pastRetakes/{id}")
    public String pastRetakes(@PathVariable("id") Integer id, Model model){
        if (!registrationService.findById(id).getRole().equals("ROLE_STUDENT")){
            return "auth/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (!personDetails.getUser().getRole().equals("ROLE_ADMIN")
                && personDetails.getUser().getId() != id) {
            return "auth/login";
        }
        User user = registrationService.findById(id);
        model.addAttribute("pastRetakes", pastRetakeService.findAllByUser(user));
        return "auth/pastRetakes";
    }
}
