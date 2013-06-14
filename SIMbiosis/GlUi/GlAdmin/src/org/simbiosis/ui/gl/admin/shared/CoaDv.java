package org.simbiosis.ui.gl.admin.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CoaDv implements IsSerializable {
	Long id;
	Long refId;
	String code;
	String prefix;
	String description;
	String codeDescription;
	Long parent;
	String parentCodeDescription;
	Integer level;
	boolean hasChildren;
	Long grandParent;
	long branch;
	String strBranch;
	long excBranch;
	String strExcBranch;

	public CoaDv() {
		super();
		id = 0L;
		refId = 0L;
		parent = 0L;
		grandParent = 0L;
		level = 0;
	}

	public CoaDv(Long id, String code, String description, Long parent,
			boolean hasChildren) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.parent = parent;
		this.hasChildren = hasChildren;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getParentCodeDescription() {
		return parentCodeDescription;
	}

	public void setParentCodeDescription(String parentCodeDescription) {
		this.parentCodeDescription = parentCodeDescription;
	}

	@Override
	public String toString() {
		return code + " - " + description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getGrandParent() {
		return grandParent;
	}

	public void setGrandParent(Long grandParent) {
		this.grandParent = grandParent;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = strBranch;
	}

	public long getExcBranch() {
		return excBranch;
	}

	public void setExcBranch(long excBranch) {
		this.excBranch = excBranch;
	}

	public String getStrExcBranch() {
		return strExcBranch;
	}

	public void setStrExcBranch(String strExcBranch) {
		this.strExcBranch = strExcBranch;
	}

}
