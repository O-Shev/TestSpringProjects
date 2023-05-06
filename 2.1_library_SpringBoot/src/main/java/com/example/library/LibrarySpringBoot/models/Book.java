package com.example.library.LibrarySpringBoot.models;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 50, message = "Title should be between 2 to 50 characters")
    @Pattern(regexp = "[A-ZА-Я] ?([a-zA-Zа-яА-Я]{1,20},? ?)*", message = "Not valid title: Название должно начинаться с большой буквы и не содержать больше одного пробела между словами")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author should not be empty")
    @Pattern(regexp = "[A-ZА-Я]([a-zA-Zа-яА-Я]{0,20}) ?([A-ZА-Я]([a-zA-Zа-яА-Я]{0,20}))?", message = "Author name is not valid: Имя автора должно состоять из двух слов с большой буквы")
    private String author;

    @Column(name = "year")
    @NotNull(message = "Year should not be empty")
    @Range(min = -1000, max = 2023, message = "Year should be in range -1000 to 2023")
    private int year;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date takenAt;

    @Transient
    private boolean isOverdue;

    public Book() {
    }

    public Book(Person owner, String title, String author, int year) {
        this.owner = owner;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
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

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isOverdue() {
        Date date = new Date();
        int rentalTime = 60000;
        if(getTakenAt()!=null)
            return (date.getTime() - getTakenAt().getTime()) >= rentalTime;
        else return false;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
