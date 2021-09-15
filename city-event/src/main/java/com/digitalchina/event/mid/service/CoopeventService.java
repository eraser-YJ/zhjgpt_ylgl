package com.digitalchina.event.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.dto.eventdeposit.CoopeventBasicDto;
import com.digitalchina.event.dto.eventdeposit.CoopeventDto;
import com.digitalchina.event.mid.entity.Coopevent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 协查事件 服务类
 * </p>
 *
 * @author lichunlong
 * @since 2019-09-15
 */
public interface CoopeventService extends IService<Coopevent> {

    IPage<CoopeventDto> countCreateCoop(Integer curid, Integer size, Integer current);

    IPage<CoopeventDto> countReceiveCoop(Integer curid, Integer size, Integer current);

    List<Map<String, Object>> eventDepositInfo(Integer ceid);

    void submitSecond(Integer beid, String reason);

    void secondPass(Integer beid, String reason);

    void firstPass(Integer beid, String reason);

    void submitFirst(Integer beid, String reason);

    void secondRefuse(Integer beid, String reason);

    void firstRefuse(Integer beid, String reason);

    void serviceFallback(Integer beid, String reason);

    void serviceCancel(Integer beid, String reason);

    void serviceClose(Integer ceid, String reason);

    CoopeventBasicDto getBasicinfo(Integer ceid);

    void updateGis(Integer ceid, String bexy);
}
