package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Permission;
import club.javalearn.crm.model.Role;
import club.javalearn.crm.model.User;
import club.javalearn.crm.service.RbacService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-06-04
 **/
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        User user = (User) principal;
        boolean hasPermission = false;

        if (principal instanceof User) {
            //如果用户名是admin，就永远返回true
            if (StringUtils.equals(user.getUsername(), "admin")) {
                hasPermission = true;
            } else {
                // 读取用户所拥有权限的所有URL
                // 读取用户所拥有权限的所有URL
                Set<Role> roles = user.getRoles();
                Set<Permission> urls = new HashSet<>();
                for (Role role:roles){
                    urls.addAll(role.getPermissions());
                }

                for (Permission permission : urls) {
                    if (antPathMatcher.match(permission.getUrl(), request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }
}
