package com.digitalchina.event.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.mid.entity.Bedept;

/**
 * <p>
 * 事件部门 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BedeptService extends IService<Bedept> {

	/**
	 * 更新变动节点的子树路径
	 * 
	 * @param oldPath    变动点旧路径
	 * @param newPath    变动点新路径
	 * @param lowerBound 下界
	 * @param upperBound 上界
	 */
	void updateSongTreePaths(Integer[] oldPath, Integer[] newPath, Integer lowerBound, Integer upperBound);

	/**
	 * 根据id查询部门信息 需父部门名称
	 * 
	 * @param id
	 * @return
	 */
	Bedept findOne(Integer id);

	void updateBdtype();

	/**
	 * 移除树
	 * 
	 * @param bedid
	 */
	void removeTreeById(Integer bedid);

	/**
	 * 获取下级所有
	 * 
	 * @param bedid
	 */
	List<Bedept> getChildById(Integer bedid);
}
