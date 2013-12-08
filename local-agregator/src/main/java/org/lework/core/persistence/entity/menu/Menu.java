package org.lework.core.persistence.entity.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.lework.core.persistence.entity.AuditorEntity;
import org.lework.core.persistence.entity.role.Role;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 * User: Gongle
 * Date: 13-10-22
 */
@Entity
@Table(name = "SS_MENU")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends AuditorEntity {

    private String name;            //菜单名
    private String code;            //代码
    private Integer sortNum;            //排序
    private String type = "menu";        //类型  menu,url
    private String status;        //状态 默认为有效状态
    private String url;                //URL
    private String icon;                //图标
    private String parentName;
    private Menu parentMenu;        //上级菜单
    private List<Menu> childrenMenus;    //下级菜单
    private List<Role> roles = new ArrayList<Role>();    //菜单对应的角色

    @Transient
    public boolean hasChild() {
        return Collections3.isNotEmpty(getChildrenMenus());
    }

    @Transient
    public boolean getHasChild() {
        return hasChild();
    }

    @Transient
    public String getParentId() {
        Menu parent = getParentMenu();
        return parent != null ? parent.getId() : Strings.EMPTY;
    }

    @Transient
    public boolean hasParent() {
        return getParentMenu() != null;
    }

    @NotEmpty
    @Length(max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @Length(max = 50)
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

    @NotBlank
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotBlank
    @Length(max = 200)
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_parent_menu_id")
    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY)
    @OrderBy(value = "sortNum asc")
    public List<Menu> getChildrenMenus() {
        return childrenMenus;
    }

    public void setChildrenMenus(List<Menu> childrenMenus) {
        this.childrenMenus = childrenMenus;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "menus")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
        Menu rhs = (Menu) obj;
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
