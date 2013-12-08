package org.lework.core.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.lework.core.common.domain.ShiroUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Apache shiro utils
 * User: Gongle
 * Date: 13-11-27
 */
public class SubjectUtils {
    private static Logger logger = LoggerFactory.getLogger(SubjectUtils.class);

    public static boolean isAnonymous() {
        Subject currentUser = getSubject();
        if (currentUser == null) {
            return true;
        }
        if (!currentUser.isAuthenticated() && !currentUser.isRemembered()) {
            return true;
        }
        return false;
    }

    public static String getUserLoginName() {
        ShiroUser su = getUser();
        return su != null ? su.getLoginName() : null;
    }
    public static ShiroUser getUser() {
        Subject currentUser = getSubject();
        if (currentUser == null) {
            return null;
        }
        if (!(currentUser.getPrincipal() instanceof ShiroUser)) {
            return null;
        }
        ShiroUser principal = (ShiroUser) currentUser.getPrincipal();
        return principal;
    }

    public static Subject getSubject() {
        Subject currentUser = null;
        try {
            currentUser = SecurityUtils.getSubject();
        } catch (UnavailableSecurityManagerException e) {
            logger.warn("getSubject(): Attempt to use Shiro prior to initialization");
        }
        return currentUser;
    }
}
