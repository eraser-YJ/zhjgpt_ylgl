package com.digitalchina.event.mid.controller;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.event.mid.service.EventDepositService;
import com.digitalchina.modules.constant.TransConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "事件管理接口")
@RestController
@RequestMapping("busievent")
public class BusieventController {

    @Autowired
    private BusieventService busieventService;
    @Autowired
    private EventDepositService depositService;

    /**
     * 事件处置-一级指挥中心-未处理
     */
    @GetMapping("list")
    @ApiOperation("获取事件数据（列表）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bestat", value = "事件状态，默认-未处理，其它：-转派中，-处理中，-待审批，-待核查，-拒绝件，-取消件，-升级件"),
            @ApiImplicitParam(name = "efbh", value = "事件类型"),
            @ApiImplicitParam(name = "etbh", value = "事件来源"),
            @ApiImplicitParam(name = "benm", value = "事件名称"),
            @ApiImplicitParam(name = "becnt", value = "事件描述"),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer")
    })
    public RespMsg<IPage<BusieventDto>> getFirstLevelEventList(String bestat, Integer efbh, Integer etbh, String benm, String becnt,
                                                               @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current) {
        IPage<BusieventDto> eventList = depositService.getFirstLevelEventList(bestat, efbh, etbh, benm, becnt, size, current);
        return RespMsg.ok(eventList);
    }

    @PostMapping("get")
    @ApiOperation("获取事件数据（单个）")
    @ApiImplicitParam(name = "beid", required = true, value = "事件ID")
    public RespMsg<Busievent> get(Integer beid) {
        Busievent busievent = busieventService.getOne(Condition.<Busievent>create().eq(Busievent.BEID, beid));
        return RespMsg.ok(busievent);
    }

    @PostMapping("save")
    @ApiOperation("新增事件数据")
    public RespMsg<Busievent> saveOne(Busievent busievent) {
        Optional.ofNullable(busievent).ifPresent(s -> busieventService.save(s));
        return RespMsg.ok(busievent);
    }

    @PostMapping("update")
    @ApiOperation("修改单个事件数据")
    @Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
    public RespMsg<Busievent> updateOne(Busievent busievent) {
        Optional.ofNullable(busievent).ifPresent(s -> busieventService.updateById(s));
        return RespMsg.ok(busievent);
    }

    @PostMapping("removeById")
    @ApiOperation("删除单个事件数据")
    @ApiImplicitParam(name = "beid", required = true, value = "事件ID")
    public RespMsg delete(Integer beid) {
        Optional.ofNullable(beid).ifPresent(s -> busieventService.remove(Condition.<Busievent>create().eq(Busievent.BEID, s)));
        return RespMsg.ok();
    }

}
