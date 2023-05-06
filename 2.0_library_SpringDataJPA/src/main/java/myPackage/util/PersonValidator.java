package myPackage.util;

import myPackage.models.Person;
import myPackage.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Person.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> findByNamePeople = peopleRepository.findByName(person.getName()).stream().findAny();

        if(findByNamePeople.isPresent()){
            if(findByNamePeople.get().getId() != person.getId())
                errors.rejectValue("name", "", "человек с таким ФИО уже существует");
        }
    }
}
