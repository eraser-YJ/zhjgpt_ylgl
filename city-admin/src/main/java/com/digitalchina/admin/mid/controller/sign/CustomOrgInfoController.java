package com.digitalchina.admin.mid.controller.sign;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.CustomOrgInfo;
import com.digitalchina.admin.mid.service.CustomOrgInfoService;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 自定义组织信息表 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-28
 */
@RestController
@RequestMapping("admin/customOrgInfo")
@Authorize
@Api(tags = "自定义组织信息管理")
public class CustomOrgInfoController {

    @Autowired
    private CustomOrgInfoService customOrgInfoService;

    @PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("获取自定义组织信息列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "编号", dataType = "String", required = false),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false),})
    public RespMsg<IPage<CustomOrgInfo>> list(@RequestParam(required = false) String code, @RequestParam(required = false) String name,
                                              @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        IPage<CustomOrgInfo> page = new Page<>(current, size);
        LambdaQueryWrapper<CustomOrgInfo> queryWrapper = Condition.<CustomOrgInfo>lambda()
                .like(!ObjectUtil.isEmpty(code), CustomOrgInfo::getCode, code)
                .like(!ObjectUtil.isEmpty(name), CustomOrgInfo::getName, name)
                .orderByAsc(CustomOrgInfo::getId);
        return RespMsg.ok(customOrgInfoService.page(page, queryWrapper));
    }

    @GetMapping("find")
    @ApiOperation("获取自定义组织信息（单个）")
    @ApiImplicitParam(name = "id", required = true, value = "主键")
    public RespMsg<CustomOrgInfo> get(Integer id) {
        CustomOrgInfo customOrgInfo = customOrgInfoService.getById(id);
        return RespMsg.ok(customOrgInfo);
    }


    @PostMapping("save")
    @ApiOperation("新增自定义组织信息")
    @Transactional
    public RespMsg<CustomOrgInfo> saveOne(@RequestBody CustomOrgInfo entity) {
        Optional.ofNullable(entity).ifPresent(s -> {
            Date now = new Date();
            s.setCrdt(now);
            s.setModt(now);
            customOrgInfoService.save(s);
        });
        return RespMsg.ok(entity);
    }

    @PostMapping("update")
    @ApiOperation("修改单个自定义组织信息")
    @Transactional
    public RespMsg<CustomOrgInfo> updateOne(@RequestBody CustomOrgInfo entity) {
        Optional.ofNullable(entity).ifPresent(s -> {
            s.setModt(new Date());
            customOrgInfoService.updateById(s);
        });
        return RespMsg.ok(entity);
    }

    @PostMapping(value = "removeById", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("删除自定义组织信息数据")
    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
    public RespMsg delete(Integer id) {
        Optional.ofNullable(id).ifPresent(s -> customOrgInfoService.remove(Condition.<CustomOrgInfo>lambda().eq(CustomOrgInfo::getId, s)));
        return RespMsg.ok();
    }

    @PostMapping(value = "removeByIds", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("批量删除自定义组织信息数据")
    @ApiImplicitParam(name = "ids", value = "主键ID数组", dataType = "Integer[]", required = true)
    public RespMsg bathDelete(Integer[] ids) {
        List<Integer> idList = CollUtil.newArrayList(ids);
        Optional.ofNullable(idList).ifPresent(s -> customOrgInfoService.removeByIds(s));
        return RespMsg.ok();
    }

    @PostMapping(value = "/code/repeat", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("检查编号是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "id", value = "主键id,更新检查时需要传", dataType = "Integer", required = false)})
    public RespMsg repeat(String code, Integer id) {
        LambdaQueryWrapper<CustomOrgInfo> queryWrapper = Condition.<CustomOrgInfo>lambda().eq(CustomOrgInfo::getCode, code)
                .ne(null != id, CustomOrgInfo::getId, id);
        return RespMsg.ok(customOrgInfoService.count(queryWrapper));
    }
}
