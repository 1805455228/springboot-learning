package com.hins.sp01hello.strategyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * 拼单方式0-自取 1-外卖
 * @author subin
 */
public enum GroupWayEnum {

    /**
     * 自取
     */
    TAKE_ONESELF(0, "自取"),
    /**
     * 外卖
     */
    TAKE_OUT(1, "外卖"),
    ;

    private int value;
    private String description;

    GroupWayEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }


    private final static Map<Integer, GroupWayEnum> KEY_MAP = new HashMap<>(values().length);

    static {
        for (GroupWayEnum value : values()) {
            KEY_MAP.put(value.getValue(), value);
        }
    }

    public static GroupWayEnum getType(int value) {
        return KEY_MAP.get(value);
    }

    public static String getDescription(int value) {
        for (GroupWayEnum cancelTypeEnum : values()) {
            if (cancelTypeEnum.getValue()==value) {
                return cancelTypeEnum.getDescription();
            }
        }
        return null;
    }
}
