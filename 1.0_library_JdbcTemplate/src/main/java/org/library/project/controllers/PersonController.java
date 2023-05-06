package org.library.project.controllers;

import org.library.project.dao.BookDao;
import org.library.project.dao.PersonDao;
import org.library.project.models.Person;
import org.library.project.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonDao personDao;
    private BookDao bookDao;
    private PersonValidator personValidator;


    @Autowired
    public PersonController(PersonDao personDao, BookDao bookDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.bookDao = bookDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDao.index());
        return "people/index";
    }


    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/create";

        personDao.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.showPerson(id));
        model.addAttribute("books", bookDao.index(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.showPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,  @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/edit";

        personDao.updatePerson(person);

        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDao.deletePerson(id);
        return "redirect:/people";
    }

}
