package com.hins.sp01hello.fileio;

/**
 * @author qixuan.chen
 * @date 2022/7/13 19:13
 */
public class FileTest {


    public static void main(String[] args) {

        String filename = "80_1081_2469c3544ada4a4a24521380abd4b1a536ef068a.jpg?mac=CMzEJ%2Fw5CalTDy3OMpaWA2YMOeUFxFYnLqraeNaqPf8%3D&expiry=1657585659288.4";
        if(filename.indexOf("?") > 1){
            String[] arr = filename.split("\\?");
            filename = arr[0];
            System.out.println(filename);
        }
    }
}
