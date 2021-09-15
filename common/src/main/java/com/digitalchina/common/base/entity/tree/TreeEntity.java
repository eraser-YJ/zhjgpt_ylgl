package com.digitalchina.common.base.entity.tree;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.digitalchina.common.base.entity.AbstractEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 树抽象实体基类
 */
public abstract class TreeEntity<T extends TreeEntity, ID extends Serializable> extends AbstractEntity<ID>
		implements TreeNode<ID> {

	private static final long serialVersionUID = 2456436850196084669L;

	@ApiModelProperty(value = "节点id")
	private ID id;

	@ApiModelProperty(value = "父节点id")
	private ID pid;

	@ApiModelProperty(value = "节点名称")
	private String name;

	@ApiModelProperty(value = "节点层级")
	private Integer level;

	@ApiModelProperty(value = "拥有的子节点列表")
	private List<T> children;

	public TreeEntity() {
		super();
	}

	/**
	 * 将集合组织成树状对象
	 *
	 * @param list
	 * @param      <T>
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transient
	public static <T extends TreeEntity> List<T> makeTree(List<T> list) {
		List<T> treeList = new ArrayList<T>();
		for (T tree : list) {
			// 找到根
			if (tree.getPid() == null || tree.getPid().equals(0)) {
				treeList.add(tree);
			}
			// 找到子
			for (T item : list) {
				if (null != item.getPid() && null != tree.getId() && item.getPid().equals(tree.getId())) {
					if (tree.getChildren() == null) {
						tree.setChildren(new ArrayList<T>());
					}
					tree.getChildren().add(item);
				}
			}
		}
		return treeList;
	}

	/**
	 * 将集合组织成树状对象
	 *
	 * @param list
	 * @param      <T>
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transient
	public static <T extends TreeEntity> List<T> makeTree2(List<T> list) {
		List<T> treeList = new ArrayList<T>();
		for (T tree : list) {
			// 找到子
			for (T item : list) {
				if (null != item.getPid() && null != tree.getId() && item.getPid().equals(tree.getId())) {
					if (tree.getChildren() == null) {
						tree.setChildren(new ArrayList<T>());
					}
					tree.getChildren().add(item);
				}
			}
			// 找不到子就当根
			treeList.add(tree);
		}
		return treeList;
	}

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public ID getPid() {
		return pid;
	}

	@Override
	public void setPid(ID pid) {
		this.pid = pid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public Integer getLevel() {
		return level;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

}
