package com.hins.sp16websocket.entity;

import lombok.Data;

/**
 * 联系人
 * @author qixuan.chen
 * @date 2020-03-16 17:48
 */
@Data
public class Recontact {

    private String userId;//用户ID

    private String username;//用户名称

    private String recontactId;//联系人ID（好友ID）

    private String recontactName;//联系人名称（好友名称）

    private String tags;//所属分组标签
}
