package com.digitalchina.common.base.entity.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 树排序
 */
public class TreeSortUtil<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 2444638065060902858L;

	private List<TreeNode<ID>> treeNodes;

	private List<TreeNode<ID>> newTreeNodes;

	public static <T extends Serializable> TreeSortUtil<T> create() {
		return new TreeSortUtil<>();
	}

	public TreeSortUtil() {
		this.newTreeNodes = new ArrayList<>();
	}

	/**
	 * 获得根节点
	 */
	public List<TreeNode<ID>> getTopNodes() {
		List<TreeNode<ID>> list = new ArrayList<>();
		for (TreeNode<ID> treeable : treeNodes) {
			if (treeable.isRoot()) {
				list.add(treeable);
			}
		}
		return list;
	}

	/**
	 * 解析根节点
	 *
	 * @param node
	 */
	public void parseSubNode(TreeNode<ID> node) {
		for (TreeNode<ID> treeable : treeNodes) {
			if (!ObjectUtil.isEmpty(treeable.getPid()) && treeable.getPid().equals(node.getId())) {
				newTreeNodes.add(treeable);
				parseSubNode(treeable);
			}
		}
	}

	/**
	 * 运行排序
	 */
	@SuppressWarnings("unchecked")
	public TreeSortUtil<ID> sort(List<?> treeNodes) {
		this.treeNodes = (List<TreeNode<ID>>) treeNodes;
		List<TreeNode<ID>> rootNodes = getTopNodes();
		for (TreeNode<ID> rootNode : rootNodes) {
			newTreeNodes.add(rootNode);
			parseSubNode(rootNode);
		}
		this.treeNodes.clear();
		this.treeNodes.addAll(newTreeNodes);
		return this;
	}

	public TreeSortUtil<ID> filter(ID... ids) {
		newTreeNodes.clear();
		for (TreeNode<ID> treeNode : this.treeNodes) {
			if (!ArrayUtil.contains(ids, treeNode.getId())) {
				newTreeNodes.add(treeNode);
			}
		}
		this.treeNodes.clear();
		this.treeNodes.addAll(newTreeNodes);
		return this;
	}
}
