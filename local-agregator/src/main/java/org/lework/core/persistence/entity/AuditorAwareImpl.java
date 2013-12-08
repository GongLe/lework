package org.lework.core.persistence.entity;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.lework.core.common.domain.ShiroUser;
import org.lework.runner.utils.Strings;
import org.springframework.data.domain.AuditorAware;

/**
 * Spring data jpa AuditorAware implement
 * User: Gongle
 * Date: 13-9-8
 * Time: 下午1:09
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser == null)
            return Strings.EMPTY;
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        return shiroUser.getLoginName() ;
    }
}
