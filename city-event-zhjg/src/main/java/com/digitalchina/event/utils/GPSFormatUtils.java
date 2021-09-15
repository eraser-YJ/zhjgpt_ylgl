package com.digitalchina.event.utils;

/**
 * 经纬度格式转化
 * @author lzy
 * @since 2019/9/12
 */
public class GPSFormatUtils {
    /**
     * 功能：  度-->度分秒
     * @param d   传入待转化格式的经度或者纬度
     * @return
     */
    public static String DDtoDMS(String d){
        String[] array=d.split("[.]");
        String D=array[0];//得到度

        Double m=Double.parseDouble("0."+array[1])*60;
        String[] array1=m.toString().split("[.]");
        String M=array1[0];//得到分

/*        Double s=Double.parseDouble("0."+array1[1])*60*10000;
        String[] array2=s.toString().split("[.]");
        String S=array2[0];//得到秒*/
        return  D+"°"+M+"′";
    }
}
