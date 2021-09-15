package com.digitalchina.event.midwarn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.midwarn.entity.Rptgen;
import com.digitalchina.event.midwarn.service.RptgenService;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <事件评价报告>
 * 
 * @author zhaoyan liang
 * @since 2019年9月17日
 */
@Api(tags = "事件评价-历史考核报告")
@Authorize
@RequestMapping("event/report")
@RestController
public class EventReportController {
	@Autowired
	private RptgenService rptgenService;

	@GetMapping("list")
	@ApiOperation("事件评价-历史考核报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = false),
			@ApiImplicitParam(name = "current", value = "第几页，默认1页", dataType = "int", required = false),
			@ApiImplicitParam(name = "rpitv", value = "报告类型（30月报，40季报，60年报）", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "crdt", value = "起止时间（yyyy/MM/dd - yyyy/MM/dd）", dataType = "String", required = false),
			@ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", required = false)})
	public RespMsg<IPage<Rptgen>> list(@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer current,
									   Integer rpitv,String crdt,String keyword) {
		return RespMsg.ok(rptgenService.rptgenList(size, current, rpitv, crdt,keyword));
	}

	@GetMapping("browse")
	@ApiOperation("事件评价-历史考核报告浏览")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rgid", value = "预警报告ID", dataType = "Integer", required = true) })
	public void browse(Integer rgid, HttpServletResponse response) {
		rptgenService.browse(rgid, response);
	}

}
