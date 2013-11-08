package com.kvc.joy.plugin.security.erbac.model.po;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.kvc.joy.commons.support.IListToTreeRestrict;
import com.kvc.joy.core.persistence.entity.UuidCrudEntity;
import com.kvc.joy.core.persistence.orm.jpa.annotations.Comment;

@Entity
@Table(name = "t_erbac_group")
@Comment("组")
public class TErbacGroup extends UuidCrudEntity implements IListToTreeRestrict<String> {

	private String parentId; // 父组id
	private String name; // 组名
	private String desc; // 描述
	private Collection<TErbacRole> roles; // 角色
	private Collection<TErbacUser> users; // 用户
	// private Collection<TErbacAuthority> authorities; // 权限
	private Collection<TErbacGroupAuth> groupAuths; // 组权限关系

	@Column(name = "GROUP_NAME", length = 64)
	@Comment("名称")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	@Comment("描述")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "groups",// 通过维护端的属性关联
	fetch = FetchType.LAZY)
	public Collection<TErbacRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<TErbacRole> roles) {
		this.roles = roles;
	}

	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "t_erbac_group_user", // 关联表名
	inverseJoinColumns = @JoinColumn(name = "USER_ID"), // 被维护端外键
	joinColumns = @JoinColumn(name = "GROUP_ID")) // 维护端外键
	public Collection<TErbacUser> getUsers() {
		return users;
	}

	public void setUsers(Collection<TErbacUser> users) {
		this.users = users;
	}

	// @ManyToMany(cascade = CascadeType.REFRESH)
	// @JoinTable(name = "t_erbac_group_auth", // 关联表名
	// inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID"), // 被维护端外键
	// joinColumns = @JoinColumn(name = "GROUP_ID")) // 维护端外键
	// public Collection<TErbacAuthority> getAuthorities() {
	// return authorities;
	// }
	//
	// public void setAuthorities(Collection<TErbacAuthority> authorities) {
	// this.authorities = authorities;
	// }

	@Column(length = 32)
	@Comment("父项ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@OneToMany(mappedBy = "group", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public Collection<TErbacGroupAuth> getGroupAuths() {
		return groupAuths;
	}

	public void setGroupAuths(Collection<TErbacGroupAuth> groupAuths) {
		this.groupAuths = groupAuths;
	}
	
//	@Id
//	@Column(length = 32)
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

}
