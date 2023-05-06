package org.library.project.util;

import org.library.project.dao.PersonDao;
import org.library.project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    //для каких классов этот валидатор будет использоваться
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person personIn = (Person) o;
        Person personOut = personDao.showPerson(personIn.getName());

        if(personOut != null){
            if(personOut.getId() != personIn.getId())
                errors.rejectValue("name", "", "человек с таким ФИО уже существует");
        }
    }
}
