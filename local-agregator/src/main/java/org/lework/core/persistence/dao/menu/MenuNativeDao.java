package org.lework.core.persistence.dao.menu;

import com.google.common.collect.Lists;
import org.lework.core.persistence.entity.menu.Menu;
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
public class MenuNativeDao extends JpaDao<Menu, String> {


    /**
     * 获取角色关联的菜单.
     *
     * @param pageable
     * @param roleId   角色ID
     * @param search   过滤条件:name
     * @return
     */
    public Page<Menu> findMenuPageByRoleId(Pageable pageable, String roleId, String search) {
        StringBuilder jpql = new StringBuilder("select x from Menu x inner join x.roles  r  where r.id=?1 ");
        List<String> arrParam = Lists.newArrayList(roleId);

        if (Strings.isNotEmpty(search)) {
            jpql.append(" and x.name like ?2 ");
            arrParam.add("%" + search + "%");
        }
        return findPage(pageable, jpql.toString(), arrParam.toArray());
    }
}
