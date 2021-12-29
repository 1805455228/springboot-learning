package com.hins.sp23.sp23mongodb.repositories;

import com.hins.sp23.sp23mongodb.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author : chenqixuan
 * @date : 2021/12/29
 */


public interface CommentRepository extends MongoRepository<Comment,String > {


    Page<Comment> findByParentid(String parentid,Pageable pageable);

}