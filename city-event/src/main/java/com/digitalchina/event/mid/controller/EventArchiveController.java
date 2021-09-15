package com.digitalchina.event.mid.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Befrom;
import com.digitalchina.event.mid.entity.Betype;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.BefromService;
import com.digitalchina.event.mid.service.BetypeService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.modules.constant.enums.EventEnum;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 事件归档前端控制器
 * @author lzy
 * @since 2019/9/10
 */
@Api(tags = "事件归档")
@Authorize
@RestController
@RequestMapping("event/archive")
public class EventArchiveController {
    @Autowired
    private BusieventService busieventService;
    @Autowired
    private BetypeService betypeService;
    @Autowired
    private BefromService befromService;
    @Autowired
    private BedeptService bedeptService;

    @GetMapping("list")
    @ApiOperation("事件归档列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = false),
            @ApiImplicitParam(name = "current", value = "第几页，默认1页", dataType = "int", required = false),
            @ApiImplicitParam(name = "etbh", value = "事件类型", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "efbh", value = "事件来源", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "bepcdpt0", value = "责任部门", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "clsdtStart", value = "归档时间起(yyyy-MM-dd)", dataType = "String", required = false),
            @ApiImplicitParam(name = "clsdtEnd", value = "归档时间止(yyyy-MM-dd)", dataType = "String", required = false),
            @ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false)})
    public RespMsg<IPage<Busievent>> list(@RequestParam(defaultValue = "10") Integer size,@RequestParam(defaultValue = "1") Integer current,
                                          Integer etbh, Integer efbh,Integer bepcdpt0,String clsdtStart,String clsdtEnd,String keyword) {
        IPage<Busievent> page = new Page<>(current, size);
        QueryWrapper<Busievent> busieventWrapper = new QueryWrapper<>();
        busieventWrapper.eq(ObjectUtil.isNotEmpty(etbh),Busievent.ETBH, etbh);
        busieventWrapper.eq(ObjectUtil.isNotEmpty(efbh),Busievent.EFBH, efbh);
        busieventWrapper.eq(ObjectUtil.isNotEmpty(bepcdpt0),Busievent.RECDPT, bepcdpt0);
        if(StringUtils.isNotBlank(clsdtStart)){
            clsdtStart+=" 00:00:00";
            busieventWrapper.ge(Busievent.CLSDT,DateUtil.parse(clsdtStart));
        }
        if(StringUtils.isNotBlank(clsdtEnd)){
            clsdtEnd+=" 23:59:59";
            busieventWrapper.le(Busievent.CLSDT,DateUtil.parse(clsdtEnd));
        }
        busieventWrapper.eq(Busievent.BESTAT,EventEnum.FINISH_STAT.getValue());
        if(StringUtils.isNotBlank(keyword)) {
            busieventWrapper.and(wrapper -> wrapper.like(Busievent.BENM, keyword).or().like(Busievent.BECNT, keyword).or().like(Busievent.BEBH, keyword));
        }
        busieventWrapper.orderByDesc(Busievent.CLSDT);
        IPage<Busievent> busieventList = busieventService.page(page, busieventWrapper);
        addSeqForBusievent(busieventList,size,current);
        return RespMsg.ok(busieventList);
    }

    private void addSeqForBusievent(IPage<Busievent> undoTaskList, Integer size, Integer current) {
        Optional.ofNullable(undoTaskList).ifPresent(dtoList -> {
            int i = 1;
            for (Busievent dto : dtoList.getRecords()) {
                dto.setSeq((current - 1) * size + i);
                i++;
                Betype betype=betypeService.getById(dto.getEtbh());
                if(betype!=null){
                    dto.setEtbhName(betype.getEtnm());
                }
                Befrom befrom=befromService.getById(dto.getEfbh());
                if(befrom!=null){
                    dto.setEfbhName(befrom.getEfnm());
                }
                Bedept bedept=bedeptService.getById(dto.getRecdpt());
                if(bedept!=null){
                    dto.setBesrcdpt0Name(bedept.getBdnm());
                }
            }
        });
    }

    @GetMapping("getBusieventBasicInfoDtoById")
    @ApiOperation("档案详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "beid", value = "事件ID", dataType = "Integer", required = true)})
    public RespMsg<BusieventBasicInfoDto> getBusieventById(Integer beid) {
        return RespMsg.ok(busieventService.getBusieventBasicInfoDtoById(beid));
    }

}
