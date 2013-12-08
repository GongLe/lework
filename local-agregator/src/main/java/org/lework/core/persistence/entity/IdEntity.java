package org.lework.core.persistence.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.lework.runner.utils.Strings;

import javax.persistence.*;

/**
 * 统一定义id的entity基类.
 * UUID主键父类
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {

    protected String id;

    /**
     * 获取主键ID
     *
     * @return String
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32, nullable = false, unique = true)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return this.id;
    }

    /**
     * 设置主键ID，
     *
     * @param id
     */
    public void setId(String id) {
        if (StringUtils.isEmpty(id)) {
            this.id = null;
        } else {
            this.id = id;
        }

    }

    @Transient
    public boolean isNew() {
        return Strings.isBlank(getId());
    }

    @Transient
    public boolean getIsNew() {
        return Strings.isBlank(getId());
    }
}
