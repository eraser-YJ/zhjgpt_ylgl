package com.digitalchina.admin.mid.controller.event;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.event.LoginUser;
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登陆过事件系统的用户 前端控制器
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
@Api(tags = "事件系统用户管理")
@RestController
//@Authorize
@RequestMapping("admin/loginUser")
@Deprecated
public class LoginUserController {

    @Autowired
    private CityEventService cityEventService;

    @GetMapping(value = "empower")
    @ApiOperation(value = "给用户指定部门")
    @ApiImplicitParams({@ApiImplicitParam(name = "uid", value = "用户Id", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "did", value = "部门id", dataType = "Integer", required = true)})
    public RespMsg<Void> empowerDept(@RequestParam(required = true) Integer uid,
                                     @RequestParam(required = true) Integer did) {

        return cityEventService.empowerDept(uid,did);
    }

    @GetMapping(value = "query")
    @ApiOperation(value = "分页查询事件系统的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", value = "登录名", dataType = "String", required = false),
            @ApiImplicitParam(name = "name", value = "用户名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "dept", value = "所属部门", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<Page<LoginUser>> queryLoginUser(@RequestParam(defaultValue = "10", required = true) Integer size,
                                                   @RequestParam(defaultValue = "1", required = true) Integer current,
                                                   @RequestParam(required = false) String login,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String dept) {
        return cityEventService.queryLoginUser(size,current,login,name,dept);
    }
}
