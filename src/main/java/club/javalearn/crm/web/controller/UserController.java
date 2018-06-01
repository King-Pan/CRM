package club.javalearn.crm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/list")
    public String userList(){
        return "userList";
    }
}
