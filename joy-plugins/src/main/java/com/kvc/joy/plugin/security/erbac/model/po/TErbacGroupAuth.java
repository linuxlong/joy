package com.kvc.joy.plugin.security.erbac.model.po;

import com.kvc.joy.core.persistence.support.entity.UuidEntity;
import com.kvc.joy.core.persistence.orm.jpa.annotations.Comment;
import com.kvc.joy.plugin.security.erbac.support.enums.AuthorityType;

import javax.persistence.*;

@Entity
@Table(name="t_erbac_group_auth")
@Comment("组权限")
public class TErbacGroupAuth extends UuidEntity {
	
	private TErbacGroup group;
	private TErbacAuthority authority;
	private AuthorityType authorityType; 
	
	@Transient
	public AuthorityType getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(AuthorityType authorityType) {
		this.authorityType = authorityType;
	}

	@Column(length = 1, nullable = false)
	@Comment(value="权限类型代码", codeId="authority_type")
	public String getAuthorityTypeCode() {
		return authorityType.getCode();
	}

	public void setAuthorityTypeCode(String authorityTypeCode) {
		this.authorityType = AuthorityType.enumOf(authorityTypeCode);
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "GROUP_ID") 
	@Comment("组ID")
	public TErbacGroup getGroup() {
		return group;
	}

	public void setGroup(TErbacGroup group) {
		this.group = group;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "AUTHORITY_ID") 
	@Comment("权限ID")
	public TErbacAuthority getAuthority() {
		return authority;
	}

	public void setAuthority(TErbacAuthority authority) {
		this.authority = authority;
	}
	
}