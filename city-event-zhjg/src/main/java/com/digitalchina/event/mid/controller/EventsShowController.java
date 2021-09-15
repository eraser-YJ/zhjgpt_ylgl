package com.digitalchina.event.mid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.dto.EventDirectoryTreeDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.mid.entity.Admdiv;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Betype;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.service.AdmdivService;
import com.digitalchina.event.mid.service.BedeptService;
import com.digitalchina.event.mid.service.BetypeService;
import com.digitalchina.event.mid.service.BusieventService;
import com.digitalchina.event.mid.service.EventDepositService;
import com.digitalchina.modules.constant.enums.EventEnum;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 事件展示服务控制器
 *
 * @author lzy
 * @since 2019/9/9
 */
@Api(tags = "事件展示服务")
@Authorize
@RestController
@RequestMapping("event/display")
public class EventsShowController {
	@Autowired
	private AdmdivService admdivService;
	@Autowired
	private BedeptService bedeptService;
	@Autowired
	private BetypeService betypeService;
	@Autowired
	private BusieventService busieventService;
	@Autowired
	private EventDepositService depositService;

	@GetMapping("getEventDirectoryTree")
	@ApiOperation("城市事件目录结构树")
	public RespMsg<List<EventDirectoryTreeDto>> getEventDirectoryTree() {
		// 事件状态(前端映射状态)
		List<Object> statusList = CollUtil.toList(EventEnum.UNHANDLE.getValue(), EventEnum.ALLOCATING.getValue(),
				EventEnum.HANDLING.getValue(), EventEnum.UNCHECKED.getValue(), EventEnum.REFUSE.getValue(),
				EventEnum.UPGRADE.getValue(), EventEnum.CANCEL.getValue(), EventEnum.FINISH.getValue());

		// 获取区域数据
		List<Admdiv> admdivList = admdivService
				.list(Condition.<Admdiv>create().eq(Admdiv.ADLEV, 2).orderByAsc(Admdiv.CRDT));
		// 获取部门数据
		List<Bedept> bedeptList = bedeptService
				.list(Condition.<Bedept>create().eq(Bedept.BDTYPE, 2).orderByAsc(Bedept.BEDID));
		// 获取事项类型数据
		List<Betype> betypeList = betypeService.list(Condition.<Betype>create().orderByAsc(Betype.CRDT));

		List<EventDirectoryTreeDto> eventDirectoryTreeDtoList = new ArrayList<>();
		// 添加一级事件目录
		EventDirectoryTreeDto eventDirectoryTreeDto = new EventDirectoryTreeDto();
		eventDirectoryTreeDto.setId(-1 + "a");
		eventDirectoryTreeDto.setName("事件分类");
		eventDirectoryTreeDto.setType(0);
		eventDirectoryTreeDtoList.add(eventDirectoryTreeDto);

		// 添加二级事件目录区域
		EventDirectoryTreeDto eventDirectoryTreeDtoAdmdiv = new EventDirectoryTreeDto();
		eventDirectoryTreeDtoAdmdiv.setId(-2 + "b");
		eventDirectoryTreeDtoAdmdiv.setName("长春新区");
		eventDirectoryTreeDtoAdmdiv.setPid(eventDirectoryTreeDto.getId());
		eventDirectoryTreeDtoAdmdiv.setType(1);
		List<EventDirectoryTreeDto> admdivLists = new ArrayList<>();
		for (Admdiv admdiv : admdivList) {
			EventDirectoryTreeDto admdivDto = new EventDirectoryTreeDto();
			admdivDto.setId(admdiv.getAdid() + "b");
			admdivDto.setName(admdiv.getAdnm());
			admdivDto.setPid(eventDirectoryTreeDtoAdmdiv.getId());
			admdivDto.setType(1);
			admdivLists.add(admdivDto);
		}
		eventDirectoryTreeDtoAdmdiv.setChildren(admdivLists);
		eventDirectoryTreeDtoList.add(eventDirectoryTreeDtoAdmdiv);

		// 添加二级事件目录部门
		EventDirectoryTreeDto eventDirectoryTreeDtoBedept = new EventDirectoryTreeDto();
		eventDirectoryTreeDtoBedept.setId(-3 + "c");
		eventDirectoryTreeDtoBedept.setName("全部部门");
		eventDirectoryTreeDtoBedept.setPid(eventDirectoryTreeDto.getId());
		eventDirectoryTreeDtoBedept.setType(2);
		List<EventDirectoryTreeDto> bedeptLists = new ArrayList<>();
		for (Bedept bedept : bedeptList) {
			EventDirectoryTreeDto bedeptDto = new EventDirectoryTreeDto();
			bedeptDto.setId(bedept.getBedid() + "c");
			bedeptDto.setName(bedept.getBdnm());
			if (null != bedept.getBdpntid()) {
				bedeptDto.setPid(bedept.getBdpntid() + "c");
			}
			bedeptDto.setType(2);
			bedeptLists.add(bedeptDto);
		}
		List<EventDirectoryTreeDto> eventDirectoryTreeDtos = EventDirectoryTreeDto.makeTree2(bedeptLists);
		for (EventDirectoryTreeDto e : eventDirectoryTreeDtos) {
			e.setPid(eventDirectoryTreeDtoBedept.getId());
		}
		eventDirectoryTreeDtoBedept.setChildren(eventDirectoryTreeDtos);
		eventDirectoryTreeDtoList.add(eventDirectoryTreeDtoBedept);

		// 添加二级事件目录类型
		EventDirectoryTreeDto eventDirectoryTreeDtoBetype = new EventDirectoryTreeDto();
		eventDirectoryTreeDtoBetype.setId(-4 + "d");
		eventDirectoryTreeDtoBetype.setName("全部类型");
		eventDirectoryTreeDtoBetype.setPid(eventDirectoryTreeDto.getId());
		eventDirectoryTreeDtoBedept.setType(3);
		List<EventDirectoryTreeDto> betypeLists = new ArrayList<>();
		for (Betype betype : betypeList) {
			EventDirectoryTreeDto betypeDto = new EventDirectoryTreeDto();
			betypeDto.setId(betype.getEtbh() + "d");
			betypeDto.setName(betype.getEtnm());
			betypeDto.setPid(eventDirectoryTreeDtoBetype.getId());
			betypeDto.setType(3);
			betypeLists.add(betypeDto);
		}
		eventDirectoryTreeDtoBetype.setChildren(betypeLists);
		eventDirectoryTreeDtoList.add(eventDirectoryTreeDtoBetype);

		// 添加状态图例
		EventDirectoryTreeDto eventDirectoryTreeDtoStatus = new EventDirectoryTreeDto();
		eventDirectoryTreeDtoStatus.setId(-5 + "e");
		eventDirectoryTreeDtoStatus.setName("全部图例");
		eventDirectoryTreeDtoStatus.setPid(eventDirectoryTreeDto.getId());
		eventDirectoryTreeDtoBedept.setType(4);
		List<EventDirectoryTreeDto> statusLists = new ArrayList<>();
		for (Object obj : statusList) {
			EventDirectoryTreeDto betypeDto = new EventDirectoryTreeDto();
			betypeDto.setId(String.valueOf(obj) + 'e');
			betypeDto.setName(String.valueOf(obj));
			betypeDto.setPid(eventDirectoryTreeDtoStatus.getId());
			betypeDto.setType(4);
			statusLists.add(betypeDto);
		}
		eventDirectoryTreeDtoStatus.setChildren(statusLists);
		eventDirectoryTreeDtoList.add(eventDirectoryTreeDtoStatus);

		//
		return RespMsg.ok(EventDirectoryTreeDto.makeTree(eventDirectoryTreeDtoList));
	}

	@GetMapping("getHomeBusieventList")
	@ApiOperation("事件搜索列表-全部")
	@ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false),
			@ApiImplicitParam(name = "adids", value = "区划ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "bepcdpt0s", value = "部门ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "etbhs", value = "事项类型ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "String[]", required = false) })
	public RespMsg<List<HomeBusieventDto>> getHomeBusieventList(String keyword, Integer[] adids, Integer[] bepcdpt0s,
			Integer[] etbhs, String[] status) {
		return RespMsg.ok(busieventService.getHomeBusieventList(keyword, adids, bepcdpt0s, etbhs, status));
	}

	@GetMapping("getHomeBusieventList/page")
	@ApiOperation("事件搜索列表-分页")
	@ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false),
			@ApiImplicitParam(name = "adids", value = "区划ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "bepcdpt0s", value = "部门ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "etbhs", value = "事项类型ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false) })
	public RespMsg<IPage<HomeBusieventDto>> getHomeBusieventListForPage(String keyword, Integer[] adids,
			Integer[] bepcdpt0s, Integer[] etbhs, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {
		IPage<Busievent> page = new Page<>(current, size);
		return RespMsg.ok(busieventService.getHomeBusieventList(page, keyword, adids, bepcdpt0s, etbhs));
	}

	@ApiOperation("首页事件统计列表")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("getHomeBusieventCountList")
	@ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false),
			@ApiImplicitParam(name = "adids", value = "区划ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "bepcdpt0s", value = "部门ID数组", dataType = "Integer[]", required = false),
			@ApiImplicitParam(name = "etbhs", value = "事项类型ID数组", dataType = "Integer[]", required = false) })
	public RespMsg<List<Map<String, Integer>>> getHomeBusieventCountList(String keyword, Integer[] adids,
			Integer[] bepcdpt0s, Integer[] etbhs) {
		List resultList = depositService.getHomeFirstLevelEventCount(keyword, adids, bepcdpt0s, etbhs);
		return RespMsg.ok(resultList);
	}

	@GetMapping("getBusieventById")
	@ApiOperation("事件属性气泡")
	@ApiImplicitParams({ @ApiImplicitParam(name = "beid", value = "事件ID", dataType = "Integer", required = true) })
	public RespMsg<HomeBusieventDto> getBusieventById(Integer beid) {
		HomeBusieventDto homeBusieventDto = busieventService.getBusieventById(beid);
		return RespMsg.ok(homeBusieventDto);
	}
}
