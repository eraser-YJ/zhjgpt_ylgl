package com.digitalchina.event.test;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import com.digitalchina.common.utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 协查事件流程测试类
 * @author lzy
 * @since 2019/10/14
 */
public class coopEventProcessTest {
    private static final String GATEWAY_PATH = "127.0.0.1:8881";
    private static final String EVENT_PATH = "127.0.0.1:8889";

    /**
     * 协查事件流程测试方法
     */
    @Test
    public void coopEventProcessTest() {
        String testAuthToken = "";
        String etest2AuthToken = "";
        String etest3AuthToken = "";
        Integer ceid = null;
        /**
         * 用户登录获取AuthToken
         */
        String[] users={"test","etest2","etest3"};
        for(int i=0;i<users.length;i++){
            String loginResult=HttpRequest.get(GATEWAY_PATH+"/login?login="+users[i]+"&password=123456").execute().body();
            Map<String, String> loginMap = null;
            try {
                loginMap = JsonMapper.getInstance().readValue(loginResult, new TypeReference<Map<String, String>>() {});
                if(200==Integer.parseInt(loginMap.get("code"))) {
                    String data = loginMap.get("data");
                    if(i==0){
                        testAuthToken=data;
                        System.out.println("testAuthToken："+testAuthToken);
                    }else if(i==1){
                        etest2AuthToken=data;
                        System.out.println("etest2AuthToken："+etest2AuthToken);
                    }else {
                        etest3AuthToken=data;
                        System.out.println("etest3AuthToken："+etest3AuthToken);
                    }
                }else {
                    String message = loginMap.get("message");
                    if(i==0){
                        System.out.println("test登录失败："+message);
                    }else if(i==1){
                        System.out.println("etest2登录失败："+message);
                    }else{
                        System.out.println("etest3登录失败："+message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //协查事件新增服务(开启流）
        ceid=investigationEventsAdd(etest2AuthToken);

        //协查件-提交一级审批
        boolean submitFirstBo=submitFirst(etest2AuthToken,ceid);

        //协查件-一级审批通过
        boolean firstPassBo=false;
        if(submitFirstBo){
            firstPassBo=firstPass(testAuthToken,ceid);
        }

        //协查件-关闭
        if(firstPassBo){
            serviceClose(etest3AuthToken,ceid);
        }
    }

    /**
     *协查事件新增服务(开启流）
     */
    public Integer investigationEventsAdd(String etest2AuthToken){
        Integer ceid = null;
        if(StringUtils.isNotBlank(etest2AuthToken)){
            Map<String, Object> investigationEventsAddMap = new HashMap<>();
            investigationEventsAddMap.put("cenm","协查事件脚本测试2");
            investigationEventsAddMap.put("cecnt", "协查事件脚本测试xxxxxx");
            investigationEventsAddMap.put("cpdesc", "因为xxxx");
            investigationEventsAddMap.put("cpbedid", 3);
            investigationEventsAddMap.put("cepcdpt0", 3);
            investigationEventsAddMap.put("etbh", 1);
            investigationEventsAddMap.put("efbh", 2);
            investigationEventsAddMap.put("hapndt", "2019-10-15 09:55");
            investigationEventsAddMap.put("addr", "地址xxx");
            investigationEventsAddMap.put("adid", 1);
            investigationEventsAddMap.put("townAdid", 2);
            String result = HttpRequest.post(EVENT_PATH+"/businessOSI/investigationEvents/add").form(investigationEventsAddMap).header("auth-token",etest2AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                ceid = Integer.valueOf(responseMap.get("data").toString());
                System.out.println("协查事件新增服务(开启流）成功，返回的事件ID为："+ceid);
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("协查件新增服务(开启流）失败；"+message);
            }
        }
        return ceid;
    }

    /**
     * 协查件-提交一级审批
     */
    public boolean submitFirst(String etest2AuthToken,Integer ceid){
        if(ceid!=null&&StringUtils.isNotBlank(etest2AuthToken)){
            Map<String, Object> submitFirstMap = new HashMap<>();
            submitFirstMap.put("ceid", ceid);
            String result = HttpRequest.get(EVENT_PATH+"/coopevent/submit/first").form(submitFirstMap).header("auth-token",etest2AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("协查件-提交一级审批成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("协查件-提交一级审批失败；"+message);
            }
        }
        return false;
    }

    /**
     * 协查件-一级审批通过
     */
    public boolean firstPass(String testAuthToken,Integer ceid){
        if(ceid!=null&&StringUtils.isNotBlank(testAuthToken)){
            Map<String, Object> firstPassMap = new HashMap<>();
            firstPassMap.put("ceid", ceid);
            String result = HttpRequest.get(EVENT_PATH+"/coopevent/first/pass").form(firstPassMap).header("auth-token",testAuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("协查件-一级审批通过成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("协查件-一级审批通过失败；"+message);
            }
        }
        return false;
    }

    /**
     * 协查件-关闭
     */
    public boolean serviceClose(String etest3AuthToken,Integer ceid){
        if(ceid!=null&&StringUtils.isNotBlank(etest3AuthToken)){
            Map<String, Object> serviceCloseMap = new HashMap<>();
            serviceCloseMap.put("ceid", ceid);
            serviceCloseMap.put("reason", "关闭原因xxxxx");
            String result = HttpRequest.get(EVENT_PATH+"/coopevent/service/close").form(serviceCloseMap).header("auth-token",etest3AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("协查件-关闭成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("协查件-关闭失败；"+message);
            }
        }
        return false;
    }
}
