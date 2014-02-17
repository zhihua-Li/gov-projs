/**
 * SysMenu.java
 *
 * 2013-5-24
 */
package com.lims.domain.po;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lizhihua
 *
 */
public class SysMenu extends BaseDomain implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;						/**		主键ID	*/

	private String menuName;				/**		菜单名称*/

	private String menuLink;				/**		菜单连接*/

	private String menuLevel;				/**		菜单级别*/

	private String parentMenuId;			/**		父菜单ID*/

	private String activeFlag;				/**		启用标识*/

	private int menuSort;					/**		排序*/


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public int getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(int menuSort) {
		this.menuSort = menuSort;
	}


	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
