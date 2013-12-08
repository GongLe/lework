package org.lework.core.persistence.dao.role;

import org.lework.core.persistence.entity.role.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Role Dao
 * User: Gongle
 * Date: 13-9-9
 * Time: 下午9:14
 */
public interface RoleDao extends PagingAndSortingRepository<Role, String>, JpaSpecificationExecutor<Role> {
    public Role findByCode(String code);

    //@Query(value = "from Role where s")
    public List<Role> findAllByStatus(String status);

    @Query("select distinct r from User u inner join u.roles r where u.id=?1 and r.status=?2 ")
    public List<Role> findUserRolesByStatus(String userId, String status);

    @Query(" from Role where groupId=?1 order by sortNum ")
    public List<Role> findRolesByGroupId(String groupId);

    @Query("select r from Role r inner join r.menus m  where m.id=?1 order by r.sortNum ")
    public List<Role> findMenuRelatedRoles(String menuId);

    @Query("select r from Role r order by r.groupId , r.sortNum ")
    public List<Role> findAllRoles();

}
