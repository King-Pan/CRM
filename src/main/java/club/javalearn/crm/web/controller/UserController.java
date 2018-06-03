package club.javalearn.crm.web.controller;

import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public String userAdd(){
        return "用户新增";
    }

    @GetMapping("/{id}")
    public String userList(@PathVariable("id")Long userId){
        return "用户查询";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")Long userId){
        return "用户删除";
    }

    @DeleteMapping("/all")
    public String all(){
        return "查询所有用户信息-包括密码";
    }
}
