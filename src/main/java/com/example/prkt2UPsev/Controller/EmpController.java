package com.example.prkt2UPsev.Controller;
import com.example.prkt2UPsev.Model.Employee;
import com.example.prkt2UPsev.Model.Role;
import com.example.prkt2UPsev.Repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/employee")
public class EmpController {
    @Autowired
    EmpRepository empRepository;
    @GetMapping("")
    public String employeeMain(Model model){
        Iterable<Employee> listEmployee = empRepository.findAll();
        model.addAttribute("listPeople", listEmployee);
        return "employee/index";
    }

    @GetMapping("/add")
    public String employeeAddView(Employee employee, Model model){
        Iterable<Role> roles = List.of(Role.values());
        model.addAttribute("roleName", roles);
        return "/employee/add";
    }

    @PostMapping("/add")
    public String employeeAdd(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()) return "employee/add";
        employee.setActive(true);
        empRepository.save(employee);
        return "redirect:/employee";
    }
    @GetMapping("/edit/{id}")
    public String employeeEdit(Model model,
                               @PathVariable long id) {

        Employee employee = empRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        Iterable<Role> roles = List.of(Role.values());
        model.addAttribute("roleName", roles);
        return("/employee/edit");
    }

    @PostMapping("/edit/{id}")
    public String employeeEdit(@Valid Employee employee, BindingResult result) {
        if(result.hasErrors()) return "/employee/edit";

        employee.setActive(true);
        empRepository.save(employee);

        return("redirect:/employee/details/" + employee.getId());
    }

    @GetMapping("/filter")
    public String employeeFilter(@RequestParam String searchName,
                                 Model model){
        List<Employee> employee = empRepository.findByNameContaining(searchName);
        model.addAttribute("listPeople", employee);
        return "employee/index";
    }
    @GetMapping("/details/{id}")
    public String employeeDetails(Model model,
                                  @PathVariable long id) {
        Employee employee = empRepository.findById(id).orElseThrow();
        model.addAttribute("people", employee);
        return ("/employee/details");
    }

    @GetMapping("/delete/{id}")
    public String employeeDelete(@PathVariable long id) {
        Employee employee = empRepository.findById(id).orElseThrow();
        employee.setActive(false);
        empRepository.save(employee);
        return("redirect:/employee");
    }
}