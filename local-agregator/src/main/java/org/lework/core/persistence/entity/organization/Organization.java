package org.lework.core.persistence.entity.organization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.lework.core.common.enumeration.OrgTypes;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.AuditorEntity;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;

import javax.persistence.*;
import java.util.List;

/**
 * 组织机构Entity
 * User: Gongle
 * Date: 13-10-22
 */
@Entity
@Table(name = "SS_ORGANIZATION")
public class Organization extends AuditorEntity {

    /** 组织代码**/
    private String code;
    /** 组织名称**/
    private String name;
    /**组织简称*/
    private String shortName;
    /** 负责人**/
    private String manager;
    /** 副负责人**/
    private String assistantManager;
    /**联系电话**/
    private String phone;
    /**内线**/
    private String innerPhone;
    /**传真**/
    private String fax;
    /**邮编**/
    private String postalCode;
    /**网址**/
    private String url;
    /**地址**/
    private String address;
    /**描述说明**/
    private String description;

    /**
     <option value="corporation">集团</option>
     <option value="region">区域</option>
     <option value="company">公司</option>
     <option value="subCompany">子公司</option>
     <option value="part">部门</option>
     <option value="subPart">子部门</option>
     <option value="team">工作组</option>
     */
    private String type;

    /**组织状态**/
    private String status  ;

    /**
     * 是否是有子级  0:false,1:true
     * ps:本来是用JPA OneToMany getChildren,出于效率考虑,直接用字段标识是否为叶子节点.
     */
    private Integer isleaf  ;
    /** 排序**/
    private Integer  sortNum;            //排序

    private String parentName;
    /**
     * 上级组织
     */
    private Organization parentOrganization;

    private List<Organization> childrenOrganizations;    //下级菜单

    @Transient
    public boolean hasChild() {
        return Collections3.isNotEmpty(getChildrenOrganizations());
    }

    @Transient
    public String getParentId() {
        Organization parent = getParentOrganization();
        return parent != null ? parent.getId() : Strings.EMPTY;
    }

    @Transient
    public boolean hasParent() {
        return getParentOrganization() != null;
    }

    @Transient
    public String getTypeName() {

        return Strings.isNotBlank(getType()) ? OrgTypes.parse(getType()).getName() : Strings.EMPTY;
    }
    @Transient
    public String getStatusName() {

        return Strings.isNotBlank(getStatus()) ?  Status.parse(getStatus()).getName() : Strings.EMPTY;
    }


    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @NotBlank
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    @Column(name = "is_leaf")
    public Integer getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Integer isleaf) {
        this.isleaf = isleaf;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_parent_org_id")
    public Organization getParentOrganization() {
        return parentOrganization;
    }

    public void setParentOrganization(Organization parentOrganization) {
        this.parentOrganization = parentOrganization;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "parentOrganization", fetch = FetchType.LAZY)
    @OrderBy(value = "sortNum asc")
    public List<Organization> getChildrenOrganizations() {
        return childrenOrganizations;
    }

    public void setChildrenOrganizations(List<Organization> childrenOrganizations) {
        this.childrenOrganizations = childrenOrganizations;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAssistantManager() {
        return assistantManager;
    }

    public void setAssistantManager(String assistantManager) {
        this.assistantManager = assistantManager;
    }

    public String getInnerPhone() {
        return innerPhone;
    }

    public void setInnerPhone(String innerPhone) {
        this.innerPhone = innerPhone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Organization rhs = (Organization) obj;
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
