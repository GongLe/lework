package org.lework.core.persistence.dao.permission;

import org.lework.core.persistence.entity.permission.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 角色权限Dao
 * User: Gongle
 * Date: 13-9-9
 * Time: 下午9:24
 */
public interface PermissionDao extends PagingAndSortingRepository<Permission, String>, JpaSpecificationExecutor<Permission> {
    public Permission findByCode(String code);

    @Query("select distinct p from User u left join u.roles r left join r.permissions p where u.id=?1 and p.status=?2  ")
    public List<Permission> findUserPermissions(String userId, String status);

}
