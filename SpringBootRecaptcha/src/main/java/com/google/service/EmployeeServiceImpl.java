package com.google.service;

import com.google.entities.EmployeeEntity;
import com.google.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }
    @Override
    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }
    @Override
    public void createEmployee(EmployeeEntity employeeEntity) {
        employeeRepository.save(employeeEntity);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}