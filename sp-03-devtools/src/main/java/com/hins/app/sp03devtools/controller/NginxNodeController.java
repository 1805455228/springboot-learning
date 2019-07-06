package com.hins.app.sp03devtools.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author qixuan.chen
 * @date 2019-07-05 10:04
 *
 * 热部署测试
 */

@RestController
public class NginxNodeController {


    @RequestMapping(value = "/node")
    public Map<String, Object> getNode(HttpServletRequest request){
        System.out.println("nginx负载均衡.............3333");
        String node = "nginx负载均衡"+"....3333";
        Map sessionIdPortMap = new HashMap();
        // 获取session信息
        sessionIdPortMap.put("sessionId：", request.getSession().getId());
        sessionIdPortMap.put("服务器端口：", request.getServerPort());
        sessionIdPortMap.put("node",node);
        return sessionIdPortMap;
    }

}
