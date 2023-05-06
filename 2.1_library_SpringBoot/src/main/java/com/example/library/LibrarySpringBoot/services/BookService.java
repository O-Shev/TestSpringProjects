package com.example.library.LibrarySpringBoot.services;


import com.example.library.LibrarySpringBoot.models.Book;
import com.example.library.LibrarySpringBoot.models.Person;
import com.example.library.LibrarySpringBoot.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> index(HttpServletRequest request){
        int page;
        int books_per_page;
        boolean sort_by_year;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            books_per_page = Integer.parseInt(request.getParameter("books_per_page"));
            sort_by_year = Boolean.parseBoolean(request.getParameter("sort_by_year"));
            if(sort_by_year) return booksRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
            else return booksRepository.findAll(PageRequest.of(page, books_per_page)).getContent();

        } catch (Exception e){
            System.out.println(e);
            return booksRepository.findAll();
        }
    }

    @Transactional
    public boolean createBook(Book book, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            booksRepository.save(book);
            return true;
        }
        else return false;
    }

    public Book showBook(int id){
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean updateBook(Book book, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            booksRepository.save(book);
            return true;
        }
        else return false;
    }

    @Transactional
    public void deleteBook(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(Person person, int bookId){
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setOwner(person);
        book.setTakenAt(new Date());
    }

    @Transactional
    public void release(int id){
        Book book = booksRepository.findById(id).orElse(null);
        book.setOwner(null);
    }

    public List<Book> search(Book book){
        if(!book.getTitle().equals("")) return booksRepository.findByTitleStartingWith(book.getTitle());
        else return null;
    }
}
