package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/ZhjgAQI")
@Api(tags = "获取天气信息")
public class ZhjgAQIController {


    @Autowired
    private RestTemplate rt;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 获取天气信息--实时天气
     *
     * @return
     */
    @GetMapping("/selectWeather")
    @ApiOperation("获取天气信息")
    public RespMsg<Map<String, String>> selectWeather() {
        //JsonNode resultData = rt.getForObject("https://www.heweather.com/v1/current/condition/changchun-101060101.html", JsonNode.class);//此接口已经作废
        //JsonNode resultData1 = rt.getForObject("http://10.0.251.72:8181/service/ccxq_aqi", JsonNode.class);
        JsonNode resultData = rt.getForObject("http://192.168.0.6:8000/v7/weather/now?location=CN101060101&key=3741d0cf8f094828b250fb514f45787b", JsonNode.class);
        JsonNode resultData1 = rt.getForObject("http://172.16.10.15:8181/service/ccxq_aqi", JsonNode.class); //和风天气接口取消了aqi，从大数据空气质量库中取值aqi,接口是成都大数据平台发布，服务名称ccxq_aqi
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> map = new HashedMap();
        Date date = new Date();
        map.put("name", "长春");//城市名称

        if (resultData.get("now") != null) {
            map.put("txt", resultData.get("now").get("text").textValue());//天气情况
            map.put("tmp", resultData.get("now").get("temp").textValue());//温度
            map.put("wdir", resultData.get("now").get("windDir").textValue());//风向
            map.put("wsc", resultData.get("now").get("windScale").textValue());//风速
            map.put("hum", resultData.get("now").get("humidity").textValue());//相对湿度
        }

        if (resultData1 != null && resultData1.get("data") != null && resultData1.get("data").size() > 0) {
            map.put("aqi", resultData1.get("data").get(0).get("AQI").textValue());//AQI
            map.put("aqiLevel", resultData1.get("data").get(0).get("AQI_LEVEL").textValue());//aqi等级
        }

        map.put("date", sdf.format(date));//时间
        return RespMsg.ok(map);
    }

    /**
     * 获取天气信息24小时
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/selectWeather24H")
    @ApiOperation("获取天气信息24小时")
    public RespMsg<List<Object>> selectWeather24test() {
        //JsonNode resultData = rt.getForObject("https://www.heweather.com//v1/current/condition/changchun-101060101.html?_=1600682100", JsonNode.class);
        //JsonNode resultData = rt.getForObject("http://192.168.0.6:8000/v1/current/condition/changchun-101060101.html?_=1600682100", JsonNode.class);
        JsonNode resultData = rt.getForObject("https://192.168.0.6:8000/v7/indices/1d?type=1,2,5,6&location=101060101&key=3741d0cf8f094828b250fb514f45787b", JsonNode.class);
        List<Object> list = new ArrayList<>();
        list.add(resultData.get("daily"));
        return RespMsg.ok(list);
    }

}
