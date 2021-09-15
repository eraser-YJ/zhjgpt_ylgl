package com.digitalchina.center;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @package: com.tree
 * @class: TreeNode1
 * @date: 2018/3/27
 * @author: jcroad(caoyajing @ yunmel.com)
 * @since 1.0
 */
public class TreeNode {

	private Integer id;
	private Integer pid;
	private String name;
	private List<TreeNode> children;

	TreeNode(Integer id, Integer pid, String name) {
		this.id = id;
		this.pid = pid;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", pid=" + pid + ", name=" + name + ", children=" + children + "]";
	}

	@Test
	public void test(String[] args) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		list.add(new TreeNode(1, 0, "1"));
		list.add(new TreeNode(2, 0, "2"));
		list.add(new TreeNode(3, 2, "3"));
		list.add(new TreeNode(4, 3, "4"));
		list.add(new TreeNode(5, 4, "5"));
		list.add(new TreeNode(6, 5, "6"));
		list.add(new TreeNode(7, 1, "6"));
		list.add(new TreeNode(8, 7, "6"));
		list.add(new TreeNode(9, 8, "6"));
		list.add(new TreeNode(10, 9, "6"));
		list.add(new TreeNode(11, 10, "6"));
		list.add(new TreeNode(12, 11, "6"));
		list.add(new TreeNode(13, 12, "6"));

		List<TreeNode> treeList = new ArrayList<TreeNode>();
		List<TreeNode> treeList1 = new ArrayList<TreeNode>();
		List<TreeNode> treeList2 = new ArrayList<TreeNode>();
		// 方法一、
		long start0 = System.currentTimeMillis();
		treeList = listGetStree(list);
		long end0 = System.currentTimeMillis();

		long start1 = System.currentTimeMillis();
		// treeList1 = listToTree(list);
		long end1 = System.currentTimeMillis();

		long start2 = System.currentTimeMillis();
		// treeList2 = toTree(list);
		long end2 = System.currentTimeMillis();

		System.out.println("耗时：" + (end0 - start0) + " 结果：" + treeList);
		// System.out.println("耗时：" + (end1 - start1) + " 结果：" + treeList1);
		// System.out.println("耗时：" + (end2 - start2) + " 结果：" + treeList2);
	}

	private static List<TreeNode> listGetStree(List<TreeNode> list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (TreeNode tree : list) {
			// 找到根
			if (tree.getPid() == 0) {
				treeList.add(tree);
			}
			// 找到子
			for (TreeNode treeNode : list) {
				if (treeNode.getPid() == tree.getId()) {
					if (tree.getChildren() == null) {
						tree.setChildren(new ArrayList<TreeNode>());
					}
					tree.getChildren().add(treeNode);
				}
			}
		}
		return treeList;
	}

	/**
	 * 方法二、
	 * 
	 * @param list
	 * @return
	 */
	public static List<TreeNode> listToTree(List<TreeNode> list) {
		// 用递归找子。
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (TreeNode tree : list) {
			if (tree.getPid() == 0) {
				treeList.add(findChildren(tree, list));
			}
		}
		return treeList;
	}

	private static TreeNode findChildren(TreeNode tree, List<TreeNode> list) {
		for (TreeNode node : list) {
			if (node.getPid() == tree.getId()) {
				if (tree.getChildren() == null) {
					tree.setChildren(new ArrayList<TreeNode>());
				}
				tree.getChildren().add(findChildren(node, list));
			}
		}
		return tree;
	}

	/**
	 * 方法三
	 * 
	 * @param list
	 * @return
	 */
	private static List<TreeNode> toTree(List<TreeNode> list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (TreeNode tree : list) {
			if (tree.getPid() == 0) {
				treeList.add(tree);
			}
		}
		for (TreeNode tree : list) {
			toTreeChildren(treeList, tree);
		}
		return treeList;
	}

	private static void toTreeChildren(List<TreeNode> treeList, TreeNode tree) {
		for (TreeNode node : treeList) {
			if (tree.getPid() == node.getId()) {
				if (node.getChildren() == null) {
					node.setChildren(new ArrayList<TreeNode>());
				}
				node.getChildren().add(tree);
			}
			if (node.getChildren() != null) {
				toTreeChildren(node.getChildren(), tree);
			}
		}
	}

}
