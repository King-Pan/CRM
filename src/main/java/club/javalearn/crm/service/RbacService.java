package club.javalearn.crm.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring Security rbac 接口
 *
 * @author king-pan
 * @date 2018-06-04
 **/
public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
