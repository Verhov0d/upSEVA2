package com.example.prkt2UPsev.Controller;
import com.example.prkt2UPsev.Model.Zoo;
import com.example.prkt2UPsev.Repository.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/zoo")
public class ZooController {
    @Autowired
    ZooRepository zooRepository;

    @GetMapping("")
    public String zooMain(Model model) {
        Iterable<Zoo> listZoo = zooRepository.findAll();
        model.addAttribute("listZoo", listZoo);
        return "zoo/index";
    }

    @GetMapping("/add")
    public String zooAddView(Zoo zoo) {
        return "zoo/add";
    }

    @PostMapping("/add")
    public String zooAdd(@Valid Zoo zoo, BindingResult result) {
        if(result.hasErrors()) return "/zoo/add";
        zooRepository.save(zoo);
        return "redirect:/zoo";
    }
    @GetMapping("/edit/{id}")
    public String zooEdit(Model model,
                          @PathVariable long id) {
        Zoo zoo = zooRepository.findById(id).orElseThrow();
        model.addAttribute("zoo", zoo);
        return("/zoo/edit");
    }

    @PostMapping("/edit/{id}")
    public String zooEdit(@Valid Zoo zoo, BindingResult result) {
        if(result.hasErrors()) return "/zoo/edit";
        zooRepository.save(zoo);
        return("redirect:/zoo/details/" + zoo.getId());
    }

    @GetMapping("/filter")
    public String zooFilter(@RequestParam String searchName,
                            Model model){
        List<Zoo> zoo = zooRepository.findByNameContaining(searchName);
        model.addAttribute("listZoo", zoo);
        return "zoo/index";
    }

    @GetMapping("/details/{id}")
    public String zooDetails(Model model,
                             @PathVariable long id) {
        Zoo zoo = zooRepository.findById(id).orElseThrow();
        model.addAttribute("animal", zoo);
        return ("/zoo/details");
    }
    @GetMapping("/delete/{id}")
    public String zooDelete(@PathVariable long id) {
        zooRepository.deleteById(id);
        return("redirect:/zoo");
    }
}