package com.hins.sp01hello.numberType;

import java.math.BigDecimal;

/**
 * @author : chenqixuan
 * @date : 2021/8/26
 */
public class BigDecimalNumber {

    public static void main(String[] args) {
        BigDecimal number = new BigDecimal(12.00);
        String result22 = compareNumber(number);
        System.out.println("======="+result22);
    }


    public static String compareNumber(BigDecimal number){
        if (!"".equals(number) && number != null){
            if (new BigDecimal(number.intValue()).compareTo(number)==0){
                //整数
                return String.valueOf(number.intValue());
            }else {
                //小数
                return String.valueOf(round(number.toString(), 2));
            }
        }
        return "";
    }

    public static double round(String value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private boolean isIntegerValue(BigDecimal bd) {
        boolean ret;

        try {
            bd.toBigIntegerExact();
            ret = true;
        } catch (ArithmeticException ex) {
            ret = false;
        }

        return ret;
    }


}
