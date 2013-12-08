package org.lework.core.persistence.dao.role;

import com.google.common.collect.Lists;
import org.lework.core.persistence.entity.role.Role;
import org.lework.runner.orm.JpaDao;
import org.lework.runner.utils.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Role实体 原生 JPQL Dao
 * User: Gongle
 * Date: 13-11-25
 * Time: 下午2:43
 */
@Repository
public class RoleNativeDao extends JpaDao<Role, String> {
    /**
     * 获取菜单关联的角色.
     *
     * @param pageable
     * @param menuId   菜单ID
     * @param search   过滤条件:name or code
     * @return
     */
    public Page<Role> findRolePageByMenuId(Pageable pageable, String menuId, String search) {
        StringBuilder jpql = new StringBuilder("select x from Role x inner join x.menus m  where m.id=?1 ");
        List<String> arrParam = Lists.newArrayList(menuId);

        if (Strings.isNotEmpty(search)) {
            jpql.append(" and x.name like ?2 or x.code like ?3 ");
            arrParam.add("%" + search + "%");
            arrParam.add("%" + search + "%");
        }
        return findPage(pageable, jpql.toString(), arrParam.toArray());
    }
}
