package com.digitalchina.admin.mid.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.entity.SysDict;
import com.digitalchina.modules.entity.SysDictItem;
import com.digitalchina.modules.service.SysDictItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <数据字典管理>
 * 
 * @author lihui
 * @since 2020年4月23日
 */
@RestController
@RequestMapping("/admin/sysDict")
//@Authorize
@Api(tags = "数据字典")
public class SysDictController {

	@Autowired
	private SysDictItemService sysDictItemService;

	@ApiOperation("数据字典分页")
	@PostMapping(value = "list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "编码"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer", required = false), })
	public RespMsg<IPage<SysDictItem>> list(@RequestParam String code, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {

		QueryWrapper<SysDictItem> cd = Condition.<SysDictItem>create().eq(SysDictItem.DICT_CODE, code);
		return RespMsg.ok(sysDictItemService.page(new Page<>(current, size), cd));
	}

	@ApiOperation("数据字典列表")
	@GetMapping(value = "all")
	@ApiImplicitParam(name = "code", value = "编码")
	public RespMsg<List<SysDictItem>> list(@RequestParam String code) {

		QueryWrapper<SysDictItem> cd = Condition.<SysDictItem>create().eq(SysDictItem.DICT_CODE, code);
		return RespMsg.ok(sysDictItemService.list(cd));
	}

	@ApiOperation("数据字典增加")
	@PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<Void> save(@Valid SysDictItem dict) {
		// 获取最大编码
		QueryWrapper<SysDictItem> cd = Condition.<SysDictItem>create().eq(SysDictItem.DICT_CODE, dict.getDictCode());
		SysDictItem item = sysDictItemService.getOne(
				cd.and(wrapper -> wrapper.eq(SysDictItem.ITEM_NAME, dict.getItemName()).or().eq(SysDictItem.ITEM_VALUE, dict.getItemValue())));
		if (null == dict.getId()) { // 新增
			dict.setCreateTime(new Date());
			if (null != item) {
				return RespMsg.error(999,"字典项名称或值已存在");
			}
		}
		dict.setUpdateTime(new Date());
		sysDictItemService.saveOrUpdate(dict);
		return RespMsg.ok();
	}

	@ApiOperation("数据字典删除")
	@PostMapping(value = "remove", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "编码"),
			@ApiImplicitParam(name = "id", value = "字典项id") })
	public RespMsg<IPage<SysDict>> remove(@RequestParam String code, @RequestParam Integer id) {

		QueryWrapper<SysDictItem> cd = Condition.<SysDictItem>create().eq(SysDictItem.DICT_CODE, code);
		sysDictItemService.remove(cd.eq(SysDictItem.ID, id));
		return RespMsg.ok();
	}

}
