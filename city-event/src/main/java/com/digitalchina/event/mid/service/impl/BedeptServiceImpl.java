package com.digitalchina.event.mid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.mapper.BedeptMapper;
import com.digitalchina.event.mid.service.BedeptService;

/**
 * <p>
 * 事件部门 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Service
public class BedeptServiceImpl extends ServiceImpl<BedeptMapper, Bedept> implements BedeptService {

	@Override
	public void updateSongTreePaths(Integer[] oldPath, Integer[] newPath, Integer lowerBound, Integer upperBound) {
		baseMapper.updateSongTreePaths(oldPath, newPath, lowerBound, upperBound);
	}

	@Override
	public Bedept findOne(Integer id) {
		return baseMapper.findOne(id);
	}

	@Override
	public void updateBdtype() {
		baseMapper.updateBdtype();
	}

	@Override
	public void removeTreeById(Integer bedid) {
		baseMapper.removeTreeById(bedid);
	}

	@Override
	public List<Bedept> getChildById(Integer bedid) {
		return baseMapper.getChildById(bedid);
	}
}
