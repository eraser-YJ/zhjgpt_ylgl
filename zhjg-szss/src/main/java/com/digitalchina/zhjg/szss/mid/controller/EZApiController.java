package com.digitalchina.zhjg.szss.mid.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 海康萤石云API后端服务
 */
@RestController
@RequestMapping("/ezapi")
public class EZApiController {

    private static final String ACCESS_TOKEN_EXPIRED_STATUS_CODE = "10002";
    private static final String ACCESS_TOKEN_REDIS_KEY = "__ezapi_access_token_redis_key__";

    @Value("${ezapi.url.getAccessToken}")
    private String apiUrlGetAccessToken;
    @Value("${ezapi.url.getDeviceList}")
    private String apiUrlGetDeviceList;
    @Value("${ezapi.url.getDeviceInfo}")
    private String apiUrlGetDeviceInfo;
    @Value("${ezapi.url.loginToRTU}")
    private String apiUrlLoginToRTU;
    @Value("${ezapi.url.openCamera}")
    private String apiUrlOpenCamera;

    @Value("${ezapi.key}")
    private String key;
    @Value("${ezapi.secret}")
    private String secret;

    @Value("${ezapi.rtu.user}")
    private String rtuUser;
    @Value("${ezapi.rtu.password}")
    private String rtuPassword;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/accessToken")
    public String getAccessToken() throws Exception {
        String accessToken = (String) redisTemplate.opsForValue().get(ACCESS_TOKEN_REDIS_KEY);
        if (StrUtil.isEmpty(accessToken)) {
            accessToken = getNewToken();
        }

        // 每次获取时都检验accessToken可用性，如果不可用重新生成(浪费资源但逻辑简单，后续如果有优化需求再修改)
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("accessToken", accessToken);
        Map<String, Object> resp = restTemplate.postForObject(apiUrlGetDeviceList, param, Map.class);
        String respCode = (String) resp.get("code");
        if (ACCESS_TOKEN_EXPIRED_STATUS_CODE.equals(respCode)) {
            accessToken = getNewToken();
        }

        return accessToken;
    }

    @GetMapping("/deviceIsActived")
    public String isActived(String deviceCode) throws Exception {
        String accessToken = getAccessToken();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("accessToken", accessToken);
        param.add("deviceSerial", deviceCode);
        Map<String, Object> resp = restTemplate.postForObject(apiUrlGetDeviceInfo, param, Map.class);
        String respCode = (String) resp.get("code");

        if (!"200".equals(respCode)) {
            throw new Exception(String.format("code: %s, msg: %s", respCode, resp.get("msg")));
        }

        Map<String, Object> data = (Map<String, Object>) resp.get("data");
        int status = (int) data.get("status");

        return status == 1 ? "online" : "offline";
    }

    @GetMapping("/openCamera")
    public String openCamera(String stationId) throws Exception {

        Assert.state(StrUtil.isNotBlank(stationId), "参数stationId不允许为空");

        String token = loginToRTU(); // 每次请求获取一个新token，避免存储token及判断token有效性的麻烦

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("X-Access-Token", token);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ctrlName", "camera_open");
        paramMap.put("sns", stationId);
        String content = new ObjectMapper().writeValueAsString(paramMap);

        HttpEntity<String> request = new HttpEntity<>(content, headers);
        Map<String, Object> resp = restTemplate.postForObject(apiUrlOpenCamera, request, Map.class);

        boolean success = (boolean) resp.get("success");

        return success ? "success" : "fail";
    }

    // 登录到RTU平台，攻取token
    private String loginToRTU() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("remember_me", true);
        paramMap.put("username", rtuUser);
        paramMap.put("password", rtuPassword);

        String content = new ObjectMapper().writeValueAsString(paramMap);

        HttpEntity<String> request = new HttpEntity<>(content, headers);

        Map<String, Object> resp = restTemplate.postForObject(apiUrlLoginToRTU, request, Map.class);

        boolean success = (boolean) resp.get("success");
        if (!success) {
            throw new Exception("error: " + resp);
        }

        return (String) ((Map) resp.get("result")).get("token");
    }

    private String getNewToken() throws Exception {

        MultiValueMap<String, String> paras = new LinkedMultiValueMap<>();
        paras.add("appKey", key);
        paras.add("appSecret", secret);

        Map<String, Object> resp = restTemplate.postForObject(apiUrlGetAccessToken, paras, Map.class);
        String respCode = (String) resp.get("code");
        if (!"200".equals(respCode)) {
            throw new Exception(String.format("code:%s, msg: %s", respCode, resp.get("message")));
        }

        Map<String, Object> data = (Map<String, Object>) resp.get("data");
        String accessToken = (String) data.get("accessToken");
        long expireTime = (long) data.get("expireTime");

        redisTemplate.opsForValue().set(ACCESS_TOKEN_REDIS_KEY, accessToken, expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);

        return accessToken;
    }


}
