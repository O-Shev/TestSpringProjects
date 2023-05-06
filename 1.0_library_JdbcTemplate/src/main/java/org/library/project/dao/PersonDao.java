package org.library.project.dao;

import org.library.project.models.Person;
import org.library.project.util.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        List<Person> list =jdbcTemplate.query("SELECT * FROM people", new PersonMapper());
        return list;
    }

    public void createPerson(Person person){
        jdbcTemplate.update("INSERT INTO people(name, year) VALUES (?, ?)", person.getName(), person.getYear());
    }

    public Person showPerson(int id){
        return jdbcTemplate.query("SELECT * FROM people WHERE id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }
    public Person showPerson(String name){
        return jdbcTemplate.query("SELECT * FROM people WHERE name=?", new Object[]{name}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void updatePerson(Person person){
        jdbcTemplate.update("UPDATE people SET name=?, year=? WHERE id=?", person.getName(), person.getYear(), person.getId());
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM people WHERE id=?", id);
    }
}
