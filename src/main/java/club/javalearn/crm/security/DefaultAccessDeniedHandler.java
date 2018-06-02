package club.javalearn.crm.security;

import club.javalearn.crm.utils.HTTPUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // AJAX请求,使用response发送403
        if (HTTPUtils.isAjaxRequest(request)) {
            response.sendError(403);
            // 非AJAX请求，跳转系统默认的403错误界面，在web.xml中配置
        } else if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    accessDeniedException.getMessage());
        }
    }
}