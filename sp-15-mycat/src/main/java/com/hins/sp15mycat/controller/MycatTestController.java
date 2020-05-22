package com.hins.sp15mycat.controller;

import com.hins.sp15mycat.dao.StudentDao;
import com.hins.sp15mycat.dao.UserDao;
import com.hins.sp15mycat.entity.Student;
import com.hins.sp15mycat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenqixuan
 * @version 1.0
 * @date 2020/5/21 11:39
 */
@RestController
@RequestMapping("/mycat")
public class MycatTestController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public String addData() {
        for (long i = 0; i < 30; i++) {
            User user = new User();
            user.setId(i);
            user.setName("张三" + i);
            user.setGender(i % 2 == 0 ? 0 : 1);
            userDao.save(user);

            Student student = new Student();
            student.setId(System.currentTimeMillis() + i);
            student.setName("张三学生" + i);
            student.setUserId(i);
            studentDao.save(student);
        }
        return "success";
    }

    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public void testFind() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        List<User> userList = userDao.findByNameLike("%张三2%", pageable).getContent();
        userList.forEach(System.out::println);

        Pageable pageable2 = PageRequest.of(0, 10, Sort.Direction.DESC, "userId");
        List<Student> studentList = studentDao.findByNameLike("%张三学生1%", pageable2).getContent();
        studentList.forEach(System.out::println);
    }
}
