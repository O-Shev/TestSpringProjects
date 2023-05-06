package myPackage.controllers;

import myPackage.models.Book;
import myPackage.models.Person;
import myPackage.services.BookService;
import myPackage.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("books", bookService.index(request));
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bookService.createBook(book, bindingResult)){
            return "redirect:/books";
        }
        else return "books/create";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable int id, Model model){
        model.addAttribute("book", bookService.showBook(id));
        model.addAttribute("people", personService.index());
        model.addAttribute("person", new Person());

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.showBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bookService.updateBook(book, bindingResult)){
            return "redirect:/books/{id}";
        }
        else return "books/edit";
    }

    @PatchMapping("/{id}/rid")
    public String releaseBook(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookService.assign(person, id);
        return "redirect:/books/{id}";
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("book", new Book());
        return "books/search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("book") Book book, Model model){
        model.addAttribute("books", bookService.search(book));
        return "books/search";
    }
}
