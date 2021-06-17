package com.hins.sp20websocket.controller;

import com.hins.sp20websocket.entity.ChatGroup;
import com.hins.sp20websocket.entity.Recontact;
import com.hins.sp20websocket.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qixuan.chen
 * @date 2020-03-11 18:54
 */
@Slf4j
@Controller
public class ChatController {

    public static Map<String,Object> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for(int i=0;i<10;i++){
            User user = new User();
            //String uuid = UUID.randomUUID().toString();
            String uuid = "chat100"+i;
            user.setId(uuid);
            user.setUsername("hins"+i);
            user.setPassword("123456");
            List<Recontact> recontactList = new ArrayList<>();
            if(uuid.equals("chat1001")){
                Recontact recontact = new Recontact();
                recontact.setUserId(user.getId());
                recontact.setUsername(user.getUsername());
                recontact.setRecontactId("chat1004");
                recontact.setRecontactName("hins4");
                recontactList.add(recontact);
            }else if(uuid.equals("chat1004")){
                Recontact recontact = new Recontact();
                recontact.setUserId(user.getId());
                recontact.setUsername(user.getUsername());
                recontact.setRecontactId("chat1001");
                recontact.setRecontactName("hins1");
                recontactList.add(recontact);

            }

            List<ChatGroup> list = new ArrayList();
            ChatGroup chatGroup = new ChatGroup();
            String groupId = "group1001";
            chatGroup.setGroupId(groupId);
            chatGroup.setGroupName("好友群x1");
            list.add(chatGroup);

            user.setRecontactList(recontactList);
            user.setChatGroupList(list);
            map.put(uuid,user);
        }

    }


    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){

        return "/index";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String login(){

        return "/chat/login";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("/user/login")
    @ResponseBody
    public WebResult loginPost(@RequestParam String username,@RequestParam String password){
        if(null != username && null != password){
            for (String s : map.keySet()) {
                User user = (User)map.get(s);
                if(username.equals(user.getUsername())){
                    if(password.equals(user.getPassword())){//认证登录跳转
                        return WebResult.success(user);
                    }
                }
            }
        }

        return WebResult.error();
    }

    /**
     * 用户信息
     * @return
     */
    @RequestMapping("/user/info")
    @ResponseBody
    public WebResult userInfo(@RequestParam String userId){
        if(null != userId){
            return WebResult.success(map.get(userId));
        }

        return WebResult.error();
    }

    /**
     * 所有用户信息
     * @return
     */
    @RequestMapping("/user/allUserIds")
    @ResponseBody
    public WebResult allUser(){
        List<Object> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(map.get(key));
        }
        return WebResult.success(list);
    }




    /**
     * 退出
     * @return
     */
    @RequestMapping("/user/logout")
    @ResponseBody
    public WebResult logoutPost(){
        return WebResult.success();
    }

    /**
     * 聊天室
     * @return
     */
    @RequestMapping("/chat")
    public String chatPage(){

        return "/chat/chat";
    }






}
