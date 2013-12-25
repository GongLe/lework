package org.lework.core.persistence.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.lework.core.persistence.entity.AuditorEntity;
import org.lework.core.persistence.entity.organization.Organization;
import org.lework.core.persistence.entity.role.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User Entity
 *
 * @author Gongle
 */
@Entity
@Table(name = "SS_USER")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends AuditorEntity {
    private String loginName;
    private String niceName;
    private String name;
    private String email;
    private String plainPassword;
    private String password;
    private String salt;
    private Date userRegistered;
    private String description;
    private String status;
    private String type;
    private String telphone; //联系电话 eg:010-6552555
    private String mobile ;  //手机号码
    private String orgName ; //组织机构名称
    private List<Role> roles = new ArrayList<Role>();
    private Organization org ;

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    @NotBlank
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 不持久化到数据库，也不显示在Restful接口的属性.
    @Transient
    @JsonIgnore
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    // 设定JSON序列化时的日期格式
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(Date userRegistered) {
        this.userRegistered = userRegistered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Email
  /*  @NotBlank*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "SS_USER_ROLE",
            joinColumns =
            @JoinColumn(name = "FK_USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns =
            @JoinColumn(name = "FK_ROLE_ID", referencedColumnName = "ID")
    )
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="FK_ORG_ID")
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        User rhs = (User) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(loginName, rhs.loginName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(loginName).
                toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("loginName", loginName)
                .append("userRegistered", userRegistered)
                .toString();
    }
}