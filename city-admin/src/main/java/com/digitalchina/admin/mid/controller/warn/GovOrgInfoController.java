package com.digitalchina.admin.mid.controller.warn;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.GovOrgInfo;
import com.digitalchina.admin.mid.service.GovOrgInfoService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 政府组织信息表 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-09-05
 */
@RestController
@RequestMapping("admin/govOrgInfo")
@Authorize
@Api(tags = "政府组织信息管理")
public class GovOrgInfoController {
    @Autowired
    private GovOrgInfoService govOrgInfoService;

    @PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("获取政府组织信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "编号", dataType = "String", required = false),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false),})
    public RespMsg<IPage<GovOrgInfo>> list(@RequestParam(required = false) String code, @RequestParam(required = false) String name,
                                           @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        IPage<GovOrgInfo> page = new Page<>(current, size);
        return RespMsg.ok(govOrgInfoService.selectPages(page, code, name));
    }

    @GetMapping("find")
    @ApiOperation("获取政府组织信息（单个）")
    @ApiImplicitParam(name = "id", required = true, value = "主键")
    public RespMsg<GovOrgInfo> get(Integer id) {
        GovOrgInfo govOrgInfo = govOrgInfoService.getById(id);
        return RespMsg.ok(govOrgInfo);
    }

    @PostMapping(value = "save")
    @ApiOperation("新增政府组织信息")
    @Transactional
    public RespMsg<GovOrgInfo> saveOne(@RequestBody @Valid GovOrgInfo entity) {
        Optional.ofNullable(entity).ifPresent(s -> {
            Date now = new Date();
            s.setCrdt(now);
            s.setModt(now);
            govOrgInfoService.save(s);
        });
        return RespMsg.ok(entity);
    }

    @PostMapping("update")
    @ApiOperation("修改单个政府组织信息")
    @Transactional
    public RespMsg<GovOrgInfo> updateOne(@RequestBody @Valid GovOrgInfo entity) {
        Optional.ofNullable(entity).ifPresent(s -> {
            s.setModt(new Date());
            govOrgInfoService.updateById(s);
        });
        return RespMsg.ok(entity);
    }

    @PostMapping(value = "removeById", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("删除政府组织信息数据")
    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
    public RespMsg delete(Integer id) {
        Optional.ofNullable(id).ifPresent(s -> govOrgInfoService.remove(Condition.<GovOrgInfo>lambda().eq(GovOrgInfo::getId, s)));
        return RespMsg.ok();
    }

    @PostMapping(value = "removeByIds", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("批量删除政府组织信息数据")
    @ApiImplicitParam(name = "ids", value = "主键ID数组", dataType = "Integer[]", required = true)
    public RespMsg bathDelete(Integer[] ids) {
        List<Integer> idList = CollUtil.newArrayList(ids);
        Optional.ofNullable(idList).ifPresent(s -> govOrgInfoService.removeByIds(s));
        return RespMsg.ok();
    }

    @PostMapping(value = "/code/repeat", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("检查编号是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "主键id,更新检查时需要传", dataType = "Integer", required = false)})
    public RespMsg repeat(String code, Integer id) {
        LambdaQueryWrapper<GovOrgInfo> queryWrapper = Condition.<GovOrgInfo>lambda().eq(GovOrgInfo::getCode, code)
                .ne(null != id, GovOrgInfo::getId, id);
        return RespMsg.ok(govOrgInfoService.count(queryWrapper));
    }
}
