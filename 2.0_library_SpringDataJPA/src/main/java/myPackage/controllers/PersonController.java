package myPackage.controllers;


import myPackage.models.Person;
import myPackage.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personService.index());
        return "people/index";
    }


    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(personService.createPerson(person, bindingResult)){
            return "redirect:/people";
        }
        else return "people/create";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        Person person = personService.showPerson(id);
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.showPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,  @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(personService.updatePerson(person, bindingResult)){
            return "redirect:/people/{id}";
        }
        else return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/people";
    }


}
