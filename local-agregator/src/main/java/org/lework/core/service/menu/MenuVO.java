package org.lework.core.service.menu;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.lework.core.persistence.entity.menu.Menu;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu Entity View Object
 * User: Gongle
 * Date: 13-12-5
 */
public class MenuVO {
    public MenuVO() {
    }

    public MenuVO(Menu entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.sortNum = entity.getSortNum();
        this.type = entity.getType();
        this.url = entity.getUrl();
        this.icon = entity.getIcon();
        this.parentName = entity.getParentName();
    }

    private String id;
    private String name;            //菜单名
    private String code;            //代码
    private Integer sortNum;            //排序
    private String type = "menu";        //类型  menu,url
    private String status;        //状态 默认为有效状态
    private String url;                //URL
    private String icon;                //图标
    private String parentName;
    List<MenuVO> childrenMenus = new ArrayList<MenuVO>();
    MenuVO parentMenu;

    public boolean hasChild() {
        return Collections3.isNotEmpty(getChildrenMenus());
    }

    public boolean getHasChild() {
        return Collections3.isNotEmpty(getChildrenMenus());
    }

    public String getParentId() {
        MenuVO parent = getParentMenu();
        return parent != null ? parent.getId() : Strings.EMPTY;
    }

    @Transient
    public boolean hasParent() {
        return getParentMenu() != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<MenuVO> getChildrenMenus() {
        return childrenMenus;
    }

    public void setChildrenMenus(List<MenuVO> childrenMenus) {
        this.childrenMenus = childrenMenus;
    }

    public MenuVO getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(MenuVO parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        MenuVO rhs = (MenuVO) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(code, rhs.code)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(code).
                toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("code", code)
                .append("name", name)
                .append("status", status)
                .toString();
    }
}
