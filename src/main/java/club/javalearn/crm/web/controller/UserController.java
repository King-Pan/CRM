package club.javalearn.crm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "用户列表";
    }

    @GetMapping("/add")
    public String userAdd(){
        return "用户新增";
    }

    @GetMapping("/{id}")
    public String userList(@PathVariable("id")Long userId){
        return "用户查询";
    }
}
