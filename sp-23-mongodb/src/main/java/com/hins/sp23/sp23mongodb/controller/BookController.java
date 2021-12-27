package com.hins.sp23.sp23mongodb.controller;


import com.hins.sp23.sp23mongodb.model.Book;
import com.hins.sp23.sp23mongodb.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : chenqixuan
 * @date : 2021/12/27
 */
//@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/v1/mongodbapp")
public class BookController
{
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public ResponseEntity<List> getAllBooks(@RequestParam(required = false) String bookTitle) {
        try {
            List listOfBooks = new ArrayList<>();
            if(bookTitle == null || bookTitle.isEmpty()) {
                bookRepository.findAll().forEach(listOfBooks::add);
            }else {
                bookRepository.findByTitleContaining(bookTitle).forEach(listOfBooks::add);
            }

            if (listOfBooks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listOfBooks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBookById(@PathVariable("id") String id)
    {
        try
        {
            Optional<Book> bookOptional = bookRepository.findById(id);

            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/books")
    public ResponseEntity addABookToLibrary(@RequestBody Book book)
    {
        try
        {
            Book createdBook = bookRepository.save(new Book(book.getTitle(), book.getAuthor(),
                    book.getIsbn()));
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity updateABook(@PathVariable("id") String id, @RequestBody Book book)
    {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book updatedBook = bookOptional.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setIsbn(book.getIsbn());
            return new ResponseEntity<>(bookRepository.save(updatedBook), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteABook(@PathVariable("id") String id)
    {
        try
        {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}