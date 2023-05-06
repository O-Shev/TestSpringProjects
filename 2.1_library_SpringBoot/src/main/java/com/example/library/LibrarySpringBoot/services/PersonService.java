package com.example.library.LibrarySpringBoot.services;



import com.example.library.LibrarySpringBoot.models.Person;
import com.example.library.LibrarySpringBoot.repositories.PeopleRepository;
import com.example.library.LibrarySpringBoot.util.PersonValidator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PeopleRepository peopleRepository;
    private final PersonValidator personValidator;

    @Autowired
    public PersonService(PeopleRepository peopleRepository, PersonValidator personValidator) {
        this.peopleRepository = peopleRepository;
        this.personValidator = personValidator;
    }

    public List<Person> index(){
        return peopleRepository.findAll();
    }

    @Transactional
    public boolean createPerson(Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(!bindingResult.hasErrors()){
            peopleRepository.save(person);
            return true;
        }
        else return false;
    }

    public Person showPerson(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        Hibernate.initialize(person.getBooks()); // We have Lazy load by default, so we need to load books in this transaction
        return person;
    }

    @Transactional
    public boolean updatePerson(Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(!bindingResult.hasErrors()){
            peopleRepository.save(person);
            return true;
        }
        else return false;
    }

    @Transactional
    public void deletePerson(int id){
        peopleRepository.deleteById(id);
    }

}
