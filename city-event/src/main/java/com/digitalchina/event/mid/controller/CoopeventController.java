package com.digitalchina.event.mid.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.eventdeposit.CoopeventBasicDto;
import com.digitalchina.event.dto.eventdeposit.CoopeventDto;
import com.digitalchina.event.mid.entity.Coopevent;
import com.digitalchina.event.mid.service.CoopeventService;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 协查事件 前端控制器
 * </p>
 *
 * @author lichunlong
 * @since 2019-09-15
 */
@Api(tags = "协查事件相关接口")
@Authorize
@RestController
@RequestMapping("/coopevent")
public class CoopeventController {

    @Autowired
    private CoopeventService coopeventService;

    /**
     * 协查件-发起件-统计
     */
    @GetMapping(value = "countCreateCoop")
    @ApiOperation("协查件-发起件-列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")
    })
    public RespMsg<IPage<CoopeventDto>> countCreateCoop(@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        IPage<CoopeventDto> list = coopeventService.countCreateCoop(SecurityUtil.currentUserId(), size, current);
        return RespMsg.ok(list);
    }

    /**
     * 协查件-协办件-统计
     */
    @GetMapping("countReceiveCoop")
    @ApiOperation("协查件-协办件-列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")
    })
    public RespMsg<IPage<CoopeventDto>> countReceiveCoop(@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        IPage<CoopeventDto> list = coopeventService.countReceiveCoop(SecurityUtil.currentUserId(), size, current);
        return RespMsg.ok(list);
    }

    /**
     * 事件处置-详情查看
     */
    @GetMapping(value = "eventDepositDetail")
    @ApiOperation("协查件-协办件-处置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "协查事件id", required = true)
    })
    public RespMsg<List<Map<String, Object>>> eventDepositDetail(Integer ceid) {
        return RespMsg.ok(coopeventService.eventDepositInfo(ceid));
    }

    @GetMapping(value = "submit/second")
    @ApiOperation("协查件-提交二级审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true)
    })
    public RespMsg<Void> submitSecond(Integer ceid) {
        try {
            coopeventService.submitSecond(ceid, null);
        } catch (Exception e) {
            throw new ServiceException("提交二级审批操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "submit/first")
    @ApiOperation("协查件-提交一级审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true)
    })
    public RespMsg<Void> submitFirst(Integer ceid) {
        try {
            coopeventService.submitFirst(ceid, null);
        } catch (Exception e) {
            throw new ServiceException("提交一级审批操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "second/pass")
    @ApiOperation("协查件-二级审批通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true)
    })
    public RespMsg<Void> secondPass(Integer ceid) {
        try {
            coopeventService.secondPass(ceid, null);
        } catch (Exception e) {
            throw new ServiceException("二级审批通过操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "first/pass")
    @ApiOperation("协查件-一级审批通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true)
    })
    public RespMsg<Void> firstPass(Integer ceid) {
        try {
            coopeventService.firstPass(ceid, null);
        } catch (Exception e) {
            throw new ServiceException("一级审批通过操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "second/refuse")
    @ApiOperation("协查件-二级驳回协查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "原因", required = true)
    })
    public RespMsg<Void> secondRefuse(Integer ceid, String reason) {
        try {
            coopeventService.secondRefuse(ceid, reason);
        } catch (Exception e) {
            throw new ServiceException("二级驳回协查操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "first/refuse")
    @ApiOperation("协查件-一级驳回协查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "原因", required = true)
    })
    public RespMsg<Void> firstRefuse(Integer ceid, String reason) {
        try {
            coopeventService.firstRefuse(ceid, reason);
        } catch (Exception e) {
            throw new ServiceException("二级驳回协查操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "service/fallback")
    @ApiOperation("协查件-反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "原因", required = true)
    })
    public RespMsg<Void> serviceFallback(Integer ceid, String reason) {
        try {
            coopeventService.serviceFallback(ceid, reason);
        } catch (Exception e) {
            throw new ServiceException("处理协查完成操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "service/close")
    @ApiOperation("协查件-关闭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "原因", required = true)
    })
    public RespMsg<Void> serviceClose(Integer ceid, String reason) {
        try {
            coopeventService.serviceClose(ceid, reason);
        } catch (Exception e) {
            throw new ServiceException("处理协查完成操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping(value = "service/cancel")
    @ApiOperation("协查件-取消")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true),
            @ApiImplicitParam(name = "reason", value = "原因", required = true)
    })
    public RespMsg<Void> serviceCancel(Integer ceid, String reason) {
        try {
            coopeventService.serviceCancel(ceid, reason);
        } catch (Exception e) {
            throw new ServiceException("取消协查操作失败！", e);
        }
        return RespMsg.ok();
    }

    @GetMapping("basicinfo")
    @ApiOperation("协查件-基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true)
    })
    public RespMsg<CoopeventBasicDto> basicinfo(Integer ceid) {
        CoopeventBasicDto coopevent = coopeventService.getBasicinfo(ceid);
        return RespMsg.ok(coopevent);
    }

    @GetMapping("getone")
    @ApiOperation("协查件-详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ceid", value = "事件id", required = true)
    })
    public RespMsg<Coopevent> getOne(Integer ceid) {
        Coopevent coopevent = coopeventService.getById(ceid);
        return RespMsg.ok(coopevent);
    }

    @GetMapping("updateone")
    @ApiOperation("协查件-修改协查")
    public RespMsg<Coopevent> updateOne(Coopevent coopevent) {
        try {
            coopevent.setModt(new Date());
            coopeventService.updateById(coopevent);
        } catch (Exception e) {
            throw new ServiceException("修改协查操作失败！", e);
        }
        return RespMsg.ok(coopevent);
    }



}
