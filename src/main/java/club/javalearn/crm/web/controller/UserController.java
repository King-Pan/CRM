package club.javalearn.crm.web.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/all")
    public String all(){
        return "查询所有用户信息-包括密码";
    }
}
