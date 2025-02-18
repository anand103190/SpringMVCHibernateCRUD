package com.curd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.curd.model.Employee;
import com.curd.service.EmployeeService;

@Controller
public class CurdController {
	@Autowired
	private EmployeeService employeeService;
	// http://localhost:8080/SpringMVCHibernateCRUD/employees

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String listemployees(Model model) {

		model.addAttribute("employee", new Employee());
		model.addAttribute("employeeList", employeeService.listEmployees());
		return "employee";
	}

	@RequestMapping(value = "/employee/add", method = RequestMethod.POST)
	public String addemployee(@ModelAttribute("employee") Employee employee) {

		if (employee.getEmployeeId() == null || employee.getEmployeeId() == 0) {
			// new employee, add it
			employeeService.addEmployee(employee);
		} else {
			// existing employee, call update
			employeeService.updateEmployee(employee);
		}

		return "redirect:/employees";
	}

	@RequestMapping("/employee/remove/{id}")
	public String removeemployee(@PathVariable("id") int id) {

		employeeService.removeEmployee(id);
		return "redirect:/employees";
	}

	@RequestMapping("/employee/edit/{id}")
	public String editemployee(@PathVariable("id") int id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		model.addAttribute("employeeList", employeeService.listEmployees());
		return "employee";
	}
}