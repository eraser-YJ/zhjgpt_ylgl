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
 * 普通事件流程测试类
 * @author lzy
 * @since 2019/10/10
 */
public class normalEventProcessTest {
    private static final String GATEWAY_PATH = "127.0.0.1:8881";
    private static final String EVENT_PATH = "127.0.0.1:8889";

    /**
     * 普通事件流程测试方法
     */
    @Test
    public void normalEventProcessTest() {
        String testAuthToken = "";
        String etest2AuthToken = "";
        String etest3AuthToken = "";
        String etest4AuthToken = "";
        Integer beid = null;
        /**
         * 用户登录获取AuthToken
         */
        String[] users={"test","etest2","etest3","etest4"};
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
                    }else if(i==2){
                        etest3AuthToken=data;
                        System.out.println("etest3AuthToken："+etest3AuthToken);
                    }else {
                        etest4AuthToken=data;
                        System.out.println("etest4AuthToken："+etest4AuthToken);
                    }
                }else {
                    String message = loginMap.get("message");
                    if(i==0){
                        System.out.println("test登录失败："+message);
                    }else if(i==1){
                        System.out.println("etest2登录失败："+message);
                    }else if(i==2){
                        System.out.println("etest3登录失败："+message);
                    }else {
                        System.out.println("etest4登录失败："+message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //普通事件新增服务(开启流）
        beid=ordinaryEventsAdd(testAuthToken);

        //事件处置-二级转派
        boolean secondAllocateBo=secondAllocate(beid,etest2AuthToken);

        //事件处置-业务部门受理
        boolean serviceReceiveBo=false;
        if(secondAllocateBo){
            serviceReceiveBo=serviceReceive(beid,etest3AuthToken);
        }

        //事件处置-业务部门-处理完成
        boolean serviceFinishBo=false;
        if(serviceReceiveBo){
            serviceFinishBo=serviceFinish(beid,etest3AuthToken);
        }

        //事件处置-二级完成（核查通过）
        boolean secondFinishBo=false;
        if(serviceFinishBo){
            secondFinishBo=secondFinish(beid,etest2AuthToken);
        }

        //事件处置-一级完成（事件关闭）
        if(secondFinishBo){
            close(beid,testAuthToken);
        }
    }

    /**
     *普通事件新增服务(开启流）
     */
    public Integer ordinaryEventsAdd(String testAuthToken){
        Integer beid = null;
        if(StringUtils.isNotBlank(testAuthToken)){
            Map<String, Object> ordinaryEventsAddMap = new HashMap<>();
            ordinaryEventsAddMap.put("efbh", 2);
            ordinaryEventsAddMap.put("etbh", 1);
            ordinaryEventsAddMap.put("adid", 1);
            ordinaryEventsAddMap.put("townAdid", 2);
            ordinaryEventsAddMap.put("benm", "脚本测试事件31");
            ordinaryEventsAddMap.put("becnt", "脚本测试事件xxxxxx3");
            ordinaryEventsAddMap.put("bepcdpt0", 2);
            ordinaryEventsAddMap.put("inroom", 0);
            ordinaryEventsAddMap.put("addr", "地址xxx");
            ordinaryEventsAddMap.put("linkman", "梁照延");
            ordinaryEventsAddMap.put("linktel", "13247572625");
            ordinaryEventsAddMap.put("hapndt", "2019-10-11 10:00");
            ordinaryEventsAddMap.put("inoldbh", "123456");
            String result = HttpRequest.post(EVENT_PATH+"/businessOSI/ordinaryEvents/add").form(ordinaryEventsAddMap).header("auth-token",testAuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                beid = Integer.valueOf(responseMap.get("data").toString());
                System.out.println("普通事件新增服务(开启流）成功，返回的事件ID为："+beid);
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("普通事件新增服务(开启流）失败；"+message);
            }
        }
        return beid;
    }

    /**
     * 事件处置-二级转派
     */
    public boolean secondAllocate(Integer beid,String etest2AuthToken){
        if(beid!=null&&StringUtils.isNotBlank(etest2AuthToken)){
            Map<String, Object> secondAllocateMap = new HashMap<>();
            secondAllocateMap.put("beid", beid);
            secondAllocateMap.put("bedid", 3);
            String result = HttpRequest.get(EVENT_PATH+"/event/deposit/second/allocate").form(secondAllocateMap).header("auth-token",etest2AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("事件处置-二级转派成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("事件处置-二级转派失败；"+message);
            }
        }
        return false;
    }

    /**
     * 事件处置-业务部门受理
     */

    public boolean serviceReceive(Integer beid,String etest3AuthToken){
        if(beid!=null&&StringUtils.isNotBlank(etest3AuthToken)){
            Map<String, Object> serviceReceiveMap = new HashMap<>();
            serviceReceiveMap.put("beid", beid);
            String result = HttpRequest.get(EVENT_PATH+"/event/deposit/service/receive").form(serviceReceiveMap).header("auth-token",etest3AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("业务部门受理事件操作成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("业务部门受理事件操作失败；"+message);
            }
        }
        return false;
    }

    /**
     * 事件处置-业务部门-处理完成
     */
    public boolean serviceFinish(Integer beid,String etest3AuthToken){
        if(beid!=null&&StringUtils.isNotBlank(etest3AuthToken)){
            Map<String, Object> serviceFinishMap = new HashMap<>();
            serviceFinishMap.put("beid", beid);
            serviceFinishMap.put("reason", "完成原因完成原因完成原因完成原因");
            String result = HttpRequest.get(EVENT_PATH+"/event/deposit/service/finish").form(serviceFinishMap).header("auth-token",etest3AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("业务部门处理事件完成操作成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("业务部门处理事件完成操作失败；"+message);
            }
        }
        return false;
    }

    /**
     * 事件处置-二级完成（核查通过）
     */
    public boolean secondFinish(Integer beid,String etest2AuthToken){
        if(beid!=null&&StringUtils.isNotBlank(etest2AuthToken)){
            Map<String, Object> secondFinishMap = new HashMap<>();
            secondFinishMap.put("beid", beid);
            secondFinishMap.put("reason", "二级完成完成原因完成原因完成原因完成原因");
            String result = HttpRequest.get(EVENT_PATH+"/event/deposit/second/finish").form(secondFinishMap).header("auth-token",etest2AuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("二级部门事件核查通过操作成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("二级部门事件核查通过操作失败；"+message);
            }
        }
        return false;
    }

    /**
     * 事件处置-一级完成（事件关闭）
     */
    public boolean close(Integer beid,String testAuthToken){
        if(beid!=null&&StringUtils.isNotBlank(testAuthToken)){
            Map<String, Object> closeMap = new HashMap<>();
            closeMap.put("beid", beid);
            closeMap.put("reason", "事件关闭完成原因完成原因完成原因");
            String result = HttpRequest.get(EVENT_PATH+"/event/deposit/close").form(closeMap).header("auth-token",testAuthToken).execute().body();
            Map<String, Object> responseMap = XmlUtil.xmlToMap(result);
            if(Integer.parseInt(String.valueOf(responseMap.get("code")))==200) {
                System.out.println("事件关闭操作成功！");
                return true;
            }else {
                String message = responseMap.get("message").toString();
                System.out.println("事件关闭操作失败；"+message);
            }
        }
        return false;
    }
}
