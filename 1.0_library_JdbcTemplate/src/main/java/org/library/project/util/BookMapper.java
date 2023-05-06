package org.library.project.util;

import org.library.project.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setPersonID(resultSet.getInt("personid"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYear(resultSet.getInt("year"));

        return book;
    }
}
