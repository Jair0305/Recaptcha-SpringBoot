package com.google.controller;

import com.google.controller.dto.EmployeeDTO;
import com.google.entities.EmployeeEntity;
import com.google.service.EmployeeService;
import com.google.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller//para imbc
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RecaptchaService recaptchaService;

    @GetMapping(path = {"/", "/all"})
    public String showAll(Model model)
    {
        List<EmployeeEntity> employeeEntityList = employeeService.findAll();

        model.addAttribute("employees", employeeEntityList);

        return "index";
    }

    @GetMapping("/create/form")
    public String createForm(Model model)
    {
        model.addAttribute("employee", new EmployeeEntity());

        return "form";
    }

    @PostMapping("/create/process")
    public String createProcess(@ModelAttribute(name = "employee") EmployeeDTO employeeDTO, @RequestParam(name = "g-recaptcha-response") String captcha, Model model)
    {
        boolean captchaValid = recaptchaService.validateRecaptcha(captcha);

        if(captchaValid)
        {
            EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(employeeDTO.getName())
                    .lastName(employeeDTO.getLastName())
                    .birthDate(employeeDTO.getBirthDate())
                    .build();
            employeeService.createEmployee(employeeEntity);
        }else
        {
            model.addAttribute("message", "Captcha invalido");
            return "error";
        }

        return "redirect:/all";
    }
}
