package com.digitalchina.event.mid.controller;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.Befrom;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.BefromService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  事件来源 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-11
 */
@Api(tags = "事件来源管理接口")
@RestController
@RequestMapping("befrom")
public class BefromController {

    @Autowired
    private BefromService befromService;
    @Autowired
    private BusieventService busieventService;

    @Authorize(SecurityConstant.FORBIDDEN)
    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新事件来源")
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Befrom befrom) {
        Date now =new Date();
        if(null == befrom.getEfbh()){
            befrom.setCrdt(now);
        }
        befrom.setModt(now);
        befromService.saveOrUpdate(befrom);
        return RespMsg.ok();
    }

    @Authorize
    @GetMapping("find")
    @ApiOperation(value = "查找单个事件来源")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Befrom> find(@RequestParam(required = true) Integer id) {
        return RespMsg.ok(befromService.getById(id));
    }

    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个事件来源",notes = "使用了外键，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg del(@RequestParam(required = true) Integer id) {
        LambdaQueryWrapper<Busievent> queryWrapper = Condition.<Busievent>create().lambda()
                .eq(Busievent::getEfbh,id);
        if(busieventService.count(queryWrapper)>0){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该事件来源已被使用，不可删除！");
        }
        befromService.removeById(id);
        return RespMsg.ok();
    }
    @Authorize
    @GetMapping("query")
    @ApiOperation(value = "分页查询事件来源")
    @ApiImplicitParams({@ApiImplicitParam(name = "efnm", value = "事项来源名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<IPage<Befrom>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                        @RequestParam(defaultValue = "1", required = true) Integer current,
                                        @RequestParam(required = false) String efnm,
                                        @RequestParam(required = false) Integer rev) {
        IPage<Befrom> page = new Page<>(current, size);
        LambdaQueryWrapper<Befrom> queryWrapper = Condition.<Befrom>lambda()
                .like(StringUtils.isNotEmpty(efnm),Befrom::getEfnm, efnm)
                .eq(null != rev,Befrom::getRev, rev)
                .orderByDesc(Befrom::getModt);
        return RespMsg.ok(befromService.page(page,queryWrapper));
    }

    @Authorize
    @GetMapping("listall")
    @ApiOperation(value = "查询事件来源列表（不分页）")
    @ApiImplicitParams({@ApiImplicitParam(name = "efnm", value = "事项来源名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            })
    public RespMsg<List<Befrom>> listall(
                                        @RequestParam(required = false) String efnm,
                                        @RequestParam(required = false) Integer rev) {
        List<Befrom> list = befromService.list(Condition.<Befrom>lambda()
                .like(StringUtils.isNotEmpty(efnm),Befrom::getEfnm, efnm)
                .eq(null != rev,Befrom::getRev, rev)
                .orderByDesc(Befrom::getModt));
        return RespMsg.ok(list);
    }
}
