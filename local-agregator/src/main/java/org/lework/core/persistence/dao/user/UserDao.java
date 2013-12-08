package org.lework.core.persistence.dao.user;

import org.lework.core.persistence.entity.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User Dao
 */
public interface UserDao extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByLoginName(String loginName);
    User findByEmail(String email);

    @Query("select u from User u inner join u.roles r  where r.id=?1 order by u.orgName ")
    public List<User> findRoleRelatedUsers(String roleId);

    @Query("select u from User u inner join u.org o where o.id=?1 order by u.name ")
    public List<User> findOrgRelatedUsers(String orgId);
}
