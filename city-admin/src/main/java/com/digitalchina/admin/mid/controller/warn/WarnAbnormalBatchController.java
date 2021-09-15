package com.digitalchina.admin.mid.controller.warn;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Warn;
import com.digitalchina.admin.mid.service.WarnService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.WnctyEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 监测异常批量管理前端控制器
 *
 * @author lzy
 * @since 2019/8/30
 */
@RestController
@RequestMapping("/admin/warn/abnormalBatch")
/* @Authorize */
@Api(tags = "监测异常批量管理")
public class WarnAbnormalBatchController {
    @Autowired
    private WarnService warnService;

    @PostMapping("list")
    @ApiOperation("获取监测警告异常信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "wnnm", value = "警告名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "adid", value = "区划ID", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false),})
    public RespMsg<IPage<Warn>> list(@RequestParam(required = false) String wnnm,
                                     @RequestParam(required = false) Integer adid, @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "1") Integer current) {
        IPage<Warn> page = new Page<>(current, size);
        QueryWrapper<Warn> queryWrapper = Condition.<Warn>create().eq(Warn.WNCTY, WnctyEnum.EXCEPTION.getCode())
                .like(StringUtils.isNotBlank(wnnm), Warn.WNNM, wnnm).eq(ObjectUtil.isNotEmpty(adid), Warn.ADID, adid)
                .orderByDesc(Warn.WNID);
        return RespMsg.ok(warnService.page(page, queryWrapper));
    }

    @PostMapping("bathUpdate")
    @ApiOperation("单个或批量修改警告状态信息数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "主键ID数组", dataType = "Integer[]", required = true),
            @ApiImplicitParam(name = "wnstat", value = "警告状态(0未查看、1已查看、2关闭、3取消、11内部处理中，12内部已处理、21外部处理中，22外部已处理)", dataType = "Integer", required = true)})
    public RespMsg<Boolean> bathUpdate(Integer[] ids, Integer wnstat) {
        List<Integer> idList = CollUtil.newArrayList(ids);
        Warn warn = new Warn();
        warn.setWnstat(wnstat);
        QueryWrapper<Warn> queryWrapper = Condition.<Warn>create().in(Warn.WNID, idList);
        return RespMsg.ok(warnService.update(warn, queryWrapper));
    }
}
