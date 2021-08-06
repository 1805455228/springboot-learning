package com.hins.sp21websocket.service;

import com.hins.sp21websocket.dto.CenterMemberInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mpg
 * @since 2021/6/28
 */
@Slf4j
@Service
public class CenterMemberService {

    //@Value("${center-cloud.tenantId}")
    private String tenantId = "PINTEA001";

    //@Value("${center-cloud.host}")
    private String host = "localhost";

    //@Value("${center-cloud.member.memberInfo}")
    private String memberInfoUrl = "/user/getUserInfoByToken";

    @Resource
    private RestTemplate restTemplate;


    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public CenterMemberInfoResp.MemberBaseResp getMemberInfo(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String url = host + "/" + memberInfoUrl;
        HttpHeaders headers = getHttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        log.info("会员中台获取会员信息URL: {}", url);
        log.info("会员中台获取会员信息请求参数token:{}", token);
        ResponseEntity<CenterMemberInfoResp> responseEntity = null;
        try {
//            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, CenterMemberInfoResp.class, entity);
//            CenterMemberInfoResp body = responseEntity.getBody();
//            log.info("会员中台获取会员信息响应参数: {}", body);
//            if (!HttpStatus.OK.equals(responseEntity.getStatusCode()) || null == body) {
//                log.warn("获取会员信息失败");
//                return null;
//            }
//            if (body.getCode() != HttpStatus.OK.value()) {
//                log.warn(Optional.ofNullable(body.getMessage()).orElse("获取会员信息失败"));
//                return null;
//            }

            //模拟登录测试
            CenterMemberInfoResp body = new CenterMemberInfoResp();
            CenterMemberInfoResp.MemberBaseResp userInfo = new CenterMemberInfoResp.MemberBaseResp();
            userInfo.setMemberId(10028l);
            userInfo.setName("username001");
            userInfo.setNickname("名称002");
            body.setData(userInfo);
            return body.getData();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json;charset=UTF-8");
        headers.set("trace_id", UUID.randomUUID().toString());
        headers.set("tenant", tenantId);
        return headers;
    }

}
