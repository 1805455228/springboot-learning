package com.hins.sp21websocket.ws.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.nio.charset.StandardCharsets;

/**
 * @author mpg
 * @date 2021/6/24
 */
@Slf4j
public class FastJsonMessageConverter extends AbstractMessageConverter {


    public FastJsonMessageConverter() {
        super(new MimeType("application", "json", StandardCharsets.UTF_8));
    }


    @Override
    protected boolean supports(Class<?> clazz) {
        // should not be called, since we override canConvertFrom/canConvertTo instead
        throw new UnsupportedOperationException();
    }


    @Override
    protected boolean canConvertFrom(Message<?> message, Class<?> targetClass) {
        String payload = WsUtil.getPayload(message);
        return JSONObject.isValidObject(payload);
    }

    @Override
    protected boolean canConvertTo(Object payload, MessageHeaders headers) {

        return true;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        try {
            String payload = WsUtil.getPayload(message);
            return JSONObject.parseObject(payload, targetClass);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        try {
            String jsonString = JSONObject.toJSONString(payload);
            return jsonString.getBytes();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
