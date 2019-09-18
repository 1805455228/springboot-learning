package com.hins.sp14elasticsearch;

import com.hins.sp14elasticsearch.controller.BookController;
import com.hins.sp14elasticsearch.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sp14ElasticsearchApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private BookController bookController;

    @Test
    public void getOne() {
        System.out.println(bookController.getById(1001).toString());
    }

    @Test
    public void getAll() {
        List<Book> res = bookController.getAll();
        res.forEach(System.out::println);
    }

    @Test
    public void search() {
        String userId = "uxcvf";
        String content = "探险家";
        List<Book> res = bookController.searchByUserIdAndName(userId,content);
        res.forEach(System.out::println);
    }



    @Test
    public void addOneTest() {
        Book book = new Book();
        book.setId(1001);
        book.setAuthor("倪匡");
        book.setBookName("卫斯理");
        book.setUserId("uxcvf");
        book.setPrice(54);
        book.setPublish("Y");
        book.setContent("上海某大酒店举行古埃及三千年古物展览，不少名人及考古专家赴会，父亲为探险家卫博士的冒险家卫斯理，受邀成为嘉宾，主办方为巨富之女童颖儿。卫斯理突然见到一个不起眼的六角型小盒子也在展出品当中，脑海中闪过二十年前，与父母在埃及偏远地区遇险的可怕经历，他记起这小盒子接触到阳光在某个角度的照射，就会伸展成太阳状。突然有蒙面匪徒多人冲入，放催眠气体迷到大部份宾客，幸卫斯理用古法解迷烟，与公安部警探好友陈阳力敌众匪徒，匪首打碎玻璃柜抢去古盒就逃，卫斯理穷追。二人追入隔壁商场，卫斯理迟一步被匪首先进电梯关上门，只好在七楼跳到中庭的圣诞树惊险地溜下，赶上匪首在地面一层出来，二人激战，再打入电梯内上升，卫终把匪首一脚踢到压碎玻璃墙自高处堕下跌死，但夺回古盒子");

        Book book2 = new Book();
        book2.setId(1002);
        book2.setAuthor("刘慈欣");
        book2.setBookName("三体");
        book2.setUserId("uxcvf");
        book2.setPrice(54);
        book2.setPublish("Y");
        book2.setContent("在地球人类接近灭亡之际，只有程心和艾AA两个幸存者乘坐光速飞船离开。罗辑成为设置于冥王星的地球文明博物馆的“守墓人”，她们在冥王星带走人类文明的精华。在云天明送的恒星的一颗行星上，程心遇到关一帆且探讨了宇宙降维的真相，然而超乎一切之上的力量要求宇宙归零重生，在黑域中穿越长达1800万年的时空……程心没有等到云天明到来，和关一帆在小宇宙中短暂居住后重新进入大宇宙生活");


        bookController.putOne(book);
        bookController.putOne(book2);
    }

    @Test
    public void addBatchTest() {
        List<Book> list = new ArrayList<>();
        Book book = new Book();
        book.setId(1001);
        book.setAuthor("倪匡");
        book.setBookName("卫斯理");
        book.setUserId("uxcvf");
        book.setPrice(54);
        book.setPublish("Y");
        book.setContent("上海某大酒店举行古埃及三千年古物展览，不少名人及考古专家赴会，父亲为探险家卫博士的冒险家卫斯理，受邀成为嘉宾，主办方为巨富之女童颖儿。卫斯理突然见到一个不起眼的六角型小盒子也在展出品当中，脑海中闪过二十年前，与父母在埃及偏远地区遇险的可怕经历，他记起这小盒子接触到阳光在某个角度的照射，就会伸展成太阳状。突然有蒙面匪徒多人冲入，放催眠气体迷到大部份宾客，幸卫斯理用古法解迷烟，与公安部警探好友陈阳力敌众匪徒，匪首打碎玻璃柜抢去古盒就逃，卫斯理穷追。二人追入隔壁商场，卫斯理迟一步被匪首先进电梯关上门，只好在七楼跳到中庭的圣诞树惊险地溜下，赶上匪首在地面一层出来，二人激战，再打入电梯内上升，卫终把匪首一脚踢到压碎玻璃墙自高处堕下跌死，但夺回古盒子");

        list.add(book);

        Book book2 = new Book();
        book2.setId(1002);
        book2.setAuthor("刘慈欣");
        book2.setBookName("三体");
        book2.setUserId("uxcvf");
        book2.setPrice(54);
        book2.setPublish("Y");
        book2.setContent("在地球人类接近灭亡之际，只有程心和艾AA两个幸存者乘坐光速飞船离开。罗辑成为设置于冥王星的地球文明博物馆的“守墓人”，她们在冥王星带走人类文明的精华。在云天明送的恒星的一颗行星上，程心遇到关一帆且探讨了宇宙降维的真相，然而超乎一切之上的力量要求宇宙归零重生，在黑域中穿越长达1800万年的时空……程心没有等到云天明到来，和关一帆在小宇宙中短暂居住后重新进入大宇宙生活");
        list.add(book2);
        bookController.putList(list);
    }

    @Test
    public void deleteBatch() {
        List<Integer> list = new ArrayList<>();
        list.add(1001);
        list.add(1002);
        bookController.deleteBatch(list);
    }

    @Test
    public void deleteByQuery() {
        bookController.deleteByUserId("uxcvf");
    }


}
