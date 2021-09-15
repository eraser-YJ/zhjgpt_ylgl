package com.digitalchina.event.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.event.mid.entity.Bedept;

/**
 * <p>
 * 事件部门 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BedeptMapper extends BaseMapper<Bedept> {

	/**
	 * 更新变动节点的子树路径
	 * 
	 * @param oldPath    变动点旧路径
	 * @param newPath    变动点新路径
	 * @param lowerBound 下界
	 * @param upperBound 上界
	 */
	void updateSongTreePaths(@Param("oldPath") Integer[] oldPath, @Param("newPath") Integer[] newPath,
			@Param("lowerBound") Integer lowerBound, @Param("upperBound") Integer upperBound);

	/**
	 * 根据id查询部门信息 需父部门名称
	 * 
	 * @param id
	 * @return
	 */
	Bedept findOne(@Param("id") Integer id);

	@Update("update bedept set bdtype = case when array_length(bdpntids, 1) is null then 1 else array_length(bdpntids, 1)+1 end;")
	void updateBdtype();

	/**
	 * 移除树
	 * 
	 * @param bedid
	 */
	void removeTreeById(Integer bedid);

	/**
	 * 获取所有下级
	 * 
	 * @param bedid
	 */
	List<Bedept> getChildById(Integer bedid);

}
