package com.digitalchina.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalchina.common.dto.DeptDto;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.dto.SysDictDto;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.service.SysDeptService;
import com.digitalchina.modules.service.SysDictItemService;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 文件服务
 * 
 * @author warrior
 *
 * @since 2019年9月23日
 */

@Api(tags = "数据字典")
@RestController
@RequestMapping("dict")
public class SysDictController {

	@Autowired
	private SysDictItemService sdis;

	@Autowired
	private SysDeptService sds;

	@GetMapping("list")
	@ApiOperation(value = "查找数据字典")
	@Authorize
	@ApiImplicitParam(name = "code", value = "字典code", dataType = "String", required = true)
	public RespMsg<List<SysDictDto>> list(String code) {
		return RespMsg.ok(sdis.findByCode(code));

	}

	@GetMapping("dept/list")
	@ApiOperation(value = "单位部门")
	@Authorize
	public RespMsg<List<DeptDto>> deptList() {
		List<DeptDto> result = new ArrayList<>();
		List<SysDept> list = sds.list(null);
		if (CollUtil.isNotEmpty(list)) {
			result = list.stream().map(item -> {
				DeptDto dto = new DeptDto();
				dto.setId(item.getDpid());
				dto.setPid(item.getBdpntid());
				dto.setName(item.getBdnm());
				return dto;
			}).collect(Collectors.toList());
		}
		return RespMsg.ok(DeptDto.makeTree(result));

	}

}
