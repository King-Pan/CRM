package club.javalearn.crm.security;

import club.javalearn.crm.model.Permission;
import club.javalearn.crm.model.Role;
import club.javalearn.crm.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
@Slf4j
@Component("customInvocationSecurityMetadataSourceService")
public class CustomInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionRepository permissionRepository;

    // resourceMap及为key-url，value-Collection<ConfigAttribute>,资源权限对应Map
    private static Map<MyGrantedAuthority, Collection<ConfigAttribute>> resourceMap = null;

    // 加载所有资源与权限的关系
    private void loadResourceDefine() {
        if (resourceMap == null) {
            resourceMap = new HashMap<>();
            List<Permission> resources = permissionRepository.findAll();
            // 加载资源对应的权限
            for (Permission permission : resources) {
                Collection<ConfigAttribute> auths = new ArrayList<ConfigAttribute>();
                for (Role role : permission.getRoles()) {
                    auths.add(new SecurityConfig(role.getRoleName()));
                }
                log.info("权限=" + auths);
                resourceMap.put(new MyGrantedAuthority(permission.getUrl(),permission.getMethod()), auths);
            }
        }
    }

    //由资源路径获得权限
    //object为请求的资源路径
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        // object 是一个URL，被用户请求的url。
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        log.info("请求原始地址-->{}<-- ", requestUrl);

        int firstQuestionMarkIndex = requestUrl.indexOf("?");
        //如果请求的资源路径有？后面的参数，则将？后面的切掉，以免拒绝访问
        if (firstQuestionMarkIndex != -1) {
            requestUrl = requestUrl.substring(0, firstQuestionMarkIndex);
        }


        log.info("请求处理后地址-->{}<-- ", requestUrl+"(?后面的参数去除)");
        if (resourceMap == null) {
            loadResourceDefine();
        }
        if(resourceMap!=null && !resourceMap.isEmpty()){
            Iterator<MyGrantedAuthority> ite = resourceMap.keySet().iterator();
            //根据资源路径获得其所需的权限
            while (ite.hasNext()) {
                MyGrantedAuthority authority = ite.next();
                RequestMatcher requestMatcher = new AntPathRequestMatcher(authority.getUrl(),authority.getMethod().toUpperCase());
                if(requestMatcher.matches(filterInvocation.getHttpRequest())) {
                    if(StringUtils.isNoneBlank(authority.getMethod()) && filterInvocation.getHttpRequest().getMethod().equalsIgnoreCase(authority.getMethod())){
                        return  resourceMap.get(authority);
                    }
                    if(StringUtils.isBlank(authority.getMethod())){
                        log.info("配置路径-->{}<--与请求路径匹配-->{}<--",authority.getUrl(),requestUrl);
                        return resourceMap.get(authority);
                    }

                }
                log.error("配置路径-->{}<--与请求路径不匹配-->{}<--",authority.getUrl(),requestUrl);
            }
        }
        return null;
    }

    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

}
