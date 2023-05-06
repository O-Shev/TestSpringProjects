package myPackage.models;



import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-ZА-Я]([a-zA-Zа-яА-Я]{1,20}) [A-ZА-Я]([a-zA-Zа-яА-Я]{1,20}) [A-ZА-Я]([a-zA-Zа-яА-Я]{1,20})", message = "FIO is not valid, It should be [Xxxx Yyyy Zzzz] format")
    private String name;

    @Column(name = "birth")
    @NotNull(message = "Date of birth should not be empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birth;

    @Transient
    private int age;

    public Person() {
    }


    public Person(String name, Date birth) {
        this.name = name;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getAge() {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birth));
        int d2 = Integer.parseInt(formatter.format(new Date()));
        return (d2-d1)/10000;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", age=" + age +
                '}';
    }
}
