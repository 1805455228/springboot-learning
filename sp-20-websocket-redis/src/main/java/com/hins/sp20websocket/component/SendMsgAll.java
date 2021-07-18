package com.hins.sp20websocket.component;

/**
 * 推送全部 （群发）
 * @author : chenqixuan
 * @date : 2021/4/29
 */
public class SendMsgAll {
    /**
     * websocket业务数据(json)
     */
    private String msg;

    /**
     * 业务模块类型
     */
    private String type;

    /**
     * 项目ID
     */
    private String projectId;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
