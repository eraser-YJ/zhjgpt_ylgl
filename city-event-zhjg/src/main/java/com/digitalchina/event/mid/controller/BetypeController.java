package com.digitalchina.event.mid.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.Betype;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.BetypeService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *事件类型 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-11
 */
@Api(tags = "事件类型管理接口")
@RestController
@RequestMapping("betype")
public class BetypeController {

    @Autowired
    private BetypeService betypeService;
    @Autowired
    private BusieventService busieventService;

    @Authorize(SecurityConstant.FORBIDDEN)
    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新事件类型")
    public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Betype betype) {
        Date now =new Date();
        if(null == betype.getEtbh()){
            betype.setCrdt(now);
        }
        betype.setModt(now);
        betype.setRev(1);// 版本默认为1
        betypeService.saveOrUpdate(betype);
        return RespMsg.ok();
    }

    @Authorize
    @GetMapping("find")
    @ApiOperation(value = "查找单个事件类型")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg<Betype> find(@RequestParam(required = true) Integer id) {
        return RespMsg.ok(betypeService.getById(id));
    }

    @Authorize(SecurityConstant.FORBIDDEN)
    @GetMapping(value = "delete")
    @ApiOperation(value = "删除单个事件类型",notes = "使用了外键，删除可能会失败")
    @ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
    public RespMsg del(@RequestParam(required = true) Integer id) {
        LambdaQueryWrapper<Busievent> queryWrapper = Condition.<Busievent>create().lambda()
                .eq(Busievent::getEtbh,id);
        if(busieventService.count(queryWrapper)>0){
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该事件类型已被使用，不可删除！");
        }
        betypeService.removeById(id);
        return RespMsg.ok();
    }

    @Authorize
    @GetMapping("query")
    @ApiOperation(value = "分页查询事件类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "etnm", value = "事项类型名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<IPage<Betype>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                        @RequestParam(defaultValue = "1", required = true) Integer current,
                                        @RequestParam(required = false) String etnm,
                                        @RequestParam(required = false) Integer rev) {
        IPage<Betype> page = new Page<>(current, size);
        LambdaQueryWrapper<Betype> queryWrapper = Condition.<Betype>lambda()
                .like(StringUtils.isNotEmpty(etnm),Betype::getEtnm, etnm)
                .eq(null != rev,Betype::getRev, rev)
                .orderByDesc(Betype::getModt);
        return RespMsg.ok(betypeService.page(page,queryWrapper));
    }

    @Authorize
    @GetMapping("listall")
    @ApiOperation(value = "查询事件类型列表（不分页）")
    @ApiImplicitParams({@ApiImplicitParam(name = "etnm", value = "事项类型名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "rev", value = "版本", dataType = "Integer", required = false),
            })
    public RespMsg<List<Betype>> listall(
                                        @RequestParam(required = false) String etnm,
                                        @RequestParam(required = false) Integer rev) {
        List<Betype> list = betypeService.list(Condition.<Betype>lambda()
                .like(StringUtils.isNotEmpty(etnm),Betype::getEtnm, etnm)
                .eq(null != rev,Betype::getRev, rev)
                .orderByDesc(Betype::getModt));
        return RespMsg.ok(list);
    }
}
