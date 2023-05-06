package org.library.project.models;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-ZА-Я]([a-zA-Zа-яА-Я]{1,20}) [A-ZА-Я]([a-zA-Zа-яА-Я]{1,20}) [A-ZА-Я]([a-zA-Zа-яА-Я]{1,20})", message = "FIO is not valid")
    private String name;

    @NotNull(message = "age should not be empty")
    @Range(min = 1801, max = 2023, message = "Year of birth should be in range 1801 to 2023")
    private int year;

    public Person() {
    }

    public Person(String name, int year, int id) {
        this.name = name;
        this.year = year;
        this.id =id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
