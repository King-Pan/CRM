package club.javalearn.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * 用户信息
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@Table(name = "sys_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles"})
public class User implements Serializable {
    /**
     * 用戶编码-自增长策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 登录用户名
     */
    @NotEmpty(message = "账号不能为空")
    @Size(min = 3, max = 20)
    @Column(name = "username",nullable = false, length = 20, unique = true)
    private String username;

    /**
     * 用户邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50)
    @Email(message = "邮箱格式不对")
    private String email;

    /**
     * 用户电话号码
     */
    private String phoneNum;

    /**
     * 用户昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @Size(min = 2, max = 20)
    @Column(length = 20, nullable = false)
    private String nickName;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 100)
    @Column(length = 100)
    private String password;

    /**
     * 加密盐
     */
    @Column(length = 100)
    private String slat;

    /**
     * 用户头像地址
     */
    @Column(length = 256)
    private String imgUrl;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户状态
     */
    @Column(length = 10)
    private String status;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<Long> roleIds = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User user = (User)obj;
            return user.getUserId().equals(this.getUserId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getUserId()!=null?this.getUserId().hashCode():0;
    }
}
