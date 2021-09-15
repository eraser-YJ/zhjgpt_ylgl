package com.digitalchina.admin.mid.controller.warn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.entity.Cpsplst;
import com.digitalchina.admin.mid.service.CpsplstService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 组合属性清单 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Api(tags = "组合属性清单管理")
@Authorize
@RestController
@RequestMapping("/admin/cpsplst")
public class CpsplstController {

	@Autowired
	private CpsplstService cpsplstService;

	@PostMapping("list")
	@ApiOperation("获取组合属性清单数据（列表）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "cplnm", value = "属性名"),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "Integer", defaultValue = "10"),
			@ApiImplicitParam(name = "current", value = "第几页,默认第一页", dataType = "Integer") })
	public RespMsg<IPage<Cpsplst>> list(String cplnm, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(defaultValue = "1") Integer current) {

		IPage<Cpsplst> page = new Page<>(current, size);
		QueryWrapper<Cpsplst> wrapper = new QueryWrapper<Cpsplst>();
		wrapper.like(ObjectUtil.isNotEmpty(cplnm), Cpsplst.CPLNM, cplnm);
		wrapper.orderByDesc(Cpsplst.CPLID);
		IPage<Cpsplst> cpsplsts = cpsplstService.page(page, wrapper);
		return RespMsg.ok(cpsplsts);
	}

	@PostMapping("get")
	@ApiOperation("获取组合属性清单数据（单个）")
	@ApiImplicitParam(name = "cplid", required = true, value = "组合属性清单ID")
	public RespMsg<Cpsplst> get(Integer cplid) {
		Cpsplst cpsplst = cpsplstService.getOne(Condition.<Cpsplst>create().eq(Cpsplst.CPLID, cplid));
		return RespMsg.ok(cpsplst);
	}

	@PostMapping("save")
	@ApiOperation("新增组合属性清单数据")
	@Transactional
	public RespMsg<Cpsplst> saveOne(Cpsplst cpsplst) {
		Optional.ofNullable(cpsplst).ifPresent(s -> cpsplstService.save(s));
		return RespMsg.ok(cpsplst);
	}

	@PostMapping("update")
	@ApiOperation("修改单个组合属性清单数据")
	@Transactional
	public RespMsg<Cpsplst> updateOne(Cpsplst cpsplst) {
		Optional.ofNullable(cpsplst).ifPresent(s -> cpsplstService.updateById(s));
		return RespMsg.ok(cpsplst);
	}

	@PostMapping("removeById")
	@ApiOperation("删除单个组合属性清单数据")
	@ApiImplicitParam(name = "cplid", required = true, value = "组合属性清单ID")
	public RespMsg<Void> delete(Integer cplid) {
		Optional.ofNullable(cplid)
				.ifPresent(s -> cpsplstService.remove(Condition.<Cpsplst>create().eq(Cpsplst.CPLID, s)));
		return RespMsg.ok();
	}

}
