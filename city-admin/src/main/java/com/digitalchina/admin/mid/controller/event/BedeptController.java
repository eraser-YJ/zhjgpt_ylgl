package com.digitalchina.admin.mid.controller.event;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.event.Bedept;
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.dto.BedeptDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author liuping
 * @since 2019-09-12
 */
@Api(tags = "事件系统部门管理接口")
@RestController
//@Authorize
@RequestMapping("/admin/bedept")
@Deprecated
public class BedeptController {

	@Autowired
	private CityEventService cityEventService;

	@PostMapping(value = "saveorupdate")
	@ApiOperation(value = "创建或者更新部门信息")
	public RespMsg<Void> saveOrUpdate(@RequestBody @Valid Bedept bedept) {
		return cityEventService.saveOrUpdateBedept(bedept);
	}

	@GetMapping("find")
	@ApiOperation(value = "查找单个部门信息")
	@ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
	public RespMsg<Bedept> find(@RequestParam(required = true) Integer id) {
		return cityEventService.findBedept(id);
	}

	@GetMapping(value = "delete")
	@ApiOperation(value = "删除单个部门信息", notes = "检查了其它表是否使用了，删除可能会失败")
	@ApiImplicitParam(name = "id", value = "主键id", dataType = "Integer", required = true)
	public RespMsg del(@RequestParam(required = true) Integer id) {
		return cityEventService.delBedept(id);
	}

	@ApiOperation(value = "展示部门树")
	@GetMapping("all")
	public RespMsg<List<BedeptDto>> findAll() {
		return cityEventService.findAllBedept();
	}

	@ApiOperation(value = "获取子部门列表")
	@GetMapping("sons")
	@ApiImplicitParam(name = "bedid", value = "父级部门id", dataType = "Integer", required = true)
	public RespMsg<List<Bedept>> findSons(Integer bedid) {
		return cityEventService.findSonsBedept(bedid);
	}

	@ApiOperation(value = "展示一二级部门树")
	@GetMapping("1or2")
	public RespMsg<List<BedeptDto>> find1or2() {
		return cityEventService.find1or2();
	}

	@GetMapping("query")
	@ApiOperation(value = "分页查询部门信息列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "bdnm", value = "部门名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "bdtype", value = "部门类型", dataType = "Integer", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
			@ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true) })
	public RespMsg<Page<Bedept>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
			@RequestParam(defaultValue = "1", required = true) Integer current,
			@RequestParam(required = false) String bdnm, @RequestParam(required = false) Integer bdtype) {
		return cityEventService.queryBedept(size, current, bdnm, bdtype);
	}

}
