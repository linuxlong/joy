package com.kvc.joy.plugin.security.erbac.biz.impl;

import com.kvc.joy.commons.bean.TreeNode;
import com.kvc.joy.commons.log.Log;
import com.kvc.joy.commons.log.LogFactory;
import com.kvc.joy.core.persistence.orm.jpa.JpaTool;
import com.kvc.joy.plugin.security.erbac.biz.IErbacRoleBiz;
import com.kvc.joy.plugin.security.erbac.model.po.TErbacRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class ErbacRoleBiz implements IErbacRoleBiz {
	
	protected static final Log logger = LogFactory.getLog(ErbacRoleBiz.class);

	public List<TreeNode> getAllRoles() {
		List<TErbacRole> roleList = JpaTool.searchAll(TErbacRole.class);
		Map<Integer, TreeNode> treeNodeMap = new HashMap<Integer, TreeNode>(roleList.size());
		for (TErbacRole role : roleList) {
//			treeNodeMap.put(role.getId(),new TreeNode(role.getId() + "", role.getName())); //TODO
		}

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (TErbacRole role : roleList) {
			//TODO
//			TreeNode node = treeNodeMap.get(role.getId());
//			Integer pId = role.getParentId();
//			if (pId == null) { // 根
//				treeNodeList.add(node);
//			} else {
//				TreeNode pNode = treeNodeMap.get(pId);
//				if (pNode != null) {
//					pNode.setLeaf(false);
//					pNode.getChildren().add(node);
//				} else {
//					logger.error("角色#" + node.getId() + "的父亲#" + pId + "不存在！");
//				}
//			}
		}
		return treeNodeList;
	}

}
