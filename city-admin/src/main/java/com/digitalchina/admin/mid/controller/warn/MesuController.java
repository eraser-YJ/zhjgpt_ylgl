package com.digitalchina.admin.mid.controller.warn;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Mesu;
import com.digitalchina.admin.mid.service.MesuService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/**
 * <p>
 * 指标 前端控制器
 * </p>
 *
 * @author lichunlong
 * @since 2019-08-30
 */
@Api(tags = "预警指标管理")
@Authorize
@RestController
@RequestMapping("/admin/mesu")
public class MesuController {

    @Autowired
    private MesuService mesuService;

    @PostMapping("list")
    @ApiOperation("获取指标数据（列表）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mscode", value = "指标代码"),
            @ApiImplicitParam(name = "msnm", value = "指标名称"),
            @ApiImplicitParam(name = "stat", value = "禁用启用（-1禁用，0启用但不处理，1启用且处理）"),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")
    })
    public RespMsg<IPage<Mesu>> list(String mscode, String msnm, Integer stat
            , @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current
    ) {
        IPage<Mesu> page = new Page<>(current, size);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like(ObjectUtil.isNotEmpty(mscode), Mesu.MSCODE, mscode);
        wrapper.like(ObjectUtil.isNotEmpty(msnm), Mesu.MSNM, msnm);
        wrapper.eq(ObjectUtil.isNotEmpty(msnm), Mesu.STAT, stat);
        wrapper.orderByDesc(Mesu.MSID);
        IPage<Mesu> mesus = mesuService.page(page, wrapper);
        return RespMsg.ok(mesus);
    }

    @PostMapping("get")
    @ApiOperation("获取指标数据（单个）")
    @ApiImplicitParam(name = "msid", required = true, value = "指标ID")
    public RespMsg<Mesu> get(Integer msid) {
        Mesu mesu = mesuService.getOne(Condition.<Mesu>create().eq(Mesu.MSID, msid));
        return RespMsg.ok(mesu);
    }

    @PostMapping("save")
    @ApiOperation("新增指标数据")
    @Transactional
    public RespMsg<Mesu> saveOne(Mesu mesu) {
        Optional.ofNullable(mesu).ifPresent(s -> mesuService.save(s));
        return RespMsg.ok(mesu);
    }

    @PostMapping("update")
    @ApiOperation("修改单个指标数据")
    @Transactional
    public RespMsg<Mesu> updateOne(Mesu mesu) {
        Optional.ofNullable(mesu).ifPresent(s -> {
            s.setModt(DateUtil.formatDateTime(new Date()));
            mesuService.updateById(s);
        });
        return RespMsg.ok(mesu);
    }

    @PostMapping("removeById")
    @ApiOperation("删除单个指标数据")
    @ApiImplicitParam(name = "msid", required = true, value = "指标ID")
    public RespMsg delete(Integer msid) {
        Optional.ofNullable(msid).ifPresent(s -> mesuService.remove(Condition.<Mesu>create().eq(Mesu.MSID, s)));
        return RespMsg.ok();
    }

}
