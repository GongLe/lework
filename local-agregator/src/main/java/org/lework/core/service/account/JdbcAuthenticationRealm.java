package org.lework.core.service.account;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.lework.core.common.domain.ShiroUser;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.user.User;
import org.lework.runner.utils.Encodes;
import org.lework.runner.utils.Strings;

import javax.annotation.PostConstruct;


/**
 * shiro身份验证类
 *
 * @author Gongle
 */
public class JdbcAuthenticationRealm extends AuthorizationRealm {
    /**
     * shiro身份验证回调方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();

        if (StringUtils.isBlank(username)) {
            throw new AccountException("用户名不能为空");
        }
        User user = accountService.getUserByLoginName(username);

        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        } else if (Strings.equals(user.getStatus(), Status.disable.getCode())) {
            throw new DisabledAccountException("账号是禁用的");
        }

        byte[] salt = Encodes.decodeHex(user.getSalt());
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginName(), user.getName());


        return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());

    }


    /**
     * 设定Password 校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
        matcher.setHashIterations(AccountService.HASH_INTERATIONS);
        /*Sets the CrendialsMatcher used during an authentication attempt to verify submitted credentials with those stored in the system.*/
        setCredentialsMatcher(matcher);
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    public AccountService getAccountService() {
        return accountService;
    }


}
