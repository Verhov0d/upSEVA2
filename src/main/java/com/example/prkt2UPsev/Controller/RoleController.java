package com.example.prkt2UPsev.Controller;
import com.example.prkt2UPsev.Model.Employee;
import com.example.prkt2UPsev.Model.Role;
import com.example.prkt2UPsev.Repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ROLs")
@PreAuthorize("hasAuthority('ROLE_BIG_BONE')")
public class RoleController {
    @Autowired
    EmpRepository employeeRepository;

    @GetMapping("")
    public String roleMain(Model model){
        Iterable<Employee> listEmployee = employeeRepository.findAll();
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        for (Employee emp:
                listEmployee){
            if (emp.getActive()== true){
                employeeArrayList.add(emp);
            }
        }
        model.addAttribute("listPeople", employeeArrayList);
        return "role/index";
    }

    @GetMapping("/edit/{id}")
    public String roleEdit(Model model,
                           @PathVariable long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        Iterable<Role> roles = List.of(Role.values());
        model.addAttribute("roleName", roles);

        return("/role/edit");
    }

    @PostMapping("/edit/{id}")
    public String roleEdit(@RequestParam Long id,
                           @RequestParam String[] roles,
                           Model model) {

        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.getRoles().clear();
        for(String role: roles){
            employee.getRoles().add(Role.valueOf(role));
        }
        employeeRepository.save(employee);

        return("redirect:/ROLs");
    }
    @GetMapping("/filter")
    public String roleFilter(@RequestParam String searchName,
                             Model model){
        List<Employee> employee =employeeRepository.findByNameContaining(searchName);
        model.addAttribute("listPeople", employee);
        return "role/index";
    }
}