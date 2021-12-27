package com.hins.sp23.sp23mongodb.repositories;


import com.hins.sp23.sp23mongodb.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author : chenqixuan
 * @date : 2021/12/27
 */
public interface BookRepository extends MongoRepository<Book, String> {

    List findByTitleContaining(String title);

    List findByAuthor(String name);
}