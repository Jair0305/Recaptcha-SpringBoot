package com.google.controller;

import com.google.controller.dto.EmployeeDTO;
import com.google.entities.EmployeeEntity;
import com.google.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller//para imbc
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

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
    public String createProcess(EmployeeDTO employeeDTO)
    {
       EmployeeEntity employeeEntity = EmployeeEntity.builder()
               .name(employeeDTO.getName())
               .lastName(employeeDTO.getLastName())
               .birthDate(employeeDTO.getBirthDate())
               .build();
        employeeService.createEmployee(employeeEntity);

        return "redirect:/all";
    }
}
