package com.digitalchina.common.base.entity.tree;

import java.beans.Transient;
import java.io.Serializable;

/**
 * <p>
 * ================================================
 * <p>
 * Title: 实体实现该接口表示想要实现树结构
 * <p>
 * Description:
 * <p>
 * Date: 2018/2/25 15:48
 * <p>
 * ================================================
 *
 * @author Hope Chen
 * @version 1.0
 */
public interface TreeNode<ID extends Serializable> {

	Long ROOT = 0L;

	/**
	 * 节点
	 *
	 * @return ID
	 */
	ID getId();

	/**
	 * 节点
	 *
	 * @param id
	 *            ID
	 */
	void setId(ID id);

	/**
	 * 节点名称
	 *
	 * @return name
	 */
	String getName();

	/**
	 * 设置节点名称
	 *
	 * @param name
	 *            name
	 */
	void setName(String name);

	/**
	 * 节点的级别，默认最高级为0
	 *
	 * @return
	 */
	Integer getLevel();

	/**
	 * 是否是根节点
	 *
	 * @return
	 */
	@Transient
	default boolean isRoot() {
		return getPid() == null || "".equals(getPid()) || getPid().equals(ROOT);
	}

	/**
	 * 父节点
	 *
	 * @return parentId
	 */
	ID getPid();

	/**
	 * 设置父节点
	 *
	 * @param pid
	 *            pid
	 */
	void setPid(ID pid);

}
