package org.lework.core.persistence.dao.user;

import com.google.common.collect.Lists;
import org.lework.core.persistence.entity.user.User;
import org.lework.runner.orm.JpaDao;
import org.lework.runner.utils.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 使用Abstract Common Dao实现dao层
 *
 * @see org.lework.runner.orm.JpaDao
 *      User: Gongle
 *      Date: 13-9-10
 */
@Repository
public class UserNativeDao extends JpaDao<User, String> {
    public static final String QUERY_ALL = "from User where 1=1";

    public User findOne(String id) {
        return get(id);
    }

    public List<User> findAll() {
        return super.findAll();
    }

    public Page<User> findPageUser(Pageable pageable) {
        return findPage(pageable, QUERY_ALL);
    }

    public User findByLoginName(String loginName) {
        return findUnique("from User where loginName=?", loginName);
    }

    /**
     * 获取角色关联的用户.
     *
     * @param pageable
     * @param roleId   角色ID
     * @param search   过滤条件:name or loginName
     * @return
     */
    public Page<User> findUserPageByRoleId(Pageable pageable, String roleId, String search) {
        StringBuilder jpql = new StringBuilder("select x from User x inner join x.roles r  where r.id=?1 ");
        List<String> arrParam = Lists.newArrayList(roleId);

        if (Strings.isNotEmpty(search)) {
            jpql.append(" and x.name like ?2 or x.loginName like ?3 ");
            arrParam.add("%" + search + "%");
            arrParam.add("%" + search + "%");
        }
        return findPage(pageable, jpql.toString(), arrParam.toArray());
    }

    /**
     * 获取组织关联的用户.
     *
     * @param pageable
     * @param orgId    组织ID
     * @param search   过滤条件:name
     * @return
     */
    public Page<User> findUserPageByOrgId(Pageable pageable, String orgId, String search) {
        StringBuilder jpql = new StringBuilder("select x from User x inner join x.org o  where o.id=?1 ");
        List<String> arrParam = Lists.newArrayList(orgId);

        if (Strings.isNotEmpty(search)) {
            jpql.append(" and x.name like ?2 or x.loginName like ?3 ");
            arrParam.add("%" + search + "%");
            arrParam.add("%" + search + "%");
        }
        return findPage(pageable, jpql.toString(), arrParam.toArray());
    }
}
