package com.example.prkt2UPsev.Repository;
import com.example.prkt2UPsev.Model.Zoo;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ZooRepository extends CrudRepository<Zoo, Long> {
    public List<Zoo> findByNameContaining(String name);
}