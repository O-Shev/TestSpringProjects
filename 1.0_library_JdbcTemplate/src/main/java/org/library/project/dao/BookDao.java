package org.library.project.dao;

import org.library.project.models.Book;
import org.library.project.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        List<Book> list =jdbcTemplate.query("SELECT * FROM books", new BookMapper());
        return list;
    }

    public List<Book> index(int id){
        List<Book> list =jdbcTemplate.query("SELECT * FROM books WHERE personid=?", new Object[]{id}, new BookMapper());
        return list;
    }

    public void createBook(Book book){
        jdbcTemplate.update("INSERT INTO books(title, author, year) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book showBook(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void updateBook(Book book){
        jdbcTemplate.update("UPDATE books SET title=?, author=?, year=? WHERE id=?", book.getTitle(), book.getAuthor(), book.getYear(), book.getId());
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }

    public void ridBook(int id){
        jdbcTemplate.update("UPDATE books SET personid=? WHERE id=?", null, id);
    }

    public void appointBook(int personId, int bookId){
        jdbcTemplate.update("UPDATE books SET personid=? WHERE id=?", personId, bookId);
    }
}
