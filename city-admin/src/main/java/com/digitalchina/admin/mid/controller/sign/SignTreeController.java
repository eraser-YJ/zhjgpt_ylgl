package com.digitalchina.admin.mid.controller.sign;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digitalchina.admin.mid.dto.NodeWeightDto;
import com.digitalchina.admin.mid.dto.RelatedINodesDto;
import com.digitalchina.admin.mid.dto.RelatedItemDto;
import com.digitalchina.admin.mid.dto.SignTreeDto;
import com.digitalchina.admin.mid.dto.SignTreeDto2;
import com.digitalchina.admin.mid.dto.SongNodesDto;
import com.digitalchina.admin.mid.entity.SignQuota;
import com.digitalchina.admin.mid.entity.SignTree;
import com.digitalchina.admin.mid.mapping.NodeWeightDtoMapping;
import com.digitalchina.admin.mid.mapping.RelatedItemDtoMapping;
import com.digitalchina.admin.mid.mapping.SignTreeDtoMapping;
import com.digitalchina.admin.mid.mapping.SignTreeMapping;
import com.digitalchina.admin.mid.service.SignQuotaService;
import com.digitalchina.admin.mid.service.SignTreeService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.GeneralStateEnum;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 体征指标树 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signTree")
//@Authorize
@Api(tags = "体征指标树管理接口")
public class SignTreeController {
	@Autowired
	private SignTreeService signTreeService;
	@Autowired
	private SignQuotaService signQuotaService;
	@Autowired
	private SignTreeDtoMapping signTreeDtoMapping;
	@Autowired
	private NodeWeightDtoMapping nodeWeightDtoMapping;
	@Autowired
	private RelatedItemDtoMapping relatedItemDtoMapping;
	@Autowired
	private SignTreeMapping signTreeMapping;

	public static final String NCODE_PREFIX = "S";

	@GetMapping("find")
	@ApiOperation("获取体征树指标（单个）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "Integer", required = true, value = "主键"),
			@ApiImplicitParam(name = "type", dataType = "Integer", required = false, value = "是否底层指标（0否 1是）;默认0,会做数据的裁剪，不想暴露太多") })
	public RespMsg get(Integer id, @RequestParam(defaultValue = "0") Integer type) {
		SignTree signTree = signTreeService.getById(id);
		if (type == 0) {
			// 裁剪数据
			return RespMsg.ok(signTreeDtoMapping.from(signTree));
		}
		return RespMsg.ok(signTree);
	}

	@PostMapping("addRoot")
	@ApiOperation("新增根节点")
	public RespMsg addRoot(@Validated(SignTreeDto.root.class) @RequestBody SignTreeDto signTreeDto) {
		SignTree entity = signTreeDtoMapping.to(signTreeDto);
		int count = signTreeService.count(Condition.<SignTree>lambda().eq(SignTree::getTid, entity.getTid()));
		if (count > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该版本已存在根节点，不可新增根节点！");
		}
		initializeNode(entity);
		entity.setWeight(BigDecimal.ONE);
		entity.setNlevel(1);
		entity.setNcode(signTreeService.getNcode(NCODE_PREFIX));
		return RespMsg.ok(signTreeService.save(entity));
	}

	@PostMapping("addNode")
	@ApiOperation("新增子节点")
	public RespMsg addNode(@Validated(SignTreeDto.node.class) @RequestBody SignTreeDto signTreeDto) {
		SignTree entity = signTreeDtoMapping.to(signTreeDto);
		int count = signTreeService.count(Condition.<SignTree>lambda().eq(SignTree::getTid, entity.getTid())
				.eq(SignTree::getNname, entity.getNname()));
		if (count > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "该版本指标名称已存在,不可重复录入！");
		}
		SignTree signTree = signTreeService.getOne(Condition.<SignTree>lambda().eq(SignTree::getTid, entity.getTid())
				.eq(SignTree::getId, entity.getTfid()));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选选的父节点不存在,请刷新列表！");
		}
		initializeNode(entity);
		entity.setNlevel(signTree.getNlevel() + 1);
		entity.setTfids(signTree.getOwnPath());
		entity.setNcode(signTreeService.getNcode(NCODE_PREFIX));
		return RespMsg.ok(signTreeService.save(entity));
	}

	/**
	 * 初始化默认属性
	 * 
	 * @param entity
	 */
	private void initializeNode(SignTree entity) {
		Date now = new Date();
		entity.setWeight(BigDecimal.ZERO);
		entity.setIsarea(GeneralStateEnum.NO.getCode());
		entity.setIskey(GeneralStateEnum.NO.getCode());
		entity.setStatus(GeneralStateEnum.YES.getCode());
		entity.setType(GeneralStateEnum.NO.getCode());
		entity.setCrdt(now);
		entity.setModt(now);
	}

	@PostMapping("update")
	@ApiOperation("修改单个体征树非底层指标信息")
	public RespMsg updateOne(@Valid @RequestBody SignTreeDto signTreeDto) {
		SignTree entity = signTreeDtoMapping.to(signTreeDto);
		int count = signTreeService.count(Condition.<SignTree>lambda().ne(SignTree::getId, entity.getId())
				.eq(SignTree::getTid, entity.getTid()).eq(SignTree::getNname, entity.getNname()));
		if (count > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "指标名称已存在,不可重复录入！");
		}
		SignTree signTree = signTreeService.getOne(Condition.<SignTree>lambda().eq(SignTree::getId, entity.getId()));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请刷新列表！");
		}
		if (signTree.getType() == 1) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "底层指标不可修改!");
		}
		entity.setModt(new Date());
		return RespMsg.ok(signTreeService.updateById(entity));
	}

	@GetMapping("removeById")
	@ApiOperation("删除体征树节点;该节点下有子节点时，会删除其子树")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	@ApiImplicitParam(name = "id", value = "主键ID", dataType = "Integer", required = true)
	public RespMsg delete(Integer id) {
		LambdaQueryWrapper<SignTree> lambdaQueryWrapper = Condition.<SignTree>lambda().eq(SignTree::getId, id);
		SignTree signTree = signTreeService.getOne(lambdaQueryWrapper);
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请刷新列表！");
		}
		lambdaQueryWrapper.or().likeRight(SignTree::getTfids, signTree.getOwnPath());
		signTreeService.remove(lambdaQueryWrapper);
		if (null == signTree.getTfid()) {
			// 把根节点删除
			return RespMsg.ok();
		}
		// 更新 父路径上的变化
		List<SignTree> songlist = signTreeService
				.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, signTree.getTfid()));
		signTreeService.updateByChangeNode(signTreeService.getById(signTree.getTfid()),
				signTreeService.getIsAreaByChild(songlist), signTreeService.getIskeyByChild(songlist));
		return RespMsg.ok();
	}

	@GetMapping("childWeights")
	@ApiOperation("查询子节点权重")
	@ApiImplicitParam(name = "id", value = "父节点id", dataType = "Integer", required = true)
	public RespMsg<SongNodesDto> childWeights(Integer id) {
		List<SignTree> songlist = signTreeService.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, id));
		return RespMsg.ok(SongNodesDto.builder().pid(id).childList(nodeWeightDtoMapping.from(songlist)).build());
	}

	@PostMapping("adjustWeights")
	@ApiOperation("调整子节点权重")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg adjustWeights(@Valid @RequestBody SongNodesDto songNodesDto) {
		List<SignTree> songlist = signTreeService
				.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, songNodesDto.getPid()));
		if (ObjectUtil.isEmpty(songlist) || songlist.size() != songNodesDto.getChildList().size()) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "子节点已变动,请重新打开！");
		}
		List<SignTree> updateList = new ArrayList<>(songNodesDto.getChildList().size());
		for (NodeWeightDto node : songNodesDto.getChildList()) {
			updateList.add(SignTree.builder().id(node.getId()).weight(new BigDecimal(node.getWeight())).build());
		}
		BigDecimal total = updateList.stream().map(SignTree::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
		if (total.compareTo(BigDecimal.ONE) != 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "总权重不为1！");
		}
		return RespMsg.ok(signTreeService.updateBatchById(updateList));
	}

	@GetMapping("queryRelatedItems")
	@ApiOperation("查询关联项")
	@ApiImplicitParam(name = "id", value = "父节点id", dataType = "Integer", required = true)
	public RespMsg<RelatedINodesDto> queryRelatedItems(Integer id) {
		SignTree signTree = signTreeService.getOne(Condition.<SignTree>lambda().eq(SignTree::getId, id));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请刷新列表！");
		}

		List<SignTree> checklist = signTreeService.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, id));
		List<SignQuota> unchecklist = signQuotaService
				.list(Condition.<SignQuota>lambda().eq(SignQuota::getStatus, GeneralStateEnum.YES.getCode()));
		// 过滤
		if (ObjectUtil.isNotEmpty(checklist) && ObjectUtil.isNotEmpty(unchecklist)) {
			List<Integer> checkIds = checklist.stream().map(SignTree::getId).collect(Collectors.toList());
			unchecklist = unchecklist.stream().filter(q -> !checkIds.contains(q.getId())).collect(Collectors.toList());
		}
		return RespMsg.ok(RelatedINodesDto.builder().pid(id).ncode(signTree.getNcode()).nname(signTree.getNname())
				.checkList(relatedItemDtoMapping.fromSignTreeList(checklist))
				.uncheckList(relatedItemDtoMapping.fromSignQuotaList(unchecklist)).build());
	}

	@PostMapping("submitRelatedItems")
	@ApiOperation("提交关联项")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg submitRelatedItems(@Valid @RequestBody RelatedINodesDto relatedINodesDto) {
		SignTree signTree = signTreeService
				.getOne(Condition.<SignTree>lambda().eq(SignTree::getId, relatedINodesDto.getPid()));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "所选记录不存在,请返回刷新列表！");
		}
		signTreeService.remove(Condition.<SignTree>lambda().eq(SignTree::getTfid, relatedINodesDto.getPid()));
		List<RelatedItemDto> checkList = relatedINodesDto.getCheckList();
		if (ObjectUtil.isEmpty(checkList)) {
			signTreeService.updateByChangeNode(signTree, 0, 0);
			return RespMsg.ok();
		}
		List<String> ncodeList = checkList.stream().map(RelatedItemDto::getNcode).collect(Collectors.toList());
		List<SignQuota> signQuotalist = signQuotaService
				.list(Condition.<SignQuota>lambda().in(SignQuota::getNcode, ncodeList));
		List<SignTree> signTreeLsit = signTreeMapping.from(signQuotalist);
		for (SignTree s : signTreeLsit) {
			// 初始化
			s.setWeight(BigDecimal.ZERO);
			s.setStatus(GeneralStateEnum.YES.getCode());
			s.setType(GeneralStateEnum.YES.getCode());
			s.setTid(signTree.getTid());
			s.setTfid(signTree.getId());
			s.setNlevel(signTree.getNlevel() + 1);
			s.setTfids(signTree.getOwnPath());
		}
		for (SignTree signTree2 : signTreeLsit) {
			signTreeService.save(signTree2);
		}
		// 下面更新路径上的变化
		Integer isAera = signTreeService.getIsAreaByChild(signTreeLsit);
		Integer isKey = signTreeService.getIskeyByChild(signTreeLsit);
		// 两者都是1,父路径上必然也都是1
		if (isAera.equals(1) && isKey.equals(1)) {
			List<Integer> ids = Pattern.compile(SignTree.PATH_SEPARATOR).splitAsStream(signTree.getOwnPath())
					.map(x -> Integer.parseInt(x)).collect(Collectors.toList());
			signTreeService.update(SignTree.builder().isarea(1).iskey(1).build(),
					Condition.<SignTree>lambda().in(SignTree::getId, ids));
			return RespMsg.ok();
		}
		signTreeService.updateByChangeNode(signTree, isAera, isKey);
		return RespMsg.ok();
	}

	@GetMapping("querySignTree")
	@ApiOperation("获取体征树或子树")
	@ApiImplicitParams({ @ApiImplicitParam(name = "tid", dataType = "Integer", required = true, value = "树版本id"),
			@ApiImplicitParam(name = "id", dataType = "Integer", required = false, value = "根节点id,不传查整棵树") })
	public RespMsg<List<SignTreeDto2>> querySignTree(Integer tid, @RequestParam(required = false) Integer id) {
		String tfids = null;
		if (null != id) {
			tfids = signTreeService.getById(id).getOwnPath();
		}
		return RespMsg.ok(SignTreeDto2.makeTree(signTreeService.queryTreeByroot(tid, id, tfids)));
	}

	@ApiOperation(value = "更新体征树排序,仅允许同级调整")
	@PostMapping(value = "sort")
	public RespMsg updateSignTreeSort(@RequestBody List<SignTree> signTreeList) {
		List<SignTree> list = new ArrayList<>();
		signTreeList.stream().forEach(item -> {
			SignTree signTree = new SignTree();
			signTree.setId(item.getId());
			signTree.setSort(item.getSort());
			list.add(signTree);
		});
		return RespMsg.ok(signTreeService.updateBatchById(list));
	}
}
