package com.hins.sp15mycat.dao;

import com.hins.sp15mycat.entity.Student;
import com.hins.sp15mycat.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenqixuan
 * @version 1.0
 * @date 2020/5/21 11:37
 */
public interface StudentDao extends JpaRepository<Student, Long> {

    Page<Student> findByNameLike(String name, Pageable pageable);

}
