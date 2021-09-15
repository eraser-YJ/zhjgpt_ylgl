package com.digitalchina.gateway.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.dto.MenuDto;
import com.digitalchina.modules.dto.MenuEvenDto;
import com.digitalchina.modules.entity.SysRoleMenu;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.service.SysMenuService;
import com.digitalchina.modules.service.SysRoleMenuService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
@Authorize
@Api(tags = "系统菜单")
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private SysMenuService sms;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${thirdservice.url1:}")
    private String url;

    @Value("${thirdservice.url2:}")
    private String url1;

    @Value("${thirdservice.url3:}")
    private String url2;

    @GetMapping("list")
    @ApiOperation(value = "查询当前用户菜单")
    @ApiImplicitParam(name = "code", value = "系统code，体征体统：citysign，预警系统：citywarn", dataType = "String", required = false)
    public RespMsg<List<MenuDto>> findUserMenu(String code) {

        return RespMsg.ok(sms.findUserMenu(SecurityUtil.currentUserId(), code));
    }

    /**
     * 查询新闻通告权限
     *
     * @return
     */
    @GetMapping("selectNoticeRole")
    @ApiOperation(value = "查询新闻通告权限")
    public RespMsg<Boolean> selectNoticeRole() {
        List<SysRoleMenu> midList = sysRoleMenuService.selectNoticeRole(SecurityUtil.currentUserId());
        if (midList.size() > 0) {
            return RespMsg.ok(true);
        } else {
            return RespMsg.ok(false);
        }
    }

    /**
     * 查询全量菜单
     *
     * @param code
     * @return
     */
    @GetMapping("multiMenuList")
    @ApiOperation(value = "查询全量菜单")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "系统code", dataType = "String"),
            @ApiImplicitParam(name = "zhjgSzss", value = "智慧建管", dataType = "String"),
            @ApiImplicitParam(name = "zhjgCsmp", value = "奕迅接口", dataType = "String")
    })
    public RespMsg<Map<String, Object>> findUserMultiMenu(String code) {
        List<SysRoleMenu> midList = sysRoleMenuService.selectMidMenu(SecurityUtil.currentUserId());
        Map<String, Object> map = new HashedMap();
        List<MenuDto> meunsList = new ArrayList<>();
        List<Object> zjMeunsList = new ArrayList<>();
        try {
            JsonNode jsonNode = getHttp(url, SecurityUtil.currentUser().getLogin());
            map.put("zhjgCsmp", setMenuDtoValue(jsonNode, meunsList, 2000000, url1));//奕迅菜单第三方接口
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取建设管理菜单权限信息失败", e.getMessage());
        }

        try {
            map.put("zhjgSzss", sms.findUserAllMenu("zhjgSzss"));//建管模块
            map.put("mid", midList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取公用设施园林绿化菜单及权限信息失败", e.getMessage());
        }

        try {
            map.put("zhufang", getZhufangMenus(url2, SecurityUtil.currentUser().getLogin()));//住房保障菜单第三方接口
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取住房保障与物业管理菜单权限信息失败", e.getMessage());
        }

        return RespMsg.ok(map);
    }

    private Object getZhufangMenus(String url2, String loginId) throws IOException {
        URLConnection connection = new URL(url2 + loginId.trim()).openConnection();
        System.out.println(connection.getURL().toString());
        connection.connect();
        try (InputStream is = connection.getInputStream()) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return objectMapper.readTree(bytes);
        }
    }

    /**
     * 查询待办事项
     *
     * @return
     */
    @GetMapping("selectEnvent")
    @ApiOperation(value = "查询待办事项")
    @ApiImplicitParams({@ApiImplicitParam(name = "p", value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "pr", value = "用户密码", dataType = "String")
    })
    public RespMsg<Map<String, Object>> selectEnvent(String p, String pr) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<MenuEvenDto> meunsEventList = new ArrayList<>();

        try {
            //JsonNode jsonNode =getHttpEvent("http://10.0.251.62:1010/csmp","csmp_admin",p,pr);
            JsonNode jsonNode = getHttpEvent(url, SecurityUtil.currentUser().getLogin(), p, pr);
            log.info("待办事项列表");
            log.info(jsonNode.toString());
            setEventDtoValue(jsonNode, meunsEventList, url1);
            map.put("totalPages", jsonNode.get("totalPages"));
            map.put("pageRows", jsonNode.get("pageRows"));
            map.put("page", jsonNode.get("page"));
            map.put("totalCount", jsonNode.get("totalCount"));
            map.put("data", meunsEventList);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询待办失败。。。。。。， %s", e.getMessage());
        }
        return RespMsg.ok(map);
    }

    /**
     * menus赋值---菜单列表
     *
     * @param jsonNode 返回格式
     * @param number   2000000 追加数字
     * @param url1     请求地址
     * @return
     */
    private List<MenuDto> setMenuDtoValue(JsonNode jsonNode, List<MenuDto> meunsList, Integer number, String url1) {
        for (int i = 0; i < jsonNode.get("menus").size(); i++) {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(jsonNode.get("menus").get(i).get("id") instanceof NullNode ? null : number + jsonNode.get("menus").get(i).get("id").asInt());
            menuDto.setName(jsonNode.get("menus").get(i).get("name") instanceof NullNode ? "" : jsonNode.get("menus").get(i).get("name").textValue().replace('"', ' ').trim());
            menuDto.setIcon(jsonNode.get("menus").get(i).get("icon") instanceof NullNode ? "" : url1 + jsonNode.get("menus").get(i).get("icon").textValue().replace('"', ' ').trim());
            menuDto.setUrl(jsonNode.get("menus").get(i).get("url") instanceof NullNode ? "" : url1 + jsonNode.get("menus").get(i).get("url").textValue().replace('"', ' ').trim());
            meunsList.add((menuDto));
        }
        return meunsList;
    }


    /**
     * menus赋值---住建菜单菜单列表
     *
     * @param jsonNode 返回格式
     * @return
     */

    private List<Object> setMenuDtoZjValue(JsonNode jsonNode, List<Object> meunsList) {
        if (!(jsonNode.get("rows") instanceof NullNode) || jsonNode != null) {
            for (int i = 0; i < jsonNode.get("rows").size(); i++) {
                MenuDto menuDto = new MenuDto();
                menuDto.setId(jsonNode.get("rows").get(i).get("id") instanceof NullNode ? null : jsonNode.get("rows").get(i).get("id").asInt());
                menuDto.setName(jsonNode.get("rows").get(i).get("name") instanceof NullNode ? "" : jsonNode.get("rows").get(i).get("name").textValue().replace('"', ' ').trim());
                menuDto.setIcon(jsonNode.get("rows").get(i).get("icon") instanceof NullNode ? "" : jsonNode.get("rows").get(i).get("icon").textValue().replace('"', ' ').trim());
                menuDto.setUrl(jsonNode.get("rows").get(i).get("url") instanceof NullNode ? "" : jsonNode.get("rows").get(i).get("url").textValue().replace('"', ' ').trim());
                meunsList.add((menuDto));
            }
        }
        return meunsList;
    }


    /**
     * MenuEvenDto---事件列表赋值
     *
     * @param jsonNode
     * @param meunsEventList
     * @param url1
     * @return
     */
    private List<MenuEvenDto> setEventDtoValue(JsonNode jsonNode, List<MenuEvenDto> meunsEventList, String url1) {
        if (jsonNode == null || jsonNode.get("data") == null) {
            return meunsEventList;
        }

        for (int i = 0; i < jsonNode.get("data").size(); i++) {
            MenuEvenDto menuEvenDto = new MenuEvenDto();
            menuEvenDto.setNo(jsonNode.get("data").get(i).get("no") instanceof NullNode ? null : jsonNode.get("data").get(i).get("no").asInt());
            menuEvenDto.setType(jsonNode.get("data").get(i).get("type") instanceof NullNode ? null : jsonNode.get("data").get(i).get("type").textValue());
            menuEvenDto.setTitle(jsonNode.get("data").get(i).get("title") instanceof NullNode ? null : jsonNode.get("data").get(i).get("title").textValue());
            menuEvenDto.setHandleUrl(jsonNode.get("data").get(i).get("handleUrl") instanceof NullNode ? null : url1 + jsonNode.get("data").get(i).get("handleUrl").textValue());
            meunsEventList.add((menuEvenDto));
        }
        return meunsEventList;
    }

    /**
     * authed_menus赋值---mid有权限的菜单
     *
     * @param jsonNode
     * @param midList
     * @param number
     * @return
     */
    private List<SysRoleMenu> setMidValue(JsonNode jsonNode, List<SysRoleMenu> midList, Integer number) {
        for (int i = 0; i < jsonNode.get("authed_menus").size(); i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMid(jsonNode.get("authed_menus").get(i) instanceof NullNode ? null : number + Integer.parseInt(jsonNode.get("authed_menus").get(i).toString()));
            midList.add(sysRoleMenu);//将返回的mid复制到List里面
        }
        return midList;
    }


    /**
     * HTTP--接口访问
     *
     * @param urlAddr  请求地址
     * @param userName 用户名
     * @return
     * @throws Exception
     */
    private JsonNode getHttp(String urlAddr, String userName) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JsonNode jsonNode = null;
        String content = null;
        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet(urlAddr + "/digital/api/menu/" + userName);
        try {
            // 执行请求,获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                jsonNode = objectMapper.readTree(content);
            }
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
        return jsonNode;
    }

    /**
     * HTTP--住建接口访问
     *
     * @param urlAddr  请求地址
     * @param userName 用户名
     * @return
     * @throws Exception
     */
    private JsonNode getZjHttp(String urlAddr, String userName) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JsonNode jsonNode = null;
        String content = null;
        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet(urlAddr + "/EXTPMS/pages/Menus/getMenus.do?username=" + userName);
        try {
            // 执行请求,获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                jsonNode = objectMapper.readTree(content);
            }
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
        return jsonNode;
    }

    /**
     * HTTP--待办事项接口访问方法
     *
     * @param urlAddr 请求地址
     * @param ln      用户名
     * @param p       当前页
     * @param ln      每页显示条数
     * @return JsonNode  JSON类型
     * @throws Exception
     */
    private JsonNode getHttpEvent(String urlAddr, String ln, String p, String pr) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JsonNode jsonNode = null;
        String content = null;
        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet(urlAddr + "/digital/api/todoList?" + "ln=" + ln + "&p=" + p + "&pr=" + pr);
        try {
            // 执行请求,获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity(), "UTF-8"));
            }
        } finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
        return jsonNode;
    }
}
