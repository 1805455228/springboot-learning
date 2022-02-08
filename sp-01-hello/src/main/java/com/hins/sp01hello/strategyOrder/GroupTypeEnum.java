package com.hins.sp01hello.strategyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 拼单类型 0-好友拼单 1-正常订单 2-两人团  3-三人团  4-四人团
 * @author subin
 */
@Getter
@AllArgsConstructor
public enum GroupTypeEnum {

    /**
     * 好友拼单
     */
    GROUP_FRIEND(0, "好友拼单",0,0, 0),
    /**
     * 正常订单
     */
    NORMAL(1, "正常订单", 0,0, 0),

    /**
     * 两人团
     */
    GROUP_TWO(2, "两人团", 1,1.5, 2),
    /**
     * 三人团
     */
    GROUP_THREE(3, "三人团",2, 2.5, 3),

    /**
     * 四人团
     */
    GROUP_FOUR(4, "四人团",2, 3.5, 5),
    ;

    private final int value;
    private final String description;
    private final int progcessTimes;
    private final double normalCheckTimes;
    private final double robotCheckTimes;

    private final static Map<Integer, GroupTypeEnum> KEY_MAP = new HashMap<>(values().length);

    static {
        for (GroupTypeEnum value : values()) {
            KEY_MAP.put(value.getValue(), value);
        }
    }

    public static GroupTypeEnum getType(int value) {
        return KEY_MAP.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(int value) {
        for (GroupTypeEnum cancelTypeEnum : values()) {
            if (cancelTypeEnum.getValue()==value) {
                return cancelTypeEnum.getDescription();
            }
        }
        return null;
    }



}
