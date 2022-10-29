package com.example.prkt2UPsev.Controller;
import com.example.prkt2UPsev.Model.Employee;
import com.example.prkt2UPsev.Repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String employeeAddView(Model model){
        return "employee/add";
    }

    @PostMapping("/add")
    public String employeeAdd(@RequestParam String name,
                              @RequestParam Integer age,
                              @RequestParam String post,
                              @RequestParam String animal,
                              @RequestParam String timetable, Model model){
        Employee employee = new Employee(name, age, post, animal, timetable);
        empRepository.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/employee/filter")
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

    @GetMapping("/edit/{id}")
    public String employeeEdit(Model model,
                               @PathVariable long id) {

        Employee employee = empRepository.findById(id).orElseThrow();
        model.addAttribute("editPeople", employee);
        return("/employee/edit");
    }

    @PostMapping("/edit/{id}")
    public String employeeEdit(@PathVariable long id,
                               @RequestParam String name,
                               @RequestParam Integer age,
                               @RequestParam String post,
                               @RequestParam String animal,
                               @RequestParam String timetable) {

        Employee employee = empRepository.findById(id).orElseThrow();
        employee.setName(name);
        employee.setAge(age);
        employee.setPost(post);
        employee.setAnimal(animal);
        employee.setTimetable(timetable);

        empRepository.save(employee);

        return("redirect:/employee/details/" + employee.getId());
    }

    @GetMapping("/delete/{id}")
    public String employeeDelete(@PathVariable long id) {
        empRepository.deleteById(id);
        return("redirect:/employee");
    }
}