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
 * ??????????????? ???????????????
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@RestController
@RequestMapping("admin/signTree")
//@Authorize
@Api(tags = "???????????????????????????")
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
	@ApiOperation("?????????????????????????????????")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "Integer", required = true, value = "??????"),
			@ApiImplicitParam(name = "type", dataType = "Integer", required = false, value = "?????????????????????0??? 1??????;??????0,??????????????????????????????????????????") })
	public RespMsg get(Integer id, @RequestParam(defaultValue = "0") Integer type) {
		SignTree signTree = signTreeService.getById(id);
		if (type == 0) {
			// ????????????
			return RespMsg.ok(signTreeDtoMapping.from(signTree));
		}
		return RespMsg.ok(signTree);
	}

	@PostMapping("addRoot")
	@ApiOperation("???????????????")
	public RespMsg addRoot(@Validated(SignTreeDto.root.class) @RequestBody SignTreeDto signTreeDto) {
		SignTree entity = signTreeDtoMapping.to(signTreeDto);
		int count = signTreeService.count(Condition.<SignTree>lambda().eq(SignTree::getTid, entity.getTid()));
		if (count > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "??????????????????????????????????????????????????????");
		}
		initializeNode(entity);
		entity.setWeight(BigDecimal.ONE);
		entity.setNlevel(1);
		entity.setNcode(signTreeService.getNcode(NCODE_PREFIX));
		return RespMsg.ok(signTreeService.save(entity));
	}

	@PostMapping("addNode")
	@ApiOperation("???????????????")
	public RespMsg addNode(@Validated(SignTreeDto.node.class) @RequestBody SignTreeDto signTreeDto) {
		SignTree entity = signTreeDtoMapping.to(signTreeDto);
		int count = signTreeService.count(Condition.<SignTree>lambda().eq(SignTree::getTid, entity.getTid())
				.eq(SignTree::getNname, entity.getNname()));
		if (count > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "??????????????????????????????,?????????????????????");
		}
		SignTree signTree = signTreeService.getOne(Condition.<SignTree>lambda().eq(SignTree::getTid, entity.getTid())
				.eq(SignTree::getId, entity.getTfid()));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "??????????????????????????????,??????????????????");
		}
		initializeNode(entity);
		entity.setNlevel(signTree.getNlevel() + 1);
		entity.setTfids(signTree.getOwnPath());
		entity.setNcode(signTreeService.getNcode(NCODE_PREFIX));
		return RespMsg.ok(signTreeService.save(entity));
	}

	/**
	 * ?????????????????????
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
	@ApiOperation("??????????????????????????????????????????")
	public RespMsg updateOne(@Valid @RequestBody SignTreeDto signTreeDto) {
		SignTree entity = signTreeDtoMapping.to(signTreeDto);
		int count = signTreeService.count(Condition.<SignTree>lambda().ne(SignTree::getId, entity.getId())
				.eq(SignTree::getTid, entity.getTid()).eq(SignTree::getNname, entity.getNname()));
		if (count > 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "?????????????????????,?????????????????????");
		}
		SignTree signTree = signTreeService.getOne(Condition.<SignTree>lambda().eq(SignTree::getId, entity.getId()));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "?????????????????????,??????????????????");
		}
		if (signTree.getType() == 1) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "????????????????????????!");
		}
		entity.setModt(new Date());
		return RespMsg.ok(signTreeService.updateById(entity));
	}

	@GetMapping("removeById")
	@ApiOperation("?????????????????????;????????????????????????????????????????????????")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	@ApiImplicitParam(name = "id", value = "??????ID", dataType = "Integer", required = true)
	public RespMsg delete(Integer id) {
		LambdaQueryWrapper<SignTree> lambdaQueryWrapper = Condition.<SignTree>lambda().eq(SignTree::getId, id);
		SignTree signTree = signTreeService.getOne(lambdaQueryWrapper);
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "?????????????????????,??????????????????");
		}
		lambdaQueryWrapper.or().likeRight(SignTree::getTfids, signTree.getOwnPath());
		signTreeService.remove(lambdaQueryWrapper);
		if (null == signTree.getTfid()) {
			// ??????????????????
			return RespMsg.ok();
		}
		// ?????? ?????????????????????
		List<SignTree> songlist = signTreeService
				.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, signTree.getTfid()));
		signTreeService.updateByChangeNode(signTreeService.getById(signTree.getTfid()),
				signTreeService.getIsAreaByChild(songlist), signTreeService.getIskeyByChild(songlist));
		return RespMsg.ok();
	}

	@GetMapping("childWeights")
	@ApiOperation("?????????????????????")
	@ApiImplicitParam(name = "id", value = "?????????id", dataType = "Integer", required = true)
	public RespMsg<SongNodesDto> childWeights(Integer id) {
		List<SignTree> songlist = signTreeService.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, id));
		return RespMsg.ok(SongNodesDto.builder().pid(id).childList(nodeWeightDtoMapping.from(songlist)).build());
	}

	@PostMapping("adjustWeights")
	@ApiOperation("?????????????????????")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg adjustWeights(@Valid @RequestBody SongNodesDto songNodesDto) {
		List<SignTree> songlist = signTreeService
				.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, songNodesDto.getPid()));
		if (ObjectUtil.isEmpty(songlist) || songlist.size() != songNodesDto.getChildList().size()) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "??????????????????,??????????????????");
		}
		List<SignTree> updateList = new ArrayList<>(songNodesDto.getChildList().size());
		for (NodeWeightDto node : songNodesDto.getChildList()) {
			updateList.add(SignTree.builder().id(node.getId()).weight(new BigDecimal(node.getWeight())).build());
		}
		BigDecimal total = updateList.stream().map(SignTree::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
		if (total.compareTo(BigDecimal.ONE) != 0) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "???????????????1???");
		}
		return RespMsg.ok(signTreeService.updateBatchById(updateList));
	}

	@GetMapping("queryRelatedItems")
	@ApiOperation("???????????????")
	@ApiImplicitParam(name = "id", value = "?????????id", dataType = "Integer", required = true)
	public RespMsg<RelatedINodesDto> queryRelatedItems(Integer id) {
		SignTree signTree = signTreeService.getOne(Condition.<SignTree>lambda().eq(SignTree::getId, id));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "?????????????????????,??????????????????");
		}

		List<SignTree> checklist = signTreeService.list(Condition.<SignTree>lambda().eq(SignTree::getTfid, id));
		List<SignQuota> unchecklist = signQuotaService
				.list(Condition.<SignQuota>lambda().eq(SignQuota::getStatus, GeneralStateEnum.YES.getCode()));
		// ??????
		if (ObjectUtil.isNotEmpty(checklist) && ObjectUtil.isNotEmpty(unchecklist)) {
			List<Integer> checkIds = checklist.stream().map(SignTree::getId).collect(Collectors.toList());
			unchecklist = unchecklist.stream().filter(q -> !checkIds.contains(q.getId())).collect(Collectors.toList());
		}
		return RespMsg.ok(RelatedINodesDto.builder().pid(id).ncode(signTree.getNcode()).nname(signTree.getNname())
				.checkList(relatedItemDtoMapping.fromSignTreeList(checklist))
				.uncheckList(relatedItemDtoMapping.fromSignQuotaList(unchecklist)).build());
	}

	@PostMapping("submitRelatedItems")
	@ApiOperation("???????????????")
	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	public RespMsg submitRelatedItems(@Valid @RequestBody RelatedINodesDto relatedINodesDto) {
		SignTree signTree = signTreeService
				.getOne(Condition.<SignTree>lambda().eq(SignTree::getId, relatedINodesDto.getPid()));
		if (ObjectUtil.isEmpty(signTree)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "?????????????????????,????????????????????????");
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
			// ?????????
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
		// ??????????????????????????????
		Integer isAera = signTreeService.getIsAreaByChild(signTreeLsit);
		Integer isKey = signTreeService.getIskeyByChild(signTreeLsit);
		// ????????????1,???????????????????????????1
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
	@ApiOperation("????????????????????????")
	@ApiImplicitParams({ @ApiImplicitParam(name = "tid", dataType = "Integer", required = true, value = "?????????id"),
			@ApiImplicitParam(name = "id", dataType = "Integer", required = false, value = "?????????id,??????????????????") })
	public RespMsg<List<SignTreeDto2>> querySignTree(Integer tid, @RequestParam(required = false) Integer id) {
		String tfids = null;
		if (null != id) {
			tfids = signTreeService.getById(id).getOwnPath();
		}
		return RespMsg.ok(SignTreeDto2.makeTree(signTreeService.queryTreeByroot(tid, id, tfids)));
	}

	@ApiOperation(value = "?????????????????????,?????????????????????")
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
