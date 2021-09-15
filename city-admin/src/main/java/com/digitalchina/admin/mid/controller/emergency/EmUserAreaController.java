package com.digitalchina.admin.mid.controller.emergency;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.NfAreaDto;
import com.digitalchina.admin.mid.entity.emergency.EmUserArea;
import com.digitalchina.admin.mid.service.EmUserAreaService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户区域授权表 前端控制器
 * </p>
 *
 * @author Auto
 * @since 2019-12-06
 */
//@Authorize
@Api(tags = "应急辅助系统区域授权管理")
@RestController
@RequestMapping("/emUserArea")
public class EmUserAreaController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EmUserAreaService emUserAreaService;

    @GetMapping("query")
    @ApiOperation(value = "分页查询拥有应急系统权限的用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "keywords", value = "关键字(用户登录名/用户名)", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "Integer", required = true)})
    public RespMsg<IPage<SysUser>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                         @RequestParam(defaultValue = "1", required = true) Integer current,
                                         @RequestParam(required = false) String keywords) {
        IPage<SysUser> page = new Page<>(current, size);
        return RespMsg.ok(sysUserService.findUserListBycode(page,SysCodeEnum.CITYEMERGENCY.getCode(),keywords));
    }

    @GetMapping("findArea")
    @ApiOperation(value = "查看用户拥有的区域的权限",notes = "树形")
    @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true)
    public RespMsg<List<NfAreaDto>> findArea(@RequestParam(required = true) Integer userId) {
        return RespMsg.ok(NfAreaDto.makeTree(emUserAreaService.getAreaDictByUser(userId)));
    }

    @CacheEvict(value = "getUserAreaByUid",key = "#uid")
    @Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    @PostMapping(value = "empower", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "授权")
    @ApiImplicitParams({@ApiImplicitParam(name = "aids", value = "区域id数组,树上打全勾的节点", dataType = "Integer[]", required = true),
            @ApiImplicitParam(name = "uid", value = "用户Id", dataType = "Integer", required = true)})
    public RespMsg<Void> empowerRole(@RequestParam(required = true) Integer[] aids,
                                     @RequestParam(required = true) Integer uid) {
        emUserAreaService.remove(Condition.<EmUserArea>lambda().eq(EmUserArea::getUid,uid));
        EmUserArea emUserArea = new EmUserArea();
        emUserArea.setUid(uid);
        emUserArea.setAids(aids);
        emUserAreaService.save(emUserArea);
        return RespMsg.ok();
    }

}
