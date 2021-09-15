package com.digitalchina.admin.utils;
/**
 * 经纬度转换工具类
 * gcj转wsg
 */
public class Gcj2WsgUtil {
    private static final double PI = 3.14159265358979324;//圆周率
    private static final double a = 6378245.0;//克拉索夫斯基椭球参数长半轴a
    private static final double ee = 0.00669342162296594323;//克拉索夫斯基椭球参数第一偏心率平方

    //输入GCJ经纬度 转WGS纬度
    public static double[] transform(double[] gcj) {

        double lat = gcj[0];
        double lon = gcj[1];

        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);

        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);

        return new double[]{lat - dLat, lon - dLon};
    }

    //转换经度所需
    private static double transformLon(double x, double y) {
        double PI = 3.14159265358979324;//圆周率
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    //转换纬度所需
    private static double transformLat(double x, double y) {
        double PI = 3.14159265358979324;//圆周率
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static void main(String[] args) {
        double[] gcj = new double[]{43.79111453286204, 125.25563840021519};
        double[] wsg = transform(gcj);
        System.out.println(wsg[0]);
        System.out.println(wsg[1]);
    }
}
