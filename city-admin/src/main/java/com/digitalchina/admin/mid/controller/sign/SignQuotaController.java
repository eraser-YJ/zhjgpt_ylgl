package com.digitalchina.admin.mid.controller.sign;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.SignQuota;
import com.digitalchina.admin.mid.entity.SignTree;
import com.digitalchina.admin.mid.service.SignQuotaService;
import com.digitalchina.admin.mid.service.SignTreeService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.enums.GeneralStateEnum;
import com.digitalchina.modules.constant.enums.SignQuotaStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 体征树指标表(仅叶子节点) 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signQuota")
//@Authorize
@Api(tags = "体征指标(仅叶子节点)管理接口")
public class SignQuotaController {

    @Autowired
    private SignQuotaService signQuotaService;
    @Autowired
    private SignTreeService signTreeService;

    @PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("获取体征指标列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ncode", value = "指标码", dataType = "String", required = false),
            @ApiImplicitParam(name = "nname", value = "指标名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "modtStart", value = "更新时间始yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "modtEnd", value = "更新时间止yyyy-MM-dd HH:mm:ss", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false),})
    public RespMsg<IPage<SignQuota>> list(@RequestParam(required = false) String ncode,
                                          @RequestParam(required = false) String nname,
                                          @RequestParam(required = false) Integer status,
                                          @RequestParam(required = false) String modtStart,
                                          @RequestParam(required = false) String modtEnd,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(defaultValue = "1") Integer current) {
        IPage<SignQuota> page = new Page<>(current, size);
        LambdaQueryWrapper<SignQuota> queryWrapper = Condition.<SignQuota>lambda()
                .like(!ObjectUtil.isEmpty(ncode), SignQuota::getNcode, ncode)
                .like(!ObjectUtil.isEmpty(nname), SignQuota::getNname, nname)
                .eq(!ObjectUtil.isEmpty(status), SignQuota::getStatus, status)
                .ge(StrUtil.isNotBlank(modtStart), SignQuota::getModt, DateUtil.parse(modtStart))
                .le(StrUtil.isNotBlank(modtEnd), SignQuota::getModt, DateUtil.parse(modtEnd))
                .orderByDesc(SignQuota::getModt);
        return RespMsg.ok(signQuotaService.page(page, queryWrapper));
    }

    @GetMapping("find")
    @ApiOperation("获取体征指标（单个）")
    @ApiImplicitParam(name = "id", required = true, value = "主键")
    public RespMsg<SignQuota> get(Integer id) {
        SignQuota signQuota = signQuotaService.getById(id);
        return RespMsg.ok(signQuota);
    }

    @PostMapping("save")
    @ApiOperation("新增体征指标信息")
    public RespMsg<SignQuota> saveOne(@Valid @RequestBody SignQuota entity) {
        int count = signQuotaService.count(Condition.<SignQuota>lambda().eq(SignQuota::getNcode, entity.getNcode()));
        if (count > 0) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "指标码已存在,不可重复录入！");
        }
        Date now = new Date();
        entity.setStatus(GeneralStateEnum.NO.getCode());
        entity.setCrdt(now);
        entity.setModt(now);
        signQuotaService.save(entity);
        return RespMsg.ok();
    }

    @PostMapping("update")
    @ApiOperation("修改单个体征指标信息")
    public RespMsg updateOne(@Valid @RequestBody SignQuota entity) {
        SignQuota signQuota = signQuotaService.getOne(Condition.<SignQuota>lambda().eq(SignQuota::getId, entity.getId()));
        if (ObjectUtil.isEmpty(signQuota)) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请刷新列表！");
        }
        if (signQuota.getStatus() == 1) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该指标已启用，不可修改!");
        }
        //只有未启用的指标才可进行编辑，而只有启用的指标才可被关联。
        // 故不需要将数据同步到指标树中去，也不会引起父路径上isKey、isAera的变动
        entity.setModt(new Date());
        signQuotaService.updateById(entity);
        return RespMsg.ok();
    }

    @PostMapping(value = "removeById", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("删除体征指标信息数据")
    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
    public RespMsg delete(Integer id) {
        LambdaQueryWrapper<SignQuota> lambdaQueryWrapper = Condition.<SignQuota>lambda().eq(SignQuota::getId, id);
        SignQuota signQuota = signQuotaService.getOne(lambdaQueryWrapper);
        if (ObjectUtil.isEmpty(signQuota)) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请刷新列表！");
        }
        if (signQuota.getStatus() == 1) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该指标已启用，不可删除!");
        }
        List<SignTree> list = signTreeService.list(Condition.<SignTree>lambda().eq(SignTree::getNcode, signQuota.getNcode()));
        if (ObjectUtil.isNotEmpty(list)) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该指标被使用过，不可删除!");
        }
        signQuotaService.remove(lambdaQueryWrapper);
        return RespMsg.ok();
    }

    @PostMapping(value = "changeStatus", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("指标启用/关闭")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "status", value = "启用状态(0 否 1 是)", dataType = "Integer", required = true)})
    public RespMsg changeStatus(Integer id, Integer status) {
        SignQuota signQuota = signQuotaService.getById(id);
        if (ObjectUtil.isEmpty(signQuota)) {
            return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请刷新列表!");
        }
        //开启
        if (status.equals(SignQuotaStatus.ACTIVE.getCode())) {
            signQuota.setStatus(SignQuotaStatus.ACTIVE.getCode());
            signQuota.setModt(DateUtil.date());
            return RespMsg.ok(signQuotaService.updateById(signQuota));
        } else if (status.equals(SignQuotaStatus.DISABLE.getCode())) {
            //关闭
            List<SignTree> list = signTreeService.list(Condition.<SignTree>lambda().eq(SignTree::getNcode, signQuota.getNcode()));
            if (ObjectUtil.isNotEmpty(list)) {
                return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该指标被使用过，不可关闭!");
            }
            signQuota.setStatus(SignQuotaStatus.DISABLE.getCode());
            signQuota.setModt(DateUtil.date());
            return RespMsg.ok(signQuotaService.updateById(signQuota));
        }
        return RespMsg.error("该指标状态有误!");
    }

    @GetMapping("queryAllEnable")
    @ApiOperation(value = "查询所有已启用的体征指标列表，用于字典")
    public RespMsg<List<SignQuota>> queryAllEnable() {
        return RespMsg.ok(signQuotaService.list(Condition.<SignQuota>lambda().eq(SignQuota::getStatus, GeneralStateEnum.YES.getCode())));
    }

}
