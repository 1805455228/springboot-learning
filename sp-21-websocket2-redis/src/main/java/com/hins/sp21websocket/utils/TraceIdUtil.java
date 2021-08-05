package com.hins.sp21websocket.utils;

import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

/**
 * @author mpg
 * @since 2021/6/21
 */
public class TraceIdUtil {

    public static String getTraceId() {
        return Optional.ofNullable(MDC.get(TraceConstants.TRACE_ID)).orElse(UUID.randomUUID().toString());
    }

    public static void setTraceId() {
        MDC.put(TraceConstants.TRACE_ID, getTraceId());
    }

}
