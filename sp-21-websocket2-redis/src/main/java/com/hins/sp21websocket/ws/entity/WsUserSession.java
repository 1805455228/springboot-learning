package com.hins.sp21websocket.ws.entity;

import com.hins.sp21websocket.dto.CenterMemberInfoResp;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.Optional;

/**
 * Description:
 *
 * @author mpg
 * @date 2019/06/26
 */
@Setter
@NoArgsConstructor
public class WsUserSession implements Principal {

    private Long memberId;

    private String name;

    private String nickname;

    public WsUserSession(CenterMemberInfoResp.MemberBaseResp memberBaseResp) {
        this.memberId = memberBaseResp.getMemberId();
        this.name = memberBaseResp.getName();
        this.nickname = memberBaseResp.getNickname();
    }

    @Override
    public String getName() {
        return Optional.ofNullable(this.memberId).map(Object::toString).orElse(null);
    }

}
