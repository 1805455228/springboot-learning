package com.hins.sp20websocket.entity;

import lombok.Data;

import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-07-07 13:36
 */

@Data
public class User {

    private String id;

    private String username;

    private String password;

    private List<ChatGroup> chatGroupList;//关联的聊天群

    private List<Recontact> recontactList;//联系人，好友列表

}
