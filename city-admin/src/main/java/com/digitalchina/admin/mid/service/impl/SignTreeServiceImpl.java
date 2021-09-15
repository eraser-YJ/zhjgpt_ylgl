package com.digitalchina.admin.mid.service.impl;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.controller.sign.SignTreeController;
import com.digitalchina.admin.mid.dto.SignTreeDto2;
import com.digitalchina.admin.mid.entity.SignTree;
import com.digitalchina.admin.mid.mapper.SignTreeMapper;
import com.digitalchina.admin.mid.service.SignTreeService;
import com.digitalchina.common.exception.ServiceException;

import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@Service
public class SignTreeServiceImpl extends ServiceImpl<SignTreeMapper, SignTree> implements SignTreeService {

	@Override
	public void copyTree(Integer old, Integer curr) {
		List<SignTreeDto2> oldlist = baseMapper.queryTreeByTid(old);
		if (ObjectUtil.isEmpty(oldlist)) {
			return;
		}
		List<SignTreeDto2> oldtree = SignTreeDto2.makeTree(oldlist);
		if (oldtree.size() != 1) {
			throw new ServiceException("所选模板只能有一棵树！");
		}
		Map<Integer, SignTreeDto2> oldMap = oldlist.stream()
				.collect(Collectors.toMap(SignTreeDto2::getId, a -> a, (k1, k2) -> k1));
		Map<Integer, SignTree> newMap = new HashMap<>();
		Queue<SignTreeDto2> queue = new ArrayDeque<>();
		queue.offer(oldtree.get(0));
		while (!queue.isEmpty()) {
			SignTreeDto2 temp = queue.poll();
			SignTreeDto2 oldRecord = oldMap.get(temp.getId());
			SignTree newRecord = oldRecord.toSignTree();
			newRecord.setTid(curr);
			if (newRecord.getType() == 0) {
				newRecord.setNcode(getNcode(SignTreeController.NCODE_PREFIX));
			}
			if (null != oldRecord.getTfid()) {
				SignTree pnode = newMap.get(oldRecord.getTfid());
				newRecord.setTfid(pnode.getId());
				newRecord.setTfids(pnode.getOwnPath());
			} else {
				newRecord.setTfid(null);
				newRecord.setTfids(null);
			}
			baseMapper.insert(newRecord);
			newMap.put(oldRecord.getId(), newRecord);
			List<SignTreeDto2> childList = temp.getChildren();

			if (ObjectUtil.isNotEmpty(childList)) {
				for (SignTreeDto2 child : childList) {
					queue.offer(child);
				}
			}
		}
	}

	@Override
	public String getNcode(String prefix) {
		return prefix + IdWorker.getIdStr();
	}

	/**
	 * 递归更新
	 * 
	 * @param old    变动点旧值
	 * @param isArea 变动点isArea新值
	 * @param isKey  变动点isKey新值
	 */
	@Override
	public void updateByChangeNode(SignTree old, Integer isArea, Integer isKey) {
		if (null == old || (isArea.equals(old.getIsarea()) && isKey.equals(old.getIskey()))) {
			return;
		}
		baseMapper.updateById(SignTree.builder().id(old.getId()).isarea(isArea).iskey(isKey).build());
		while (null != old.getTfid()) {
			List<SignTree> songlist = baseMapper
					.selectList(Condition.<SignTree>lambda().eq(SignTree::getTfid, old.getTfid()));
			isArea = getIsAreaByChild(songlist);
			isKey = getIskeyByChild(songlist);
			old = baseMapper.selectById(old.getTfid());
			updateByChangeNode(old, isArea, isKey);
		}
	}

	@Override
	public Integer getIsAreaByChild(List<SignTree> signTreeLsit) {
		if (ObjectUtil.isEmpty(signTreeLsit)) {
			return Integer.valueOf(0);
		}
		return signTreeLsit.stream().map(q -> {
			if (q.getIsarea().equals(Integer.valueOf(0))) {
				return Integer.valueOf(0);
			}
			return Integer.valueOf(1);
		}).reduce(0, (acc, item) -> acc | item);
	}

	@Override
	public Integer getIskeyByChild(List<SignTree> signTreeLsit) {
		if (ObjectUtil.isEmpty(signTreeLsit)) {
			return Integer.valueOf(0);
		}
		return signTreeLsit.stream().map(SignTree::getIskey).reduce(0, (acc, item) -> acc | item);
	}

	@Override
	public List<SignTreeDto2> queryTreeByroot(Integer tid, Integer root, String rootPath) {
		return baseMapper.queryTreeByroot(tid, root, rootPath);
	}
}
