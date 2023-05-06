package org.library.project.models;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    private int personID;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 50, message = "Title should be between 2 to 50 characters")
    @Pattern(regexp = "[A-ZА-Я] ?([a-zA-Zа-яА-Я]{1,20},? ?)*", message = "Not valid title: Название должно начинаться с большой буквы и не содержать больше одного пробела между словами")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Pattern(regexp = "[A-ZА-Я]([a-zA-Zа-яА-Я]{0,20}) ?([A-ZА-Я]([a-zA-Zа-яА-Я]{0,20}))?", message = "Author name is not valid: Имя автора должно состоять из двух слов с большой буквы")
    private String author;

    @NotNull(message = "Year should not be empty")
    @Range(min = -1000, max = 2023, message = "Year should be in range -1000 to 2023")
    private int year;

    public Book() {
    }

    public Book(int id, int personID, String title, String author, int year) {
        this.id = id;
        this.personID = personID;
        this.title = title;
        this.author = author;
        this.year = year;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }
}
