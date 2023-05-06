package org.library.project.controllers;


import org.library.project.dao.BookDao;
import org.library.project.dao.PersonDao;
import org.library.project.models.Book;
import org.library.project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDao bookDao;
    private PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/create";
        bookDao.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable int id, Model model){
        Book book = bookDao.showBook(id);
        model.addAttribute("book", book);
        List<Person> list;
        boolean belong = false;
        if(book.getPersonID() != 0) {
            list = new ArrayList<>();
            list.add(personDao.showPerson(book.getPersonID()));
            belong = true;
        }
        else list = personDao.index();
        model.addAttribute("people", list);
        model.addAttribute("belong", belong);
        model.addAttribute("person", new Person());
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.showBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookDao.updateBook(book);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/rid")
    public String ridBook(@PathVariable("id") int id){
        bookDao.ridBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/appoint")
    public String appointBook(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDao.appointBook(person.getId(), id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDao.deleteBook(id);
        return "redirect:/books";
    }

}
