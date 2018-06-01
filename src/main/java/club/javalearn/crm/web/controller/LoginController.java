package club.javalearn.crm.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * crm登录处理控制器
 *
 * @author king-pan
 * @date 2018-05-18
 **/
@Controller
public class LoginController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/whoAmI")
    public Object whoIm() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
