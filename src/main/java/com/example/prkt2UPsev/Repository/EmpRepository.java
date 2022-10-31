package com.example.prkt2UPsev.Repository;
import com.example.prkt2UPsev.Model.Employee;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EmpRepository extends CrudRepository<Employee, Long> {
    public List<Employee> findByNameContaining(String name);
    Employee findByUsername(String username);
}